package xyz.content.template.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.content.template.mapper.UserMapper;
import xyz.content.template.model.converter.UserConverter;
import xyz.content.template.model.dto.SaveUserDto;
import xyz.content.template.model.dto.UpdateUserDto;
import xyz.content.template.model.entity.User;
import xyz.content.template.response.ResultPage;
import xyz.content.template.response.ResultResponse;
import xyz.content.template.service.UserService;

import java.time.LocalDateTime;
import java.util.Date;
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
        return "" + num;
    }

    /**
     * 添加用户
     *
     * @param saveUserDto
     * @return
     */
    @Override
    public ResultResponse<User> saveUser(SaveUserDto saveUserDto) {
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
        if(affectedRows != 1){
            return ResultResponse.error();
        }
        return ResultResponse.success(null);
    }
}




