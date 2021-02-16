package com.community.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/17 12:47 上午
 */



@Data
public class LoginDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 15, message = "登录用户名长度在2-15")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "登录密码长度在6-20")
    private String password;

    private Boolean rememberMe;
}
