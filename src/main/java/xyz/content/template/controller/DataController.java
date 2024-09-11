package xyz.content.template.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import xyz.content.template.model.dto.SearchDataPageDto;
import xyz.content.template.model.dto.UpdateDataDto;
import xyz.content.template.model.entity.DataEntry;
import xyz.content.template.model.vo.DataDayDataVo;
import xyz.content.template.response.ResultResponse;
import xyz.content.template.service.DataService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-06 13:25
 **/
@RestController
@RequestMapping("/data")
@Tag(name = "数据接口")
public class DataController {
    @Resource
    private DataService dataService;

    @Operation(summary = "添加数据")
    @PostMapping("/add")
    public ResultResponse add(@RequestBody Map<String, Object> data){
        String phones = data.get("phones").toString();
        return dataService.addDataPhone(phones);
    }

    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public ResultResponse page(@ModelAttribute SearchDataPageDto searchDataPageDto){
        return dataService.pageListData(searchDataPageDto);
    }

    @Operation(summary = "修改数据")
    @PostMapping("/update")
    public ResultResponse update(@RequestBody UpdateDataDto data){
        return dataService.updateDataEntryOrder(data);
    }


    @Operation(summary = "导出数据")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        dataService.exportDataEntry(response);
    }


    @Operation(summary = "删除数据")
    @DeleteMapping("/delete/{id}")
    public ResultResponse delete(@PathVariable("id") String id){
        return dataService.deleteDataIds(id);
    }

    @Operation(summary = "获取当天的数据")
    @GetMapping("/today")
    public ResultResponse<List<DataEntry>> getTodayData() {
        return dataService.getSameDayDataEntry();
    }

    @Operation(summary = "获取用户今日数据")
    @GetMapping("/today/user")
    public ResultResponse<DataDayDataVo> getUserTodayData() {
        return dataService.getDataDayData();
    }

    @Operation(summary = "重新发送手机号")
    @GetMapping("/resend")
    public ResultResponse resend(@RequestParam("phone") String phone){
        return dataService.resetSendPhone(phone);
    }
}
