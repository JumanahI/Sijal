package org.example.sijalsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(250) not null")
    private String about;

    @Column(columnDefinition = "varchar(250) not null")
    private String experience;

    @OneToOne
    @MapsId
    private User user;


    @OneToMany(cascade = CascadeType.ALL , mappedBy = "hr")
    @JsonIgnore
    private Set<RequestInterview> requestInterviewSet;


}
