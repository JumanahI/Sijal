package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByInterviewSession_Id(Integer sessionId);

    Question findQuestionById(Integer id);
}