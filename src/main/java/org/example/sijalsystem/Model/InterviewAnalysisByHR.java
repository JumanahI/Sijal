package org.example.sijalsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewAnalysisByHR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Strengths field cannot be empty")
    @Size(min = 15, max = 250, message = "Strengths must be between 15 and 250 characters")
    @Column(columnDefinition = "varchar(250) not null")
    private String strengths;

    @NotBlank(message = "Weaknesses field cannot be empty")
    @Size(min = 15, max = 250, message = "Weaknesses must be between 15 and 250 characters")
    @Column(columnDefinition = "varchar(250) not null")
    private String weaknesses;

    @Min(value = 0, message = "Final score must be at least 0")
    @Max(value = 100, message = "Final score cannot exceed 100")
    @Column(columnDefinition = "int not null")
    private Integer finalScore;

    @OneToOne
    @MapsId
    @JoinColumn(name = "interview_with_HR")
    private InterviewWithHR interviewWithHR;

}
