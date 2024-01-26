package org.globaroman.taskmanagementsystem.dto.attachment;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Value;

@Data
public class CreateAttachmentRequireDto implements Serializable {
    @NotBlank
    private String fileName;
    @NotBlank
    private String filePath;
}
