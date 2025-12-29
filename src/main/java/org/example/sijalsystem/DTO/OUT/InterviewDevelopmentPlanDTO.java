package org.example.sijalsystem.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDevelopmentPlanDTO {

    private List<String> strengthsEnhancement;
    private List<String> weaknessesImprovement;
    private List<String> skillRecommendations;
    private List<String> generalCareerTips;

}
