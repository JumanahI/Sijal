package org.example.sijalsystem.DTO.OUT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CVRecommendationDTO {

    List<String> summarySuggestions;
    List<String> skillsSuggestions;
    List<String> experienceSuggestions;
    List<String> generalTips;
}

