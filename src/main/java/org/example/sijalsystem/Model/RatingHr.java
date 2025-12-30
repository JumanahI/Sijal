package org.example.sijalsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sijalsystem.vaildationGroups.ValidationGroup1;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RatingHr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Comment must not be empty")
    @Column(columnDefinition = "varchar(250)")
    private String comment;

    @NotNull(message = "Start must not be empty")
    @Column(columnDefinition ="int ")
    private Integer stars;


    @ManyToOne
    private Customer customer;

    @ManyToOne
    private HR hr;
}
