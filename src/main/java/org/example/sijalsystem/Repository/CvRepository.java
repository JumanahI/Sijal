package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<Cv, Integer> {
}
