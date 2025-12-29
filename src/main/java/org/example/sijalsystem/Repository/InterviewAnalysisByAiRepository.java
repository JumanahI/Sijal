package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.InterviewAnalysisByAi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewAnalysisByAiRepository extends JpaRepository<InterviewAnalysisByAi,Integer> {
    InterviewAnalysisByAi findByInterviewSession_Id(Integer sessionId);

    List<InterviewAnalysisByAi> findByInterviewSession_Customer_Id(Integer customerId);

}
