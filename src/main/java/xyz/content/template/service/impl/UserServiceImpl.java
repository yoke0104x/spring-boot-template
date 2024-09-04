package xyz.content.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import xyz.content.template.mapper.UserMapper;
import xyz.content.template.model.entity.User;
import xyz.content.template.service.UserService;

/**
* @author yoke
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-04 11:41:46
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public String getUserNum() {
        int num = baseMapper.getUserNum();
        return ""+num;
    }
}




