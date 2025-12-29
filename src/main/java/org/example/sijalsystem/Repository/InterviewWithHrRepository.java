package org.example.sijalsystem.Repository;
import org.example.sijalsystem.Model.InterviewWithHR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewWithHrRepository extends JpaRepository<InterviewWithHR, Integer> {

    InterviewWithHR findInterviewWithHrById(Integer id);

    List<InterviewWithHR>
    findByRequest_Customer_Id(Integer customerId);

    List<InterviewWithHR>
    findByRequest_Hr_Id(Integer hrId);

}
