package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.GenerateCV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerateCVRepository extends JpaRepository<GenerateCV , Integer> {
}
