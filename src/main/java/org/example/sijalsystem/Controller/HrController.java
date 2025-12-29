package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.DTO.IN.HrDTOIn;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.HrService;
import org.example.sijalsystem.vaildationGroups.ValidationGroup1;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hr")
@RequiredArgsConstructor
public class HrController {

    private final HrService hrService;

    @GetMapping("/get-hr")
    public ResponseEntity<?> getAllHrs(){
        return ResponseEntity.status(200).body(hrService.getAllHrs());
    }

    @PostMapping("/register-hr")
    public ResponseEntity<?> registerHr(@RequestBody @Validated(ValidationGroup1.class) HrDTOIn hrDTOIn){
        hrService.register(hrDTOIn);
        return ResponseEntity.status(200).body(new APIResponse("HR registered successfully"));
    }

    @PutMapping("/update-hr")
    public ResponseEntity<?> updateHr(@AuthenticationPrincipal User user, @RequestBody @Validated(ValidationGroup1.class) HrDTOIn hrDTOIn){
        hrService.updateHr(user.getId(),hrDTOIn);
        return ResponseEntity.status(200).body(new APIResponse("HR account updated successfully"));
    }

    @DeleteMapping("/delete-hr")
    public ResponseEntity<?> deleteHr(@AuthenticationPrincipal User user){
        hrService.deleteHr(user.getId());
        return ResponseEntity.status(200).body(new APIResponse("HR account deleted successfully"));
    }
//extra7
    @GetMapping("/get-hr-ordered")
    public ResponseEntity<?> findAllHROrderByHighestRating(){
        return ResponseEntity.status(200).body(hrService.findAllHROrderByHighestRating());
    }

    @PutMapping("/activate-hr/{hr_id}")
    public ResponseEntity<?> activateHr(@PathVariable Integer hr_id){
        hrService.activeHrByAdmin(hr_id);
        return ResponseEntity.status(200).body(new APIResponse("Hr activated successfully"));
    }


}
