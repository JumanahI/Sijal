package org.example.sijalsystem.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.CV;
import org.example.sijalsystem.Model.Question;
import org.example.sijalsystem.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final OpenAiService openAiService;
    private final QuestionRepository questionRepository;

    // Create
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Read all
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Read one
    public Question getQuestionById(Integer id) {
        Question q = questionRepository.findQuestionById(id);
        if (q == null) throw new APIException("Question not found: " + id);
        return q;
    }

    // Update
    @Transactional
    public Question updateQuestion(Integer id, Question updated) {
        Question old = questionRepository.findQuestionById(id);
        if (old == null) throw new APIException("Question not found: " + id);

        updated.setId(id);
        return questionRepository.save(updated);
    }

    // Delete
    public void deleteQuestion(Integer id) {
        Question q = questionRepository.findQuestionById(id);
        if (q == null) throw new APIException("Question not found: " + id);

        questionRepository.delete(q);
    }

    public List<Question> getQuestionsBySessionId(Integer sessionId) {
        return questionRepository.findByInterviewSession_Id(sessionId);
    }






    /**
     * يولد أسئلة مقابلة بناءً على بيانات الـ CV
     */
    public List<String> generateQuestionsFromCv(CV cv) {

        String prompt = buildPrompt(cv);

        String aiResponse = openAiService.ask(prompt);

        return parseQuestions(aiResponse);
    }

    // ----------------- helpers -----------------

    private String buildPrompt(CV cv) {
        return """
        You are a professional job interviewer.

        Based on the following CV, generate 5 interview questions.
        - Questions must be clear and relevant.
        - Each question must be on a separate line.
        - Do NOT number the questions.
        - Do NOT add explanations or extra text.

        CV Summary:
        %s

        Skills:
        %s

        Education:
        %s

        Experience:
        %s
        """.formatted(
                safe(cv.getSummary()),
                safe(cv.getSkills()),
                safe(cv.getEducation()),
                safe(cv.getExperience())
        );
    }

    private List<String> parseQuestions(String aiResponse) {
        return Arrays.stream(aiResponse.split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
