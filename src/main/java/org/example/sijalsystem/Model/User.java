package org.example.sijalsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String phoneNumber;

    private String email;

    private String username;

    private String name;

    private String password;

    private String age;

    private String role;

    private LocalDate createdAt;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonIgnore
    private Customer customer;

    //relation with hr
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonIgnore
    private HR hr;

}
