package xyz.content.template.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-06 09:05
 **/
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    // 指定一条 match 规则
                    SaRouter
                            .match("/**")    // 拦截的 path 列表，可以写多个 */
                            .notMatch(
                                    "/user/login"
                            )
                            .check(r -> StpUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式
                }))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/doc.html",
                        "/doc.html*",
                        "/webjars/**",
                        "/img.icons/**",
                        "/swagger-resources/**",
                        "/**/v3/api-docs/**",
                        "/user/captcha/**",
                        "/user/save",
                        "/data/update"
                );
    }
}
