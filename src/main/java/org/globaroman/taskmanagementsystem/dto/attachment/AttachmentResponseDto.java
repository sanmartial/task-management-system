package org.globaroman.taskmanagementsystem.dto.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponseDto implements Serializable {
    private Long id;
    private String dropBoxId;
    private String fileName;
    private LocalDate uploadDate;
}
