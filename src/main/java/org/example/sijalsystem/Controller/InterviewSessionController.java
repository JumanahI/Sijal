package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;

import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Service.InterviewSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/interview-sessions")
@RequiredArgsConstructor
public class InterviewSessionController {


    private final InterviewSessionService interviewSessionService;
    @PostMapping("/start/{userId}")
    public ResponseEntity<?> start(@PathVariable Integer userId) {
        interviewSessionService.startSessionAndGenerateQuestions(userId);
        return ResponseEntity.status(200).body(new APIResponse("تم إعداد المقابلة بنجاح" +
                "ستصلك رسالة على بريدك الإلكتروني تتضمن رقم جلسة المقابلة وخطوات البدء" +
                "يرجى التأكد من جاهزية الهاتف قبل بدء المقابلة. "));
    }



//    @GetMapping("/session/{sessionId}")
//    public ResponseEntity<?> getSession(@PathVariable String sessionId) {
//        return ResponseEntity.ok(interviewSessionService.getSessionPayload(sessionId));
//    }


    @GetMapping("/get_question/{sessionId}")
    public ResponseEntity<?> getSession(@PathVariable String sessionId) {
        Map<String, Object> payload = interviewSessionService.getSessionPayload(sessionId);
        boolean valid = Boolean.TRUE.equals(payload.get("valid"));
        if (!valid) {
            return ResponseEntity.status(404).body(payload);
        }
        return ResponseEntity.ok(payload);
    }

}
