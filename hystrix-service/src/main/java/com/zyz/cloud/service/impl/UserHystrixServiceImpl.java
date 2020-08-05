package com.zyz.cloud.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zyz.cloud.entity.CommonResult;
import com.zyz.cloud.entity.User;
import com.zyz.cloud.service.UserHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.jws.Oneway;

/**
 * @program:springcloudzyz
 * @description:
 * @author: Mr.zyz
 * @create: 2020-08-05 16:03
 */
@Service
public class UserHystrixServiceImpl implements UserHystrixService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServer;

    @Override
    @HystrixCommand(fallbackMethod = "getDefaultUser")
    public CommonResult getUser(Integer id) {
        return restTemplate.getForObject(userServer+"/user/{1}",CommonResult.class,id);
    }


    public CommonResult getDefaultUser(@PathVariable Integer id){
        User defaultUser = new User(-1, "defaultUser", "123456");
        return new CommonResult<>(defaultUser);
    }
}
