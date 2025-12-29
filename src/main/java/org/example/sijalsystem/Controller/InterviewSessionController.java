package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;

import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.DTO.IN.JopDescription;
import org.example.sijalsystem.Service.InterviewSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/interview-sessions")
@RequiredArgsConstructor
public class InterviewSessionController {

//extra10
    private final InterviewSessionService interviewSessionService;
    @PostMapping("/start-session-with-cv/{userId}")
    public ResponseEntity<?> start(@PathVariable Integer userId) {
        interviewSessionService.startSessionAndGenerateQuestions(userId,null);
        return ResponseEntity.status(200).body(new APIResponse("تم إعداد المقابلة بنجاح" +
                "ستصلك رسالة على بريدك الإلكتروني تتضمن رقم جلسة المقابلة وخطوات البدء" +
                "يرجى التأكد من جاهزية الهاتف قبل بدء المقابلة. "));
    }
//extra11
    @PostMapping("/start-session-with-description/{customerId}")
    public ResponseEntity<?> startWithDes(@PathVariable Integer customerId, @RequestBody JopDescription jobDescription){
        interviewSessionService.startSessionAndGenerateQuestions(customerId,jobDescription);
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

//extra13
    @GetMapping("/get-my-sessions/{customerId}")
    public ResponseEntity<?> getMySessions(@PathVariable Integer customerId) {
        return ResponseEntity.status(200).body(interviewSessionService.getMySessions(customerId));
    }

//extra13
    @GetMapping("/get-session/by/id/{customerId}/{sessionId}/")
    public ResponseEntity<?> getSessionAnalysis(@PathVariable Integer customerId, @PathVariable Integer sessionId) {
        return ResponseEntity.status(200).body(interviewSessionService.getMySessionById(customerId, sessionId));
    }



}
