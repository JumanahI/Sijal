package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Model.RequestInterview;
import org.example.sijalsystem.Service.RequestInterviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/request-interview")
@RequiredArgsConstructor
public class RequestInterviewController {

    private final RequestInterviewService requestInterviewService;

    @GetMapping("/get-Request")
    public ResponseEntity<?> getAllRequestInterview(){
        return ResponseEntity.status(200).body(requestInterviewService.getRequestInterview());
    }

    @PostMapping("/add-Request/{customer_id}/{hr_id}")
    public ResponseEntity<?> addRequestInterview(@PathVariable Integer customer_id,@PathVariable Integer hr_id, @RequestBody @Valid RequestInterview requestInterview){
        requestInterviewService.sendRequestInterview(customer_id,hr_id,requestInterview);
        return ResponseEntity.status(200).body(new APIResponse("Request interview added successfully"));
    }

    @PutMapping("/update-Request/{request_id}")
    public ResponseEntity<?> updateRequestInterview(@PathVariable Integer request_id, @RequestBody @Valid RequestInterview requestInterview){
        requestInterviewService.updateRequestInterview(request_id,requestInterview);
        return ResponseEntity.status(200).body(new APIResponse("Request interview updated successfully"));
    }

    @DeleteMapping("/delete-Request/{request_id}")
    public ResponseEntity<?> deleteRequestInterview(@PathVariable Integer request_id){
        requestInterviewService.deleteRequestInterview(request_id);
        return ResponseEntity.status(200).body(new APIResponse("Request interview deleted successfully"));
    }

    @PutMapping("/approve-request/{hr_id}/{request_id}")
    public ResponseEntity<?> approveRequest(@PathVariable Integer hr_id,@PathVariable Integer request_id){
        requestInterviewService.approveRequest(hr_id, request_id);
        return ResponseEntity.status(200).body(new APIResponse("Request interview approved successfully"));
    }

    @PutMapping("/reject-request/{hr_id}/{request_id}")
    public ResponseEntity<?> rejectRequest(@PathVariable Integer hr_id,@PathVariable Integer request_id){
        requestInterviewService.rejectRequest(hr_id, request_id);
        return ResponseEntity.status(200).body(new APIResponse("Request interview rejected successfully"));
    }


}
