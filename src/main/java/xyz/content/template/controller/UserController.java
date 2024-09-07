package xyz.content.template.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.content.template.model.dto.LoginDto;
import xyz.content.template.model.dto.PageDto;
import xyz.content.template.model.dto.SaveUserDto;
import xyz.content.template.model.dto.UpdateUserDto;
import xyz.content.template.model.entity.User;
import xyz.content.template.model.vo.UserInfoVo;
import xyz.content.template.response.ResultPage;
import xyz.content.template.response.ResultResponse;
import xyz.content.template.service.UserService;

import java.util.List;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @program: user
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 09:24
 **/
@RestController
@RequestMapping("/user")
@Tag(name = "用户", description = "用户相关接口")
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "保存用户")
    @PostMapping("/save")
    public ResultResponse<User> save(@Validated @RequestBody SaveUserDto saveUserDto, HttpServletRequest request) {
        return userService.saveUser(saveUserDto,request);
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResultResponse<User> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public ResultResponse<List<User>> list() {
        return userService.listUser();
    }

    @Operation(summary = "分页获取用户列表")
    @PostMapping("/list/page")
    public ResultResponse<ResultPage<User>> listPage(@RequestBody PageDto pageDto) {
        return userService.listUserByPage(pageDto.getCurrent(), pageDto.getSize());
    }

    @Operation(summary = "根据id修改用户")
    @PostMapping("/update")
    public ResultResponse<User> update(@Validated @RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUserById(updateUserDto);
    }

    @Operation(summary = "获取图形验证码")
    @GetMapping("/captcha/{w}/{h}")
    public void getCaptcha(@PathVariable("w") String w , @PathVariable("h") String h , HttpServletResponse response) {
        int width = Integer.parseInt(w);
        int height = Integer.parseInt(h);
        userService.getCaptcha(width,height,response);
    }

    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public ResultResponse<String> logout() {
        return userService.logout();
    }


    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/info")
    public ResultResponse<UserInfoVo> info() {
        return userService.getUserInfo();
    }

    @Operation(summary = "更新当前用户")
    @PostMapping("/update/current")
    public ResultResponse<User> updateCurrent(@Validated @RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUserInfo(updateUserDto);
    }
}
