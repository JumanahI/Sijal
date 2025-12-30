package org.example.sijalsystem.Service;

import jakarta.mail.Session;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.CV;
import org.example.sijalsystem.Model.InterviewSession;
import org.example.sijalsystem.Model.Question;
import org.example.sijalsystem.Repository.InterviewSessionRepository;
import org.example.sijalsystem.Repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final OpenAiService openAiService;
    private final QuestionRepository questionRepository;
    private final InterviewSessionRepository interviewSessionRepository;


    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }


    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }




    public List<Question> getQuestionsBySessionId(Integer userId,Integer sessionId) {
        InterviewSession session=interviewSessionRepository.findInterviewSessionById(sessionId);
        if (session.getCustomer().getId().equals(userId)){
            throw new APIException("You don't have this session");
        }
        return questionRepository.findByInterviewSession_Id(sessionId);
    }

    public List<String> generateQuestionsFromCv(CV cv) {

        String prompt = buildPrompt(cv);
        String aiResponse = openAiService.ask(prompt);

        return parseQuestions(aiResponse);
    }

    public List<String> generateQuestionsFromCvAndDes(CV cv, String jobDescription) {

        String prompt = buildPromptFromCvAndJop(cv, jobDescription);
        String aiResponse = openAiService.ask(prompt);
        return parseQuestions(aiResponse);
    }



    private String buildPrompt(CV cv) {
        return """
        You are a professional job interviewer.

        Based on the following CV, generate 3 interview questions.
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

    private String buildPromptFromCvAndJop(CV cv, String jobDescription) {
        return """
    You are a professional job interviewer.

    Based on the following CV and Job Description, generate 4 interview questions.
    - Questions must match both the candidate background and the job requirements.
    - Questions must be clear and relevant.
    - Each question must be on a separate line.
    - Do NOT number the questions.
    - Do NOT add explanations or extra text.

    Job Description:
    %s

    CV Summary:
    %s

    Skills:
    %s

    Education:
    %s

    Experience:
    %s
    """.formatted(
                safe(jobDescription),
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
