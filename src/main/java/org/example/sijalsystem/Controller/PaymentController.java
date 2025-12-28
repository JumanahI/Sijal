package org.example.sijalsystem.Controller;


import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.Model.Card;
import org.example.sijalsystem.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

//    @PostMapping("/card")
//    public ResponseEntity<String> processPayment(@RequestBody Card paymentRequest) {
//        return paymentService.processPayment(paymentRequest,20);
//    }

    @GetMapping("/get-status/{id}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentStatus(id));
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handlePaymentCallback() {
    return ResponseEntity.ok("Subscription completed successfully");
    }

}
