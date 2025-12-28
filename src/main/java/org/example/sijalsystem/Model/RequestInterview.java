package org.example.sijalsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Message must not be null")
    @Size(min = 4, max = 150, message = "Message size 4-150 characters")
    @Column(columnDefinition = "varchar(150) not null")
    private String message;

    @Pattern(regexp = "^(APPROVE|PENDING|REJECTED)$",message = "Status must be either APPROVE,PENDING or REJECTED")
    @Column(columnDefinition = "varchar(20) ")
    private String status;

    @NotNull(message = "Start time must not be null")
    @Column(columnDefinition = "dateTime not null")
    private LocalDate startTime;

    @CreationTimestamp
    @Column(columnDefinition = "dateTime ")
    private LocalDate create_at;

    //------------------------relation----------------------------


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "hr_id")
    private HR hr;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "request")
    @PrimaryKeyJoinColumn
    private InterviewWithHR interviewWithHR;

}
