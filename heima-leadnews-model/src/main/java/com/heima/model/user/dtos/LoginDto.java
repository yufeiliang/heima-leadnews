package com.heima.model.user.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("用户登录DTO")
@Data
public class LoginDto {
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
