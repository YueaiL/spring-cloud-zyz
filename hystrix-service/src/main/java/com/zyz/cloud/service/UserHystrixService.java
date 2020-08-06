package com.zyz.cloud.service;

import com.zyz.cloud.entity.CommonResult;
import com.zyz.cloud.entity.User;

import java.util.concurrent.Future;

public interface UserHystrixService {
    CommonResult getUser(Integer id);

    CommonResult getCacheUser(Integer id);

    CommonResult removeCache(Integer id);

    Future<User> getUserFuture(Integer i);
}
