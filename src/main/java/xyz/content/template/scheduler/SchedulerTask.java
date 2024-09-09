package xyz.content.template.scheduler;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.content.template.service.DataService;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-08 16:08
 **/
@Component
@Slf4j
public class SchedulerTask {

    @Resource
    private DataService dataService;

    @Scheduled(cron="59 59 23 * * ? ")
    private void process(){
        log.info("定时任务开始");
        log.info("开始清空表内数据");
        dataService.deleteAllData();
        log.info("清空表内数据完成");
    }
}
