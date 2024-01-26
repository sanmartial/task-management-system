package org.globaroman.taskmanagementsystem.dto.attachment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAttachmentRequireDto {
    @NotBlank
    private String fileName;
    @NotBlank
    private String filePath;
}
