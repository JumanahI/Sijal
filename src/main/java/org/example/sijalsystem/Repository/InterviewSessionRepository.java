package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession,Integer> {
    InterviewSession findInterviewSessionById(Integer id);
}
