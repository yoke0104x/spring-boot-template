package xyz.content.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import xyz.content.template.model.dto.LoginDto;
import xyz.content.template.model.dto.SaveUserDto;
import xyz.content.template.model.dto.UpdateUserDto;
import xyz.content.template.model.entity.User;
import xyz.content.template.model.vo.UserInfoVo;
import xyz.content.template.response.ResultPage;
import xyz.content.template.response.ResultResponse;

import java.util.List;

/**
* @author yoke
* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-04 11:41:46
*/
public interface UserService extends IService<User> {

    /**
     * 保存用户
     * @param saveUserDto
     * @return
     */
    ResultResponse<User> saveUser(SaveUserDto saveUserDto, HttpServletRequest request);

    /**
     * 获取用户列表
     * @return
     */
    ResultResponse<List<User>> listUser();

    /**
     * 分页获取用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<ResultPage<User>> listUserByPage(int pageNum, int pageSize);

    /**
     * 根据id获取用户
     * @param updateUserDto
     * @return
     */
    ResultResponse updateUserById(UpdateUserDto updateUserDto);

    /**
     * 获取验证码
     * @param w 验证码宽度
     * @param h 验证码高度
     * @param response
     */
    void getCaptcha(int w,int h,HttpServletResponse response);

    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    ResultResponse login(LoginDto loginDto);

    /**
     * 用户登出
     * @return
     */
    ResultResponse logout();

    /**
     * 获取用户信息
     * @return
     */
    ResultResponse<UserInfoVo> getUserInfo();

    /**
     * 更新用户信息
     * @param updateUserDto
     * @return
     */
    ResultResponse updateUserInfo(UpdateUserDto updateUserDto);
}
