package xyz.content.template.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import xyz.content.template.model.entity.CommEntity;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-05 10:01
 **/
@Data
@Schema(description = "更新用户信息")
public class UpdateUserDto extends CommEntity {

    /**
     * 用户ID，非自增
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "用户ID")
    @NotEmpty(message = "用户ID不能为空")
    private String id;

    /**
     * 用户名，必须唯一
     */
    @Schema(description = "用户名")
    private String username;

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
}
