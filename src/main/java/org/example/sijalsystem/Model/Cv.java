package org.example.sijalsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cv {

    @Id
    private Integer id;

    @Lob
    private String summary;

    @Lob
    private String skills;

    @Lob
    private String education;

    @Lob
    private String experience;

    private Boolean isGenerated;


    private LocalDateTime createdAt;



    @OneToOne
    @MapsId
    @JsonIgnore
    private Customer customer;


}
