package xyz.content.template.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.content.template.enums.StatusEnum;
import xyz.content.template.mapper.DataMapper;
import xyz.content.template.model.converter.DataConverter;
import xyz.content.template.model.dto.SearchDataPageDto;
import xyz.content.template.model.dto.UpdateDataDto;
import xyz.content.template.model.entity.DataEntry;
import xyz.content.template.model.entity.User;
import xyz.content.template.model.vo.DataDayDataVo;
import xyz.content.template.response.ResultPage;
import xyz.content.template.response.ResultResponse;
import xyz.content.template.service.DataService;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
* @author yoke
* @description 针对表【data】的数据库操作Service实现
* @createDate 2024-09-06 13:18:45
*/
@Service
@Slf4j
public class DataServiceImpl extends ServiceImpl<DataMapper, DataEntry>
    implements DataService{
    @Resource
    private DataMapper dataMapper;

    @Resource
    private DataConverter dataConverter;

    @Value("${send.url}")
    private String orderUrl;

    @Async
    public String sendPhone(String phone,User user){
        log.info("请求路径:{} ",orderUrl);
        // 不必要等到这个成功才可以
        String str = HttpUtil.get(
                String.format("%s?phone=%s&agent=%s",orderUrl,phone,user.getUsername())
        );
        log.info("请求路径:{} ---- 请求结果：{}",String.format("%s?phone=%s&agent=%s",orderUrl,phone,user.getUsername()),str);
        return str;
    }

    @Override
    public ResultResponse addDataPhone(String phones){
        String[] phoneArray = phones.split(",");
        for (String phone : phoneArray) {
            if(phone.isEmpty()){
                continue;
            }
            if(phone.length() != 11){
                return ResultResponse.error(StatusEnum.PHONE_ERROR);
            }
            User user = (User) StpUtil.getSession().get("user");
            DataEntry dataEntry = new DataEntry();
            dataEntry.setPhone(phone);
            dataEntry.setUserId(StpUtil.getLoginIdAsString());
            dataEntry.setUsername(user.getUsername());
            DataEntry dataEntry1 = dataMapper.selectOne(new LambdaQueryWrapper<DataEntry>().eq(DataEntry::getPhone, phone));
            sendPhone(phone,user);


            if(dataEntry1 != null){
                return ResultResponse.error(StatusEnum.PHONE_EXIST,String.format("手机号【%s】已存在！", dataEntry1.getPhone()));
            }
            super.save(dataEntry);
        }
        return ResultResponse.success(null);
    }

    @Override
    public ResultResponse<ResultPage<DataEntry>> pageListData(SearchDataPageDto searchDataPageDto){
        if(searchDataPageDto.getSize() == 0){
            searchDataPageDto.setSize(10);
        }
        if(searchDataPageDto.getCurrent() == 0){
            searchDataPageDto.setCurrent(1);
        }
        IPage<DataEntry> page = new Page<>(searchDataPageDto.getCurrent(), searchDataPageDto.getSize());
        LambdaQueryWrapper<DataEntry> wrapper = new LambdaQueryWrapper<DataEntry>();
        wrapper.eq(DataEntry::getUserId, StpUtil.getLoginIdAsString())
                .like(StringUtils.isNotEmpty(searchDataPageDto.getPhone()), DataEntry::getPhone, searchDataPageDto.getPhone())
                .like(StringUtils.isNotEmpty(searchDataPageDto.getResults()), DataEntry::getResults, searchDataPageDto.getResults())
                .like(StringUtils.isNotEmpty(searchDataPageDto.getUsername()), DataEntry::getUsername, searchDataPageDto.getUsername());
        wrapper.orderByDesc(DataEntry::getCreateTime);
        IPage<DataEntry> result = baseMapper.selectPage(page,wrapper);
        return ResultResponse.success(new ResultPage<>(result));
    }


    @Override
    public ResultResponse updateDataEntryOrder(UpdateDataDto updateDataDto) {
        DataEntry dataEntry = baseMapper.selectOne(new LambdaQueryWrapper<DataEntry>().eq(DataEntry::getPhone, updateDataDto.getPhone()));
        if(dataEntry == null){
            return ResultResponse.error(StatusEnum.DATA_NOT_EXIST);
        }
        int rows = baseMapper.update(dataConverter.dtoToEntity(updateDataDto), new LambdaQueryWrapper<DataEntry>().eq(DataEntry::getPhone, updateDataDto.getPhone()));
        if(rows > 0){
            return ResultResponse.success(null);
        }
        return ResultResponse.error();
    }


    @Override
    public void exportDataEntry(HttpServletResponse response) throws IOException {
        List<DataEntry> list = baseMapper.selectList(new LambdaQueryWrapper<DataEntry>().eq(DataEntry::getUserId, StpUtil.getLoginIdAsString()));
        // 下载txt文件
        StringBuilder fileContent = new StringBuilder();
        for (DataEntry entry : list) {
            // 这里可以根据需要调整格式，例如：`phone:username` 或者其他格式
            fileContent.append("Phone:")
                    .append(entry.getPhone())
                    .append(" --- Username:")
                    .append(entry.getUsername());

            if(entry.getStatus() != null){
                fileContent.append(" --- Status:")
                        .append(entry.getStatus());
            }

            if(entry.getResults() != null){
                fileContent.append(" --- Results:")
                        .append(entry.getResults());
            }

            fileContent.append("\n");
        }
        // 设置响应头
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        String rawFileName = sdf.format(date) + "导出的数据.txt";
        String encodedFileName = URLEncoder.encode(rawFileName, "UTF-8");
        response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);

        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader("Content-Disposition","attachment;filename="+rawFileName);
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(fileContent.toString().getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            //LOGGER.error("导出文件文件出错:{}",e);
        } finally {try {
            buff.close();
            outStr.close();
        } catch (Exception e) {
            //LOGGER.error("关闭流对象出错 e:{}",e);
        }
        }

    }


    @Override
    public ResultResponse deleteDataIds(String id) {
        int num = baseMapper.deleteById(id);
        if(num > 0) {
            return ResultResponse.success("删除成功");
        }else{
            return ResultResponse.error(null);
        }
    }

    @Override
    public ResultResponse<List<DataEntry>> getSameDayDataEntry() {
        // 获取当天的开始时间（00:00:00）和结束时间（23:59:59）
        // 当天 00:00:00
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        // 当天 23:59:59
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        log.info("startOfDay: {}, endOfDay: {} ,currentDay: {}", startOfDay, endOfDay, LocalDateTime.now());

        // 构建查询条件
        LambdaQueryWrapper<DataEntry> queryWrapper = new LambdaQueryWrapper<>();
        // 大于等于当天 00:00:00
        queryWrapper.ge(DataEntry::getCreateTime, startOfDay)
                // 小于等于当天 23:59:59
                .le(DataEntry::getCreateTime, endOfDay);

        // 执行查询，获取当天添加的数据
        List<DataEntry> dataEntries = baseMapper.selectList(queryWrapper);

//        DataDayDataVo dataDayDataVo = new DataDayDataVo();
//        dataDayDataVo.setData(dataEntries.size());
        // 使用 forEach 遍历 dataEntries 并根据 status 统计
//        dataEntries.forEach(dataEntry -> {
//            // status 非空且非空白
//            if (dataEntry.getStatus() != null && !StringUtils.isBlank(dataEntry.getStatus())) {
//                if (dataEntry.getStatus().contains("注册失败")) {
//                    dataDayDataVo.setFailData(dataDayDataVo.getFailData() + 1);
//                } else if (dataEntry.getStatus().contains("注册成功")) {
//                    dataDayDataVo.setSuccessData(dataDayDataVo.getSuccessData() + 1);
//                }else{
//                    // 未处理的情况
//                    dataDayDataVo.setUnprocessedData(dataDayDataVo.getUnprocessedData() + 1);
//                }
//            }
//        });

//        List<DataDayVo> dataDayVos = new ArrayList<>(dataEntries.stream()
//                .reduce(new HashMap<String, DataDayVo>(), (map, entry) -> {
//                    String username = entry.getUsername();
//                    DataDayVo dataDayVo = map.getOrDefault(username, new DataDayVo());
//                    dataDayVo.setUsername(username);
//                    dataDayVo.setUserId(entry.getUserId());
//                    // 更新统计信息
//                    dataDayVo.setTotalData(dataDayVo.getTotalData() + 1);
//                    if (entry.getStatus() == null) {
//                        dataDayVo.setUnhandledData(dataDayVo.getUnhandledData() + 1);
//                    } else if (entry.getStatus().contains("注册成功")) {
//                        dataDayVo.setSuccessData(dataDayVo.getSuccessData() + 1);
//                    } else if (entry.getStatus().contains("注册失败")) {
//                        dataDayVo.setFailData(dataDayVo.getFailData() + 1);
//                    }
//
//                    map.put(username, dataDayVo);
//                    return map;
//                }, (map1, map2) -> {
//                    map2.forEach((k, v) -> map1.merge(k, v, (v1, v2) -> {
//                        v1.setTotalData(v1.getTotalData() + v2.getTotalData());
//                        v1.setUnhandledData(v1.getUnhandledData() + v2.getUnhandledData());
//                        v1.setSuccessData(v1.getSuccessData() + v2.getSuccessData());
//                        v1.setFailData(v1.getFailData() + v2.getFailData());
//                        return v1;
//                    }));
//                    return map1;
//                }).values());
//
//            dataDayDataVo.setDataDayVoList(dataDayVos);

//        dataDayDataVo.setFailData(dataEntries.stream().filter(dataEntry -> dataEntry.getStatus() != null && StringUtils.isBlank(dataEntry.getStatus()) &&  dataEntry.getStatus().equals("注册失败")).toList().size());
//        dataDayDataVo.setSuccessData(dataEntries.stream().filter(dataEntry -> dataEntry.getStatus() != null && StringUtils.isBlank(dataEntry.getStatus()) &&  dataEntry.getStatus().equals("注册成功")).toList().size());
//        dataDayDataVo.setUnprocessedData(dataEntries.size() - dataDayDataVo.getFailData() - dataDayDataVo.getSuccessData());

//        // 如果你只需要一条记录，可以使用 selectOne
//        DataEntry dataEntry = baseMapper.selectOne(queryWrapper);
        return ResultResponse.success(dataEntries);
    }


    @Override
    public ResultResponse<DataDayDataVo> getDataDayData() {
        // 获取当天的开始时间（00:00:00）和结束时间（23:59:59）
        // 当天 00:00:00
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        // 当天 23:59:59
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        // 构建查询条件
        LambdaQueryWrapper<DataEntry> queryWrapper = new LambdaQueryWrapper<>();
        // 大于等于当天 00:00:00
        queryWrapper.eq(DataEntry::getUserId, StpUtil.getLoginIdAsString())
                .ge(DataEntry::getCreateTime, startOfDay)
                // 小于等于当天 23:59:59
                .le(DataEntry::getCreateTime, endOfDay);

        // 执行查询，获取当天添加的数据
        List<DataEntry> dataEntries = baseMapper.selectList(queryWrapper);

        DataDayDataVo dataDayDataVo = new DataDayDataVo();
        dataDayDataVo.setData(dataEntries.size());
        // 使用 forEach 遍历 dataEntries 并根据 status 统计
        dataEntries.forEach(dataEntry -> {
            // status 非空且非空白
            if (dataEntry.getStatus() != null && !StringUtils.isBlank(dataEntry.getStatus())) {
                if (dataEntry.getStatus().contains("注册失败")) {
                    dataDayDataVo.setFailData(dataDayDataVo.getFailData() + 1);
                } else if (dataEntry.getStatus().contains("注册成功")) {
                    dataDayDataVo.setSuccessData(dataDayDataVo.getSuccessData() + 1);
                }else{
                    // 未处理的情况
                    dataDayDataVo.setUnprocessedData(dataDayDataVo.getUnprocessedData() + 1);
                }
            }
        });
        return ResultResponse.success(dataDayDataVo);
    }

    @Override
    public ResultResponse deleteAllData(){
        int delete = baseMapper.delete(null);
        if(delete == 0){
            return ResultResponse.error();
        }
        return ResultResponse.success(delete);
    }

    @Override
    public ResultResponse resetSendPhone(String phone){
       User user = (User) StpUtil.getSession().get("user");
        return ResultResponse.success(sendPhone(phone,user));
    }
}




