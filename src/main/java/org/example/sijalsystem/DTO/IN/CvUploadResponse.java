package org.example.sijalsystem.DTO.IN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvUploadResponse {
    private Integer cvId;
    private String message;
    private CvDataDTO extractedData;
}
