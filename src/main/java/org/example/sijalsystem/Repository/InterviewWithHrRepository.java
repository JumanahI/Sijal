package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.InterviewWithHR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewWithHrRepository extends JpaRepository<InterviewWithHR, Integer> {

    InterviewWithHR findInterviewWithHrById(Integer id);
}
