package org.example.sijalsystem.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    private Integer id;

    @OneToOne
    @MapsId
    private User user;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "customer")
    @PrimaryKeyJoinColumn
    private CV cv;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private Set<InterviewSession> sessions;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "customer")
    @JsonIgnore
    private Set<RequestInterview> requestInterviewSet;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "customer")
    @JsonIgnore
    private Set<Card> cardSet;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "customer")
    @JsonIgnore
    private Set<Subscription> subscriptionSet;
}

