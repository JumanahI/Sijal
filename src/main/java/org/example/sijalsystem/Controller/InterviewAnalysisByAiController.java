package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.InterviewAnalysisByAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
public class InterviewAnalysisByAiController {

    private final InterviewAnalysisByAiService analysisService;

    @GetMapping("/all-analysis")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(analysisService.getAllAnalysesForCustomer(user.getId()));
    }

    @GetMapping("/analysis-for-session/{sessionId}")
    public ResponseEntity<?> getAnalysisForSession(@AuthenticationPrincipal User user, @PathVariable Integer sessionId) {
        return ResponseEntity.status(200).body(analysisService.getAnalysisForSession(user.getId(), sessionId));
    }
}
