package org.example.sijalsystem.Controller;


import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/get-status/{id}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentStatus(id));
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handlePaymentCallback() {
    return ResponseEntity.ok("Subscription completed successfully");
    }

}
