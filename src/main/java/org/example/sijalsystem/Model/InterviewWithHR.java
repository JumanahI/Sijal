package org.example.sijalsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewWithHR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Status must not be null")
    @Pattern(regexp = "^(UPCOMING|RUNNING|COMPLETE)$",message = "Status must be either UPCOMING, RUNNING  or COMPLETE")
    @Column(columnDefinition = "varchar(20) not null")
    private String status;

    @NotBlank(message = "Status must not be null")
    @Column(columnDefinition = "varchar(70) not null")
    private String meetingURL;

    @CreationTimestamp
    @Column(columnDefinition = "dateTime ")
    private LocalDate create_at;

    //------------------------relation----------------------------

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "interviewWithHR")
    @JsonIgnore
    private InterviewAnalysisByHR interviewAnalysisByHR;

    @OneToOne
    @JsonIgnore
    private RequestInterview requestInterview;

}
