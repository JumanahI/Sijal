package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.InterviewAnalysisByHR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewAnalysisByHrRepository extends JpaRepository<InterviewAnalysisByHR,Integer> {

    InterviewAnalysisByHR findByInterviewWithHR_Id(Integer id);

}
