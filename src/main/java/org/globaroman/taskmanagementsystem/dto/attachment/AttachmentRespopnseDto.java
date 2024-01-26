package org.globaroman.taskmanagementsystem.dto.attachment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentRespopnseDto implements Serializable {
    private Long id;
    private String dropBoxId;
    private String fileName;
    private LocalDate uploadDate;
}
