package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Service.PaymentService;
import org.example.sijalsystem.Service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final PaymentService paymentService;

    @GetMapping("/get-subscription")
    public ResponseEntity<?> getSubscriptions(){
        return ResponseEntity.status(200).body(subscriptionService.getSubscriptions());
    }


    @PostMapping("/subscribe/{customer_id}")
    public ResponseEntity<?> subscribe(@PathVariable Integer customer_id){
        return ResponseEntity.status(200).body(subscriptionService.subscribe(customer_id));
    }

    @DeleteMapping("/delete-subscribe/{customer_id}/{subscription_id}")
    public ResponseEntity<?> updateSubscribe(@PathVariable Integer customer_id,@PathVariable Integer subscription_id){
        subscriptionService.deleteSubscription(customer_id,subscription_id);
        return ResponseEntity.status(200).body(new APIResponse("Subscription completed successfully"));
    }
}
