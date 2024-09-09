package xyz.content.template.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-07 23:01
 **/
@Data
@Schema(description = "每日数据")
public class DataDayVo {
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 每日总数据
     */
    @Schema(description = "每日总数据")
    private int totalData;

    /**
     * 每日成功数据
     */
    @Schema(description = "每日成功数据")
    private int successData;

    /**
     * 每日失败数据
     */
    @Schema(description = "每日失败数据")
    private int failData;

    /**
     * 每日未处理数
     */
    @Schema(description = "每日未处理数")
    private int unhandledData;
}
