package org.nuaa.tomax.easyblog.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/17 20:58
 */
@Data
public class UserForm {
    @NotNull(message = "用户名不能为空")
    @Length(min = 5, max = 18, message = "用户名长度[5, 18]")
    private String username;

    @NotNull(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "密码长度[6, 18]")
    private String password;
}
