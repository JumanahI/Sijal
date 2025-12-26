package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.HR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HrRepository extends JpaRepository<HR,Integer> {

    HR findHRById(Integer id);
}
