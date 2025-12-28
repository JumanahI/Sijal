package org.example.sijalsystem.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String number;

    private String cvc;

    private String month;

    private String year;

    private String callbackUrl;

    @ManyToOne
    private Customer customer;

}
