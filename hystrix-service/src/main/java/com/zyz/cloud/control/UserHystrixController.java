package com.zyz.cloud.control;

import cn.hutool.core.thread.ThreadUtil;
import com.zyz.cloud.entity.CommonResult;
import com.zyz.cloud.entity.User;
import com.zyz.cloud.service.UserHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @program:springcloudzyz
 * @description:
 * @author: Mr.zyz
 * @create: 2020-08-05 16:01
 */
@RestController
@RequestMapping("/user")
public class UserHystrixController {

    @Autowired
    private UserHystrixService service;

    @GetMapping("/testFallback/{id}")
    public CommonResult testFallback(@PathVariable Integer id){
        return service.getUser(id);
    }


    @GetMapping("/testCache/{id}")
    public CommonResult testCache(@PathVariable Integer id){
        service.getCacheUser(id);
//        service.getCacheUser(id);
        service.removeCache(id);
        service.getCacheUser(id);
        return new CommonResult(200, "操作成功");
    }

    @GetMapping("/testCollapser")
    public CommonResult testCollapser() throws ExecutionException, InterruptedException {
        Future<User> future1 = service.getUserFuture(1);
        Future<User> future2 = service.getUserFuture(2);
        future1.get();
        future2.get();
        ThreadUtil.safeSleep(100);
        Future<User> future3 = service.getUserFuture(3);
        future3.get();
        return new CommonResult(200, "操作成功");
    }
}
