package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.RequestInterview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestInterviewRepository extends JpaRepository<RequestInterview, Integer> {

    RequestInterview findRequestInterviewById(Integer id);
}
