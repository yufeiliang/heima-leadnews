package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.pojo.ApUser;
import com.heima.user.dtos.LoginDto;

public interface ApUserService extends IService<ApUser> {
    ResponseResult login(LoginDto dto);
}
