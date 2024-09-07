package xyz.content.template.model.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import xyz.content.template.model.dto.SaveUserDto;
import xyz.content.template.model.dto.UpdateUserDto;
import xyz.content.template.model.entity.User;
import xyz.content.template.model.vo.UserInfoVo;

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


    /**
     * user转vo
     * @param user
     * @return
     */
    @Mappings(value = {
            @Mapping(target = "password", ignore = true),
    })
    UserInfoVo userToVo(User user);

    /**
     * Dto转Entity
     * @param userDto
     * @return
     */
    User dtoToEntity(UpdateUserDto userDto);
}
