package org.globaroman.taskmanagementsystem.dto.attachment;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponseDto {
    private Long id;
    private String dropBoxId;
    private String fileName;
    private LocalDate uploadDate;
}
