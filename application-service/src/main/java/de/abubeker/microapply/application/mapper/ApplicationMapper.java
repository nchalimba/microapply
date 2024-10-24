package de.abubeker.microapply.application.mapper;

import de.abubeker.microapply.application.dto.ApplicationDto;
import de.abubeker.microapply.application.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    ApplicationDto toDto(Application application);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applicationDate", ignore = true)
    Application toEntity(ApplicationDto applicationDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applicationDate", ignore = true)
    void updateEntityFromDto(ApplicationDto dto, @MappingTarget Application application);
}
