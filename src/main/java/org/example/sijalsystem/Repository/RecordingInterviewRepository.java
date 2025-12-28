package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.RecordingInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordingInterviewRepository extends JpaRepository<RecordingInterview,Integer> {
    RecordingInterview findRecordingInterviewById(Integer id);
}
