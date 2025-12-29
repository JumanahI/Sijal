package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession,Integer> {
    InterviewSession findInterviewSessionById(Integer id);

    List<InterviewSession> findInterviewSessionsByCustomerId(Integer customerId);

    boolean existsByIdAndCustomer_Id(Integer sessionId, Integer customerId);
}
