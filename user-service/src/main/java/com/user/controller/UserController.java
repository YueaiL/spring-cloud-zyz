package com.user.controller;

import com.user.entity.CommonResult;
import com.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @program:springcloudzyz
 * @description:
 * @author: Mr.zyz
 * @create: 2020-08-05 14:19
 */
@RequestMapping("/user")
@RestController
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public CommonResult<User> create(@RequestBody User user){
        return new CommonResult(200, "创建成功");
    }

    @GetMapping("/{id}")
    public CommonResult<User> getUser(@PathVariable Integer id){
        log.warn("请求进入了"+id);
        return new CommonResult<User>(new User(id, "zyz", "123456"));
    }
}
