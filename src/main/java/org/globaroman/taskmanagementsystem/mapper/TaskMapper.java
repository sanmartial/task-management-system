package org.globaroman.taskmanagementsystem.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.globaroman.taskmanagementsystem.config.MapperConfig;
import org.globaroman.taskmanagementsystem.dto.task.CreateTaskRequireDto;
import org.globaroman.taskmanagementsystem.dto.task.TaskResponseDto;
import org.globaroman.taskmanagementsystem.model.Label;
import org.globaroman.taskmanagementsystem.model.Task;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class, uses = {LabelMapper.class, CommentMapper.class})
public interface TaskMapper {

    @Mapping(target = "labels", ignore = true)
    Task toModel(CreateTaskRequireDto requireDto);

    @Mapping(target = "labelsIds", ignore = true)
    TaskResponseDto toDto(Task task);

    @AfterMapping
    default void linkAttachments(@MappingTarget Task task) {
        task.getAttachments().forEach(attachment -> attachment.setTask(task));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(TaskResponseDto taskResponseDto, @MappingTarget Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(CreateTaskRequireDto createTaskRequireDto, @MappingTarget Task task);

    @AfterMapping
    default void setLabel(
            @MappingTarget Task task,
            CreateTaskRequireDto requireDto
    ) {
        Set<Label> labels = requireDto.getLabelsIds()
                .stream()
                .map(labelId -> {
                    Label label = new Label();
                    label.setId(labelId);
                    return label;
                })
                .collect(Collectors.toSet());
        task.setLabels(labels);
    }

    @AfterMapping
    default void setLabelId(
            @MappingTarget TaskResponseDto responseDto,
            Task task) {
        Set<Long> labelsIds = task.getLabels()
                .stream()
                .map(Label::getId)
                .collect(Collectors.toSet());
        responseDto.setLabelsIds(labelsIds);
    }
}
