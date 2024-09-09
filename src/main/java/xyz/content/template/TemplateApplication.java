package xyz.content.template;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yoke
 */
@SpringBootApplication
@MapperScan("xyz.content.template.mapper")
@EnableScheduling
public class TemplateApplication {

	public static void main(String[] args) {
		// 修改时区
		System.setProperty("user.timezone", "Asia/Shanghai");
		SpringApplication.run(TemplateApplication.class, args);
		System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
	}
}
