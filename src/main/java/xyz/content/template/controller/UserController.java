package xyz.content.template.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.content.template.model.dto.SaveUserDto;
import xyz.content.template.model.entity.User;
import xyz.content.template.response.ResultResponse;
import xyz.content.template.service.UserService;

import java.util.List;

/**
 * @program: user
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 09:24
 **/
@RestController
@RequestMapping("/list")
@Tag(name = "用户", description = "用户相关接口")
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "获取用户数量")
    @GetMapping("/hello")
    public String hello(){
        String num = userService.getUserNum();
        return "hello word" + "" + num;
    }

    @Operation(summary = "保存用户")
    @PostMapping("/save")
    public ResultResponse<User> save(@Validated @RequestBody SaveUserDto saveUserDto){
        return userService.saveUser(saveUserDto);
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public ResultResponse<List<User>> list(){
        return userService.listUser();
    }
}
