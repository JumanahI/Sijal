package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Model.InterviewAnalysisByHR;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.InterviewAnalysisByHrService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Interview-analysis-by-hr")
@RequiredArgsConstructor
public class InterviewAnalysisByHrController {


    private final InterviewAnalysisByHrService interviewAnalysisByHrService;

    @GetMapping("/get-interviews-analysis")
    public ResponseEntity<?> getAllInterviewAnalysis(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(interviewAnalysisByHrService.getAllInterviewAnalysisByHr());
    }

    @PostMapping("/add-interview-analysis/{interview_id}")
    public ResponseEntity<?> addInterviewAnalysis(@PathVariable Integer interview_id , @RequestBody @Valid InterviewAnalysisByHR interviewAnalysisByHR){
        interviewAnalysisByHrService.addInterviewAnalysisByHR(interview_id,interviewAnalysisByHR);
        return ResponseEntity.status(200).body(new APIResponse("Interview analysis added successfully"));
    }

    @PutMapping("/update-interview-analysis/{analysis_id}")
    public ResponseEntity<?> updateInterviewAnalysis(@PathVariable Integer analysis_id, @RequestBody @Valid InterviewAnalysisByHR interviewAnalysisByHR){
        interviewAnalysisByHrService.updateInterviewAnalysisByHR(analysis_id, interviewAnalysisByHR);
        return ResponseEntity.status(200).body(new APIResponse("Interview analysis updated successfully"));
    }

    @DeleteMapping("/delete-interview-analysis/{analysis_id}")
    public ResponseEntity<?> deleteInterviewAnalysis(@PathVariable Integer analysis_id){
        interviewAnalysisByHrService.deleteInterviewAnalysisByHR(analysis_id);
        return ResponseEntity.status(200).body(new APIResponse("Interview analysis deleted successfully"));
    }
}
