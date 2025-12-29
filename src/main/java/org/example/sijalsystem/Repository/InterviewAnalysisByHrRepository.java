package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.InterviewAnalysisByHR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewAnalysisByHrRepository extends JpaRepository<InterviewAnalysisByHR,Integer> {

    InterviewAnalysisByHR findByInterviewWithHR_Id(Integer id);

    List<InterviewAnalysisByHR>
    findByInterviewWithHR_Request_Customer_Id(Integer customerId);

    List<InterviewAnalysisByHR>
    findByInterviewWithHR_Request_Hr_Id(Integer hrId);
}
