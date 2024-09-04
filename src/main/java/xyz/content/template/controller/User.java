package xyz.content.template.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.content.template.service.UserService;

/**
 * @program: user
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 09:24
 **/
@RestController
@RequestMapping("/list")
public class User {

    @Resource
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        String num = userService.getUserNum();
        return "hello word" + "" + num;
    }
}
