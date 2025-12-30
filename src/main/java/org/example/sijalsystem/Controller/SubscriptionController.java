package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.PaymentService;
import org.example.sijalsystem.Service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/get-subscription")
    public ResponseEntity<?> getSubscriptions(){
        return ResponseEntity.status(200).body(subscriptionService.getSubscriptions());
    }


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(subscriptionService.subscribe(user.getId()));
    }

    @DeleteMapping("/cancel-subscribe/{subscription_id}")
    public ResponseEntity<?> updateSubscribe(@AuthenticationPrincipal User user,@PathVariable Integer subscription_id){
        subscriptionService.deleteSubscription(user.getId(),subscription_id);
        return ResponseEntity.status(200).body(new APIResponse("Subscription completed successfully"));
    }
}
