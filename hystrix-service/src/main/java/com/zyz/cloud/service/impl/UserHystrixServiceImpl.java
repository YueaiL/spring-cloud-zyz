package com.zyz.cloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.zyz.cloud.entity.CommonResult;
import com.zyz.cloud.entity.User;
import com.zyz.cloud.service.UserHystrixService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @program:springcloudzyz
 * @description:
 * @author: Mr.zyz
 * @create: 2020-08-05 16:03
 */
@Service
public class UserHystrixServiceImpl implements UserHystrixService {

    private static Logger log = LoggerFactory.getLogger(UserHystrixServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServer;

    @Override
    @HystrixCommand(fallbackMethod = "getDefaultUser",ignoreExceptions = NullPointerException.class,commandKey = "getUser",threadPoolKey = "getUserThreadPool",groupKey = "getUserGroup")
    public CommonResult getUser(Integer id) {
        if(id == 1){
            throw  new IndexOutOfBoundsException("异常");
        }else if(id == 2) {
            throw new NullPointerException("空指针异常");
        }
        return restTemplate.getForObject(userServer+"/user/{1}",CommonResult.class,id);
    }


    public CommonResult getDefaultUser(@PathVariable Integer id){
        User defaultUser = new User(-1, "defaultUser", "123456");
        return new CommonResult<>(defaultUser);
    }

    @Override
    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "getDefaultUser",commandKey = "getCacheUser")
    public CommonResult getCacheUser(Integer id){
        return restTemplate.getForObject(userServer+"/user/{1}", CommonResult.class, id);
    }

    @Override
    @CacheRemove(commandKey = "getUserCache",cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public CommonResult removeCache(Integer id) {
        return restTemplate.getForObject(userServer+"/user/{1}",CommonResult.class,id);
    }

    @Override
    @HystrixCollapser(batchMethod = "getUserByIds",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "100")
    })
    public Future<User> getUserFuture(Integer i) {
        return new AsyncResult<User>(){
            @Override
            public User invoke(){
                CommonResult forObject = restTemplate.getForObject(userServer + "/user/{1}", CommonResult.class, i);
                Map data = (Map) forObject.getData();
                User user = BeanUtil.mapToBean(data, User.class, true);
                return user;
            }
        };
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Integer> ids){
        log.info("getUserByIds:{}",ids);
        CommonResult forObject = restTemplate.getForObject(userServer + "/user/getUserByIds?ids={1}", CommonResult.class, CollUtil.join(ids,","));
        return (List<User>) forObject.getData();
    }


    public String getCacheKey(Integer id){
        return String.valueOf(id);
    }
}
