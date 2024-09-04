package xyz.content.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.content.template.model.dto.SaveUserDto;
import xyz.content.template.model.entity.User;
import xyz.content.template.response.ResultResponse;

import java.util.List;

/**
* @author yoke
* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-04 11:41:46
*/
public interface UserService extends IService<User> {

    /**
     * 获取用户数量
     * @return
     */
    String getUserNum();


    /**
     * 保存用户
     * @param saveUserDto
     * @return
     */
    ResultResponse<User> saveUser(SaveUserDto saveUserDto);

    /**
     * 获取用户列表
     * @return
     */
    ResultResponse<List<User>> listUser();
}
