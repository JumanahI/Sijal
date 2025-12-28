package org.example.sijalsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cv")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String summary;

    @Lob
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String education;

    @Column(columnDefinition = "TEXT")
    private String experience;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}