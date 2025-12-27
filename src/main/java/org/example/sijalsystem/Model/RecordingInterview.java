package org.example.sijalsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecordingInterview {

    @Id
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String recordingUrl;

    @Lob
    private String transcript;

    private LocalDateTime createdAt;

    @OneToOne
    @MapsId
    @JsonIgnore
    private InterviewSession interviewSession;
}