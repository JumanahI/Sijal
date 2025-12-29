package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;

import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.DTO.IN.JopDescription;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.InterviewSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/interview-sessions")
@RequiredArgsConstructor
public class InterviewSessionController {

//extra10
    private final InterviewSessionService interviewSessionService;
    @PostMapping("/start-session-with-cv")
    public ResponseEntity<?> start(@AuthenticationPrincipal User user) {
        interviewSessionService.startSessionAndGenerateQuestions(user.getId(),null);
        return ResponseEntity.status(200).body(new APIResponse("تم إعداد المقابلة بنجاح" +
                "ستصلك رسالة على بريدك الإلكتروني تتضمن رقم جلسة المقابلة وخطوات البدء" +
                "يرجى التأكد من جاهزية الهاتف قبل بدء المقابلة. "));
    }

    @PostMapping("start-session-with-description")
    public ResponseEntity<?> startWithDes(@AuthenticationPrincipal User user, @RequestBody JopDescription jopDescription){
        interviewSessionService.startSessionAndGenerateQuestions(user.getId(),jopDescription);
        return ResponseEntity.status(200).body(new APIResponse("تم إعداد المقابلة بنجاح" +
                "ستصلك رسالة على بريدك الإلكتروني تتضمن رقم جلسة المقابلة وخطوات البدء" +
                "يرجى التأكد من جاهزية الهاتف قبل بدء المقابلة. "));
    }

//extra12
    @GetMapping("/get_question/{sessionId}")
    public ResponseEntity<?> getSession(@PathVariable String sessionId) {
        Map<String, Object> payload = interviewSessionService.getSessionPayload(sessionId);
        boolean valid = Boolean.TRUE.equals(payload.get("valid"));
        if (!valid) {
            return ResponseEntity.status(404).body(payload);
        }
        return ResponseEntity.ok(payload);
    }


    @GetMapping("/get-my-sessions")
    public ResponseEntity<?> getMySessions(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(interviewSessionService.getMySessions(user.getId()));
    }


    @GetMapping("/get-session-by-id/{sessionId}")
    public ResponseEntity<?> getSessionAnalysis(@AuthenticationPrincipal User user, @PathVariable Integer sessionId) {
        return ResponseEntity.status(200).body(interviewSessionService.getMySessionById(user.getId(), sessionId));
    }



}
