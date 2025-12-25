package org.example.sijalsystem.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    // Upload CV (PDF path)
    @Column(name = "cv_path")
    private String cvPath;



    @OneToOne(mappedBy = "customer")
    private GenerateCV generateCV;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

