package org.globaroman.taskmanagementsystem.mapper;

import org.globaroman.taskmanagementsystem.dto.project.ProjectResponseDto;
import org.globaroman.taskmanagementsystem.config.MapperConfig;
import org.globaroman.taskmanagementsystem.dto.project.CreateProjectRequestDto;
import org.globaroman.taskmanagementsystem.model.Project;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class, uses = {TaskMapper.class})
public interface ProjectMapper {

    Project toModel(CreateProjectRequestDto createProjectRequestDto);

    ProjectResponseDto toDto(Project project);

    @AfterMapping
    default void linkTasks(@MappingTarget Project project) {
        project.getTasks().forEach(task -> task.setProject(project));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(ProjectResponseDto projectResponseDto,
                          @MappingTarget Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(CreateProjectRequestDto createProjectRequestDto,
                          @MappingTarget Project project);
}
