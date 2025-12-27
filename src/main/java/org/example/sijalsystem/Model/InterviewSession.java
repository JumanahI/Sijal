package org.example.sijalsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InterviewSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "interviewSession")
    private Set<Question> questions;


    @OneToOne(cascade = CascadeType.ALL,mappedBy = "interviewSession")
    @PrimaryKeyJoinColumn
    private RecordingInterview recordingInterview;
}
