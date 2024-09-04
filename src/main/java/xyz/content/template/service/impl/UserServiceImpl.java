package xyz.content.template.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.content.template.mapper.UserMapper;
import xyz.content.template.model.converter.UserConverter;
import xyz.content.template.model.dto.SaveUserDto;
import xyz.content.template.model.entity.User;
import xyz.content.template.response.ResultResponse;
import xyz.content.template.service.UserService;

import java.util.List;

/**
* @author yoke
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-04 11:41:46
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserConverter userConverter;

    @Override
    public String getUserNum() {
        int num = baseMapper.getUserNum();
        return ""+num;
    }

    /**
     * 添加用户
     * @param saveUserDto
     * @return
     */
    @Override
    public ResultResponse<User> saveUser(SaveUserDto saveUserDto) {
        User userDaoTo = userConverter.userDaoTo(saveUserDto);
        userDaoTo.setPassword(SecureUtil.md5(userDaoTo.getPassword()));
        boolean isSaved = super.save(userDaoTo);
        userDaoTo.setPassword(null);
        if(!isSaved){
            return ResultResponse.error();
        }
        return ResultResponse.success(userDaoTo);
    }

    @Override
    public ResultResponse<List<User>> listUser() {
        List<User> list = super.list();
        return ResultResponse.success(list.stream().map(user -> {
            user.setPassword(null);
            return user;
        }).toList());
    }
}




