package org.example.sijalsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.sijalsystem.vaildationGroups.ValidationGroup1;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name must not be empty")
    @Column(columnDefinition = "varchar(80) not null")
    private String name;

    @NotBlank(message = "Number must not be empty")
    @Column(columnDefinition = "varchar(80) not null")
    private String number;

    @NotBlank(message = "CVC must not be empty")
    @Column(columnDefinition = "varchar(80) not null")
    private String cvc;

    @NotBlank(message = "Month must not be empty")
    @Column(columnDefinition = "varchar(80) not null")
    private String month;

    @NotBlank(message = "Year must not be empty")
    @Column(columnDefinition = "varchar(80) not null")
    private String year;

    @Column(columnDefinition = "varchar(80) ")
    private String callbackUrl;

    @ManyToOne
    private Customer customer;

}
