package com.zyz.service.user;

import com.zyz.service.entity.CommonResult;
import com.zyz.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @program:springcloudzyz
 * @description:
 * @author: Mr.zyz
 * @create: 2020-08-05 14:36
 */
@RestController
@RequestMapping("/user")
public class UserRibbonController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @GetMapping("/{id}")
    public CommonResult<User> getUser(@PathVariable Integer id){
        return restTemplate.getForObject(userServiceUrl+"/user/{1}", CommonResult.class,id);
    }

    @PostMapping("/create")
    public CommonResult create(@RequestBody User user){
        return restTemplate.postForObject(userServiceUrl+"/create", user, CommonResult.class);
    }
}
