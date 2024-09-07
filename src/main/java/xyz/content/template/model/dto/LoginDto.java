package xyz.content.template.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-06 10:31
 **/
@Data
public class LoginDto {
    /**
     * 用户名，必须唯一
     */
    @Schema(description = "用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    @NotEmpty(message = "用户密码不能为空")
    private String password;

}
