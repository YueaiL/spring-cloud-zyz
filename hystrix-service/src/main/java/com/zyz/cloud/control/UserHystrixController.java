package com.zyz.cloud.control;

import com.zyz.cloud.entity.CommonResult;
import com.zyz.cloud.entity.User;
import com.zyz.cloud.service.UserHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
