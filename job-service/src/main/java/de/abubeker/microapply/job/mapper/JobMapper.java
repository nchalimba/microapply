package de.abubeker.microapply.job.mapper;

import de.abubeker.microapply.job.dto.JobDto;
import de.abubeker.microapply.job.model.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JobMapper {
    JobDto toDto(Job job);

    @Mapping(target = "id", ignore = true)
    Job toEntity(JobDto jobDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(JobDto dto, @MappingTarget Job job);
}
