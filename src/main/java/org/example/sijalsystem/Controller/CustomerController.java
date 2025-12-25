package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.DTO.IN.CustomerDTOIn;
import org.example.sijalsystem.Service.CustomerService;
import org.example.sijalsystem.vaildationGroups.ValidationGroup1;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get-customer")
    public ResponseEntity<?> getCustomer(){
        return ResponseEntity.status(200).body(customerService.getCustomers());
    }

    @PostMapping("/create-customer")
    public ResponseEntity<?> addCustomer(@RequestBody @Validated(ValidationGroup1.class) CustomerDTOIn customerDTOIn){
        customerService.addCustomer(customerDTOIn);
        return ResponseEntity.status(200).body(new APIResponse("created customer successfully"));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer userId , @RequestBody @Validated(ValidationGroup1.class) CustomerDTOIn customerDTOIn){
        customerService.updateCustomer(userId,customerDTOIn);
        return ResponseEntity.status(200).body(new APIResponse("updated customer account successfully"));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer userId){
        customerService.deleteCustomer(userId);
        return ResponseEntity.status(200).body(new APIResponse("deleted customer successfully"));
    }
}
