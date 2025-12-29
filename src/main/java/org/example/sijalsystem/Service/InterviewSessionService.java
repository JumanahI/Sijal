package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.DTO.IN.JopDescription;
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
    private final SendMailService sendMailService;


    public List<InterviewSession> getMySessions(Integer customerId) {
        return interviewSessionRepository.findInterviewSessionsByCustomerId(customerId);
    }


    public InterviewSession getMySessionById(Integer customerId, Integer sessionId) {
        boolean allowed = interviewSessionRepository.existsByIdAndCustomer_Id(sessionId, customerId);
        if (!allowed) {
            throw new APIException("You are not allowed to access this session");
        }
        InterviewSession session = interviewSessionRepository.findInterviewSessionById(sessionId);
        if (session == null) {
            throw new APIException("InterviewSession not found: " + sessionId);
        }

        return session;
    }


    @Transactional
    public void startSessionAndGenerateQuestions(Integer userId, JopDescription jop) {

        Customer customer = customerRepository.findCustomerById(userId);
                if (customer==null){
                   throw  new APIException("Customer not found for userId: " + userId);
                }

        CV cv = cvRepository.findCVById(userId);
                if (cv==null) {
                    throw new APIException("CV not found for userId: " + userId);
                }

        InterviewSession session = new InterviewSession();
        session.setCustomer(customer);
        session.setCreatedAt(LocalDateTime.now());
        session.setStatus("CREATED");
        session = interviewSessionRepository.save(session);


        List<String> questions;

        if (jop != null && jop.getJopDescription() != null && !jop.getJopDescription().isBlank()) {

            session.setJopDescription(jop.getJopDescription());

            questions = questionGenerationService.generateQuestionsFromCvAndDes(cv, jop.getJopDescription());

        } else {

            questions = questionGenerationService.generateQuestionsFromCv(cv);
        }

        for (String qText : questions) {
            Question q = new Question();
            q.setInterviewSession(session);
            q.setText(qText);
            questionRepository.save(q);
        }

        Integer sessionId = session.getId();
        String phoneNumber = "+1(803)8792772";
        String subject = "رقم جلسة المقابلة – منصة سجال";
        String body = """
مرحباً %s،

تم إنشاء جلسة المقابلة الخاصة بك بنجاح عبر منصة سجال ✅

رقم جلسة المقابلة:
%s

لبدء المقابلة:
📞 يرجى الاتصال على الرقم التالي:
%s

خطوات الدخول:
1- الاتصال على الرقم أعلاه
2- إدخال رقم الجلسة باستخدام لوحة أرقام الهاتف
3- ستبدأ المقابلة مباشرة بعد التحقق من الرقم

نتمنى لك تجربة مفيدة وتوفيقاً في المقابلة 🌟

منصة سجال
        """.formatted(customer.getUser().getName(), sessionId, phoneNumber);

        sendMailService.sendMessage(customer.getUser().getEmail(), subject, body);

    }

    @Transactional(readOnly = true)
    public Map<String, Object> getSessionPayload(String sessionId) {

        Integer id;
        try {
            id = Integer.parseInt(sessionId.trim());
        } catch (Exception e) {
            return Map.of(
                    "valid", false,
                    "message", "SESSION_NOT_FOUND"
            );
        }



        List<String> questions = questionRepository.findByInterviewSession_Id(id)
                .stream()
                .map(q -> q.getText())
                .toList();

        if (questions.isEmpty()) {
            return Map.of(
                    "valid", false,
                    "message", "SESSION_NOT_FOUND"
            );
        }


        InterviewSession interviewSession =interviewSessionRepository.findInterviewSessionById(id);
        interviewSession.setStatus("IN_PROGRESS");
        return Map.of(
                "valid", true,
                "questions", questions
        );
    }
}
