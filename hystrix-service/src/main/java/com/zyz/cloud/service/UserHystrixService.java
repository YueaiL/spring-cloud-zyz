package com.zyz.cloud.service;

import com.zyz.cloud.entity.CommonResult;

public interface UserHystrixService {
    CommonResult getUser(Integer id);
}
