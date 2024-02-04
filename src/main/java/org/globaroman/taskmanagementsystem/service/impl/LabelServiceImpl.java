package org.globaroman.taskmanagementsystem.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.globaroman.taskmanagementsystem.dto.label.CreateLabelRequireDto;
import org.globaroman.taskmanagementsystem.dto.label.LabelResponseDto;
import org.globaroman.taskmanagementsystem.exception.EntityNotFoundCustomException;
import org.globaroman.taskmanagementsystem.mapper.LabelMapper;
import org.globaroman.taskmanagementsystem.model.Label;
import org.globaroman.taskmanagementsystem.repository.LabelRepository;
import org.globaroman.taskmanagementsystem.service.LabelService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public LabelResponseDto save(CreateLabelRequireDto requireDto) {
        Label label = labelMapper.toModel(requireDto);
        return labelMapper.toDto(labelRepository.save(label));
    }

    @Override
    public List<LabelResponseDto> getAll() {
        return labelRepository.findAll().stream()
                .map(labelMapper::toDto)
                .toList();
    }

    @Override
    public LabelResponseDto update(Long id, CreateLabelRequireDto requireDto) {
        Label label = labelRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundCustomException("Can not find label with id: " + id)
        );
        label.setColor(requireDto.getColor());
        label.setName(requireDto.getName());
        return labelMapper.toDto(labelRepository.save(label));
    }

    @Override
    public void deleteById(Long id) {
        labelRepository.deleteById(id);
    }
}
