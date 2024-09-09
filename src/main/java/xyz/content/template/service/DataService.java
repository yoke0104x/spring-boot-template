package xyz.content.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import xyz.content.template.model.dto.SearchDataPageDto;
import xyz.content.template.model.dto.UpdateDataDto;
import xyz.content.template.model.entity.DataEntry;
import xyz.content.template.model.vo.DataDayDataVo;
import xyz.content.template.response.ResultPage;
import xyz.content.template.response.ResultResponse;

import java.io.IOException;
import java.util.List;

/**
* @author yoke
* @description 针对表【data】的数据库操作Service
* @createDate 2024-09-06 13:18:45
*/
public interface DataService extends IService<DataEntry> {

    /**
     * 添加数据
     * @param phones
     * @return
     */
    ResultResponse addDataPhone(String phones);

    /**
     * 分页数据
     * @param searchDataPageDto
     * @return
     */
    ResultResponse<ResultPage<DataEntry>> pageListData(SearchDataPageDto searchDataPageDto);

    /**
     * 更新数据
     * @param updateDataDto
     * @return
     */
    ResultResponse updateDataEntryOrder(UpdateDataDto updateDataDto);

    /**
     * 导出数据
     * @param response
     * @throws IOException
     */
    void exportDataEntry(HttpServletResponse response) throws IOException;

    /**
     * 根据ID删除当前数据
     * @param id
     * @return
     */
    ResultResponse deleteDataIds(String id);

    /**
     * 获取当天数据
     * @return
     */
    ResultResponse<List<DataEntry>> getSameDayDataEntry();

    /**
     * 获取当天数据
     * @return
     */
    ResultResponse<DataDayDataVo> getDataDayData();

    /**
     * 删除所有数据
     * @return
     */
    ResultResponse deleteAllData();
}
