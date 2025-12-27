package org.example.sijalsystem.DTO.IN;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvDataDTO {

    private String summary;
    private String skills;
    private String education;
    private String experience;

}
