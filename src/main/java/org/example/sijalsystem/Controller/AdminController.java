package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Service.HrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final HrService hrService;

    @PostMapping("/active-account/{hrId}")
    public ResponseEntity<?> activeHrAccount(@PathVariable Integer hrId){
        hrService.activeHrByAdmin(hrId);
        return ResponseEntity.status(200).body(new APIResponse("The HR account active successfully "));
    }
}
