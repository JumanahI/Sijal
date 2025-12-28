package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Model.Card;
import org.example.sijalsystem.Service.CardService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add-card/{customer_id}")
    public ResponseEntity<?> addCard(@PathVariable Integer customer_id ,@RequestBody @Valid Card card){
        cardService.addCard(customer_id,card);
        return ResponseEntity.status(200).body(new APIResponse("Card added successfully"));
    }

    @PutMapping("/update-card/{customer_id}/{card_id}")
    public ResponseEntity<?> updateCard(@PathVariable Integer customer_id, @PathVariable Integer card_id,@RequestBody @Valid Card card){
        cardService.updateCard(customer_id,card_id,card);
        return ResponseEntity.status(200).body(new APIResponse("Card updated successfully"));
    }

    @DeleteMapping("/delete-card/{customer_id}/{card_id}")
    public ResponseEntity<?> deleteCard(@PathVariable Integer customer_id,@PathVariable Integer card_id){
        cardService.deleteCard(customer_id, card_id);
        return ResponseEntity.status(200).body(new APIResponse("Card deleted successfully"));
    }
}
