package org.globaroman.taskmanagementsystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.globaroman.taskmanagementsystem.dto.label.CreateLabelRequireDto;
import org.globaroman.taskmanagementsystem.dto.label.LabelResponseDto;
import org.globaroman.taskmanagementsystem.mapper.LabelMapper;
import org.globaroman.taskmanagementsystem.model.Color;
import org.globaroman.taskmanagementsystem.model.Label;
import org.globaroman.taskmanagementsystem.repository.LabelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
class LabelServiceImplTest {

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private LabelMapper labelMapper;
    @InjectMocks
    private LabelServiceImpl labelService;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Save a new label -> receive successful result")
    void save_SaveLabel_ShouldReturnLabelResponseDto() {
        CreateLabelRequireDto requireDto = createRequireDto();
        Label label = createLabelForTest();
        LabelResponseDto responseDto = createResponseDto();

        Mockito.when(labelMapper.toModel(requireDto)).thenReturn(label);
        Mockito.when(labelRepository.save(label)).thenReturn(label);
        Mockito.when(labelMapper.toDto(label)).thenReturn(responseDto);

        LabelResponseDto responseDtoResult = labelService.save(requireDto);

        Assertions.assertNotNull(responseDtoResult);
        Assertions.assertEquals(1L, responseDtoResult.getId());
    }

    @Test
    @DisplayName("Get all labels")
    void getAll_GetAllSavedLabelsOk() {
        Label label = createLabelForTest();
        LabelResponseDto responseDto = createResponseDto();
        List<Label> labels = new ArrayList<>();
        labels.add(label);
        List<LabelResponseDto> responseDtos = new ArrayList<>();
        responseDtos.add(responseDto);

        Mockito.when(labelRepository.findAll()).thenReturn(labels);
        Mockito.when(labelMapper.toDto(Mockito.any(Label.class)))
                .thenReturn(new LabelResponseDto());

        List<LabelResponseDto> result = labelService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Update Label -> return LabelResponseDto")
    void update_UpdateValidLabel_ReturnUpdatedLabelResponseDto() {
        CreateLabelRequireDto requireDtoUpdate = createRequireDto();
        requireDtoUpdate.setColor(Color.RED);

        Label updateLabel = createLabelForTest();
        updateLabel.setColor(Color.RED);

        LabelResponseDto responseDto = createResponseDto();
        responseDto.setColor(Color.RED);
        Label existLabel = createLabelForTest();

        Mockito.when(labelRepository.findById(existLabel.getId()))
                .thenReturn(Optional.of(existLabel));
        Mockito.when(labelRepository.save(updateLabel)).thenReturn(updateLabel);
        Mockito.lenient().when(labelMapper.toDto(Mockito.any(Label.class))).thenReturn(responseDto);

        LabelResponseDto result = labelService.update(existLabel.getId(), requireDtoUpdate);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseDto, result);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete existing Label by ID")
    void deleteById_DeleteExistingLabel_ShouldSuccessDeleteOk() {
        Label label = createLabelForTest();

        labelService.deleteById(label.getId());

        Mockito.verify(labelRepository, Mockito.times(1)).deleteById(label.getId());
    }

    private Label createLabelForTest() {
        Label label = new Label();
        label.setId(1L);
        label.setName("Label 1");
        label.setColor(Color.BLUE);
        return label;
    }

    private CreateLabelRequireDto createRequireDto() {
        CreateLabelRequireDto requireDto = new CreateLabelRequireDto();
        requireDto.setName("Label 1");
        requireDto.setColor(Color.BLUE);
        return requireDto;
    }

    private LabelResponseDto createResponseDto() {
        LabelResponseDto responseDto = new LabelResponseDto();
        responseDto.setName("Label 1");
        responseDto.setColor(Color.BLUE);
        responseDto.setId(1L);
        return responseDto;
    }
}
