package xyz.content.template.model.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.content.template.model.dto.SaveUserDto;
import xyz.content.template.model.entity.User;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 16:12
 **/
@Mapper(componentModel = "spring")
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * dto转user
     * @param userDto
     * @return
     */
    User userDaoTo(SaveUserDto userDto);
}
