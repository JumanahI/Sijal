package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card,Integer> {

    Card findCardById(Integer id);

    List<Card> findCardByCustomerId(Integer customer_id);
}
