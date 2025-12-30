package org.example.sijalsystem.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Model.InterviewWithHR;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.InterviewWithHrService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Interview-with-hr")
@RequiredArgsConstructor
public class InterviewWithHrController {

    private final InterviewWithHrService interviewWithHrService;

    @GetMapping("/get-interviews")
    public ResponseEntity<?> getAllInterviewWithHr(){
        return ResponseEntity.status(200).body(interviewWithHrService.getAllInterviewWithHr());
    }


    @PutMapping("/update-interview/{interview_id}")
    public ResponseEntity<?> updateInterviewWithHr(@PathVariable Integer interview_id, @RequestBody @Valid InterviewWithHR interviewWithHr){
        interviewWithHrService.updateInterviewWithHr(interview_id, interviewWithHr);
        return ResponseEntity.status(200).body(new APIResponse("Interview with HR updated successfully"));
    }

    @DeleteMapping("/delete-interview/{interview_id}")
    public ResponseEntity<?> deleteInterviewWithHr(@PathVariable Integer interview_id){
        interviewWithHrService.deleteInterviewWithHr(interview_id);
        return ResponseEntity.status(200).body(new APIResponse("Interview with HR deleted successfully"));
    }

    @PutMapping("/start-interview/{interview_id}")
    public ResponseEntity<?> startInterview(@AuthenticationPrincipal User user, @PathVariable Integer interview_id){
        interviewWithHrService.startInterview(user.getId(), interview_id);
        return ResponseEntity.status(200).body(new APIResponse("Interview with HR started successfully"));
    }

    @PutMapping("/end-interview/{interview_id}")
    public ResponseEntity<?> endInterview(@AuthenticationPrincipal User user,@PathVariable Integer interview_id){
        interviewWithHrService.endInterview(user.getId(), interview_id);
        return ResponseEntity.status(200).body(new APIResponse("Interview with HR ended successfully"));
    }

    @PutMapping("/cancel-interview/{interview_id}")
    public ResponseEntity<?> cancelInterview(@AuthenticationPrincipal User user,@PathVariable Integer interview_id){
        interviewWithHrService.cancelInterview(user.getId(), interview_id);
        return ResponseEntity.status(200).body(new APIResponse("Interview with HR canceled successfully"));
    }

    @GetMapping("/get-interview-by-hr/{hr_id}")
    public ResponseEntity<?> getInterviewByHrId(@PathVariable Integer hr_id){
        return ResponseEntity.status(200).body(interviewWithHrService.getInterviewByHrId(hr_id));
    }


    @GetMapping("/get-interview-by-customer/{customer_id}")
    public ResponseEntity<?> getInterviewByCustomerId(@PathVariable Integer customer_id){
        return ResponseEntity.status(200).body(interviewWithHrService.getInterviewByCustomerId(customer_id));
    }


}
