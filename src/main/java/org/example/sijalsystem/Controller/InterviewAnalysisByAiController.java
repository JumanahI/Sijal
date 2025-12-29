package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.Service.InterviewAnalysisByAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
public class InterviewAnalysisByAiController {

    private final InterviewAnalysisByAiService analysisService;
//extra8
    @GetMapping("/all-analysis/{customerId}")
    public ResponseEntity<?> getAll(@PathVariable Integer customerId) {
        return ResponseEntity.status(200).body(analysisService.getAllAnalysesForCustomer(customerId));
    }
//extra9
    @GetMapping("/analysis-for-session/{customerId}/{sessionId}")
    public ResponseEntity<?> getAnalysisForSession(@PathVariable Integer customerId, @PathVariable Integer sessionId) {
        return ResponseEntity.status(200).body(analysisService.getAnalysisForSession(customerId, sessionId));
    }
}
