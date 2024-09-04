package xyz.content.template.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import xyz.content.template.model.entity.CommEntity;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 15:56
 **/
@Data
@Schema(description = "保存用户")
public class SaveUserDto {
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

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    @NotEmpty(message = "用户邮箱不能为空")
    @Email(message = "请输入有效的电子邮件地址")
    private String email;

    /**
     * 用户手机号
     */
    @Schema(description = "用户手机号")
    private String phone;
}
