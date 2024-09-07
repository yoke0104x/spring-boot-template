package xyz.content.template.service.impl;

import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.content.template.enums.StatusEnum;
import xyz.content.template.mapper.UserMapper;
import xyz.content.template.model.converter.UserConverter;
import xyz.content.template.model.dto.LoginDto;
import xyz.content.template.model.dto.SaveUserDto;
import xyz.content.template.model.dto.UpdateUserDto;
import xyz.content.template.model.entity.User;
import xyz.content.template.model.vo.LoginVo;
import xyz.content.template.model.vo.UserInfoVo;
import xyz.content.template.response.ResultPage;
import xyz.content.template.response.ResultResponse;
import xyz.content.template.service.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yoke
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-09-04 11:41:46
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserConverter userConverter;

    @Resource
    private SaTokenDaoRedisJackson saTokenDaoRedisJackson;

    /**
     * 添加用户
     *
     * @param saveUserDto
     * @return
     */
    @Override
    public ResultResponse<User> saveUser(SaveUserDto saveUserDto, HttpServletRequest request) {

        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, saveUserDto.getUsername()));
        if (user != null) {
            return ResultResponse.error(StatusEnum.USERNAME_EXIST);
        }

        Cookie[] cookies = request.getCookies();
        Cookie[] filter = ArrayUtil.filter(cookies, cookie -> "captcha-token".equals(cookie.getName()));
        String redisCaptchaToken = "captcha-token:" + filter[0].getValue();
        String captcha = saTokenDaoRedisJackson.get(redisCaptchaToken);
        if (captcha == null) {
            return ResultResponse.error(StatusEnum.CAPTCHA_EXPIRED);
        }
        String captchaCodeUpper = captcha.toUpperCase();
        String codeUpper = saveUserDto.getCode().toUpperCase();
        if (!captchaCodeUpper.equals(codeUpper)) {
            return ResultResponse.error(StatusEnum.CAPTCHA_ERROR);
        }
        User userDaoTo = userConverter.userDaoTo(saveUserDto);
        userDaoTo.setPassword(SecureUtil.md5(userDaoTo.getPassword()));
        boolean isSaved = super.save(userDaoTo);
        userDaoTo.setPassword(null);
        if (!isSaved) {
            return ResultResponse.error();
        }
        return ResultResponse.success(userDaoTo);
    }

    @Override
    public ResultResponse<List<User>> listUser() {
        List<User> list = super.list();
        list.stream().map(user -> {
            user.setPassword(null);
            return user;
        }).toList();
        return ResultResponse.success(list);
    }

    @Override
    public ResultResponse<ResultPage<User>> listUserByPage(int pageNum, int pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> result = baseMapper.selectPage(page, null);
        result.getRecords().stream().map(user -> {
            user.setPassword(null);
            return user;
        }).toList();
        return ResultResponse.success(ResultPage.of(result));
    }

    @Override
    public ResultResponse updateUserById(UpdateUserDto updateUserDto) {
        updateUserDto.setUpdateTime(LocalDateTime.now());
        int affectedRows = baseMapper.updateUserById(updateUserDto);
        if (affectedRows != 1) {
            return ResultResponse.error();
        }
        return ResultResponse.success(null);
    }

    @Override
    public void getCaptcha(int w, int h, HttpServletResponse response) {
        CircleCaptcha captcha = new CircleCaptcha(w, h, 4, 20);
        String uuid = UUID.randomUUID().toString();
        log.info("验证码:{} ----- UUID:{}", captcha.getCode(), uuid);
        try (ServletOutputStream out = response.getOutputStream()) {
            // 将验证码写入响应输出流
            captcha.write(out);
            Cookie cookie = new Cookie("captcha-token", uuid);
            cookie.setPath("/");
            response.addCookie(cookie);
            saTokenDaoRedisJackson.set("captcha-token:" + uuid, captcha.getCode(), 60);
            // 自动关闭输出流，因为 try-with-resources 会自动管理流的关闭
        } catch (IOException e) {
            // 处理 IO 异常
            e.printStackTrace();
            // 可以返回一些错误信息或日志记录
        }
    }

    @Override
    public ResultResponse login(LoginDto loginDto) {
        // 获取用户
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        if (user == null) {
            return ResultResponse.error(StatusEnum.USER_NOT_EXIST);
        }
        String pass = SecureUtil.md5(loginDto.getPassword());
        if (!user.getPassword().equals(pass)) {
            return ResultResponse.error(StatusEnum.PASSWORD_ERROR);
        }
        // 登录成功，生成token
        StpUtil.login(user.getId());
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(StpUtil.getTokenValue());
        // 将用户信息存入session
        StpUtil.getSession().set("user", user);
        return ResultResponse.success(loginVo);
    }

    @Override
    public ResultResponse logout() {
        StpUtil.logout();
        return ResultResponse.success(null);
    }

    @Override
    public ResultResponse<UserInfoVo> getUserInfo() {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, StpUtil.getLoginId()));
        if (user == null) {
            return ResultResponse.error(StatusEnum.USER_NOT_EXIST);
        }
        UserInfoVo userInfoVo = userConverter.userToVo(user);
        userInfoVo.setName(user.getName());
        return ResultResponse.success(userInfoVo);
    }

    @Override
    public ResultResponse updateUserInfo(UpdateUserDto updateUserDto) {
        User user = userConverter.dtoToEntity(updateUserDto);
        user.setName(updateUserDto.getName());
        user.setPassword(SecureUtil.md5(updateUserDto.getPassword()));
        int affectedRows = baseMapper.updateById(user);
        StpUtil.getSession().set("user",user);
        if (affectedRows != 1) {
            return ResultResponse.error();
        }
        return ResultResponse.success(null);
    }
}




