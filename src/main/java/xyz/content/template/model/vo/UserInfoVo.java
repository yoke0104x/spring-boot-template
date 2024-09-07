package xyz.content.template.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.content.template.model.entity.CommEntity;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-06 11:17
 **/
@Data
@Schema(description = "用户信息")
public class UserInfoVo extends CommEntity {

    /**
     * 用户名，必须唯一
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    private String password;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;

    /**
     * 用户手机号
     */
    @Schema(description = "用户手机号")
    private String phone;

    /**
     * 姓氏
     */
    @Schema(description = "姓氏")
    private String surname;

    /**
     * 名字
     */
    @Schema(description = "用户名字")
    private String name;
}
