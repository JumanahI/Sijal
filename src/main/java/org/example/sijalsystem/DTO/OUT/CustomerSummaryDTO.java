package org.example.sijalsystem.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSummaryDTO {

    private String summary;
    private List<String> keySkills;
    private List<String> careerHighlights;
}
