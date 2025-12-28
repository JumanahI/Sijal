package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Integer> {

    Card findCardById(Integer id);
}
