package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.Model.CV;
import org.example.sijalsystem.Model.Customer;
import org.example.sijalsystem.Model.InterviewSession;
import org.example.sijalsystem.Model.Question;
import org.example.sijalsystem.Repository.CVRepository;
import org.example.sijalsystem.Repository.CustomerRepository;
import org.example.sijalsystem.Repository.InterviewSessionRepository;
import org.example.sijalsystem.Repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class InterviewSessionService {

    private final CustomerRepository customerRepository;
    private final CVRepository cvRepository;
    private final InterviewSessionRepository interviewSessionRepository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionGenerationService;

    @Transactional
    public void startSessionAndGenerateQuestions(Integer userId) {

        Customer customer = customerRepository.findById(userId).orElseThrow(() -> new RuntimeException("Customer not found for userId: " + userId));

        CV cv = cvRepository.findById(userId).orElseThrow(() -> new RuntimeException("CV not found for userId: " + userId));

        InterviewSession session = new InterviewSession();
        session.setCustomer(customer);
        session.setCreatedAt(LocalDateTime.now());
        session.setStatus("CREATED");
        session = interviewSessionRepository.save(session);

        // 2) توليد الأسئلة
        List<String> questions = questionGenerationService.generateQuestionsFromCv(cv);

        // 3) تخزين الأسئلة وربطها بالsession
        for (String qText : questions) {
            Question q = new Question();
            q.setInterviewSession(session);
            q.setText(qText);
            questionRepository.save(q);
        }

    }

    @Transactional(readOnly = true)
    public Map<String, Object> getSessionPayload(String sessionId) {

        // 1) تحقق أن sessionId رقم
        Integer id;
        try {
            id = Integer.parseInt(sessionId.trim());
        } catch (Exception e) {
            return Map.of(
                    "valid", false,
                    "message", "SESSION_NOT_FOUND"
            );
        }



        // 3) جب الأسئلة
        List<String> questions = questionRepository.findByInterviewSession_Id(id)
                .stream()
                .map(q -> q.getText())
                .toList();

        // 4) لو ما فيه أسئلة اعتبرها غير صالحة (زي “not found”)
        if (questions.isEmpty()) {
            return Map.of(
                    "valid", false,
                    "message", "SESSION_NOT_FOUND"
            );
        }

        // 5) صالح
        // email to customer
        InterviewSession interviewSession =interviewSessionRepository.findInterviewSessionById(id);
        interviewSession.setStatus("IN_PROGRESS");
        return Map.of(
                "valid", true,
                "questions", questions
        );
    }





}
