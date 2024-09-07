package xyz.content.template.model.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.content.template.model.dto.UpdateDataDto;
import xyz.content.template.model.entity.DataEntry;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-06 15:06
 **/
@Mapper(componentModel = "spring")
public interface DataConverter {
    DataConverter INSTANCE = Mappers.getMapper(DataConverter.class);

    /**
     * dto to entity
     */
    DataEntry dtoToEntity(UpdateDataDto dto);
}
