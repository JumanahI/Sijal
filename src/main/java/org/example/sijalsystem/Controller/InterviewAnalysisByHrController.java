package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.DTO.OUT.InterviewDevelopmentPlanDTO;
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
    public ResponseEntity<?> getAllInterviewAnalysis(){
        return ResponseEntity.status(200).body(interviewAnalysisByHrService.getAllInterviewAnalysisByHr());
    }

    @PostMapping("/add-interview-analysis/{interview_id}")
    public ResponseEntity<?> addInterviewAnalysis(@AuthenticationPrincipal User user, Integer hr_id,@PathVariable Integer interview_id , @RequestBody @Valid InterviewAnalysisByHR interviewAnalysisByHR){
        interviewAnalysisByHrService.addInterviewAnalysisByHR(user.getId(),interview_id,interviewAnalysisByHR);
        return ResponseEntity.status(200).body(new APIResponse("Interview analysis added successfully"));
    }

    @PutMapping("/update-interview-analysis/{analysis_id}")
    public ResponseEntity<?> updateInterviewAnalysis(@AuthenticationPrincipal User user,@PathVariable Integer analysis_id, @RequestBody @Valid InterviewAnalysisByHR interviewAnalysisByHR){
        interviewAnalysisByHrService.updateInterviewAnalysisByHR(user.getId(),analysis_id, interviewAnalysisByHR);
        return ResponseEntity.status(200).body(new APIResponse("Interview analysis updated successfully"));
    }

    @DeleteMapping("/delete-interview-analysis/{analysis_id}")
    public ResponseEntity<?> deleteInterviewAnalysis(@AuthenticationPrincipal User user,@PathVariable Integer analysis_id){
        interviewAnalysisByHrService.deleteInterviewAnalysisByHR(user.getId(),analysis_id);
        return ResponseEntity.status(200).body(new APIResponse("Interview analysis deleted successfully"));
    }

    @GetMapping("/get-interview-analysis-by-hr")
    public ResponseEntity<?> getInterviewAnalysisByHrId(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(interviewAnalysisByHrService.getInterviewAnalysisByHrId(user.getId()));
    }

    @GetMapping("/get-interview-analysis-by-customer")
    public ResponseEntity<?> getInterviewAnalysisByCustomerId(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(interviewAnalysisByHrService.getInterviewAnalysisByCustomerId(user.getId()));
    }


    @GetMapping("/development-plan")
    public ResponseEntity<?> getDevelopmentPlan(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(interviewAnalysisByHrService.getInterviewDevelopmentPlanForCustomer(user.getId()));
    }
}
