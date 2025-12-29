package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Model.Card;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/get-cards")
    public ResponseEntity<?> getCards(){
        return ResponseEntity.status(200).body(cardService.getCards());
    }

    @PostMapping("/add-card")
    public ResponseEntity<?> addCard(@AuthenticationPrincipal User user , @RequestBody @Valid Card card){
        cardService.addCard(user.getId(), card);
        return ResponseEntity.status(200).body(new APIResponse("Card added successfully"));
    }

    @PutMapping("/update-card/{card_id}")
    public ResponseEntity<?> updateCard(@AuthenticationPrincipal User user, @PathVariable Integer card_id,@RequestBody @Valid Card card){
        cardService.updateCard(user.getId(), card_id ,card);
        return ResponseEntity.status(200).body(new APIResponse("Card updated successfully"));
    }

    @DeleteMapping("/delete-card/{card_id}")
    public ResponseEntity<?> deleteCard(@AuthenticationPrincipal User user,@PathVariable Integer card_id){
        cardService.deleteCard(user.getId(), card_id);
        return ResponseEntity.status(200).body(new APIResponse("Card deleted successfully"));
    }
}
