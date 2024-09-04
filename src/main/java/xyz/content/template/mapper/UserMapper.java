package xyz.content.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.content.template.model.entity.User;

/**
* @author yoke
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-09-04 11:41:46
* @Entity /xyz/content/template.User
*/
public interface UserMapper extends BaseMapper<User> {
    int getUserNum();
}




