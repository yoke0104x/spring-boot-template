package xyz.content.template.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-07 23:04
 **/
@Data
@Schema(description = "当前总数量")
public class DataDayDataVo {
    @Schema(description = "当前总数量")
    private int data;

    @Schema(description = "成功总数量")
    private int successData;

    @Schema(description = "失败总数量")
    private int failData;

    @Schema(description = "未处理总数量")
    private int unprocessedData;

    @Schema(description = "用户数量")
    private List<DataDayVo> dataDayVoList;
}
