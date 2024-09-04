package xyz.content.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.content.template.model.entity.User;

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
}
