package xyz.content.template.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-06 13:54
 **/
@Data
@Schema(description = "分页查询数据")
public class SearchDataPageDto extends PageDto {

    /**
     * 用户
     */
    @Schema(description = "用户")
    private String username;

    /**
     * 号码
     */
    @Schema(description = "号码")
    private String phone;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 反馈结果
     */
    @Schema(description = "反馈结果")
    private String results;
}
