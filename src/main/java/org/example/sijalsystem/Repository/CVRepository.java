package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CVRepository extends JpaRepository<CV, Integer> {
    CV findCVByCustomerId(Integer customerId);

    CV findCVById(Integer id);

    boolean existsByCustomerId(Integer customerId);
}
