package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.Question;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.QuestionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/add")
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("/get-all")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }


    @GetMapping("/questions-for-session/{sessionId}")
    public List<Question> getQuestionsBySession(@AuthenticationPrincipal User user, @PathVariable Integer sessionId) {
        return questionService.getQuestionsBySessionId(user.getId(),sessionId);
    }
}