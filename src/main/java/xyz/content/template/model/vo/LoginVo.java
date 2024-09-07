package xyz.content.template.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-06 11:07
 **/
@Data
@Schema(description = "返回数据")
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "token")
    private String token;
}
