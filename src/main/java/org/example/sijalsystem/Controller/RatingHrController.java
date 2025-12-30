package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Model.RatingHr;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.RatingHrService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating-hr")
@RequiredArgsConstructor
public class RatingHrController {

    private final RatingHrService ratingHrService;

    @GetMapping("/get-rating")
    public ResponseEntity<?> getRatingsHr() {
        return ResponseEntity.status(200).body(ratingHrService.getRatingsHr());
    }

    @PostMapping("/add-rating/{hr_id}")
    public ResponseEntity<?> addRatingHr(@AuthenticationPrincipal User user, @PathVariable Integer hr_id, @RequestBody @Valid RatingHr ratingHr) {
        ratingHrService.addHrRating(user.getId(), hr_id, ratingHr);
        return ResponseEntity.status(200).body(new APIResponse("Rating added successfully"));
    }

    @PutMapping("/update-rating{rating_id}")
    public ResponseEntity<?> updateRatingHr(@AuthenticationPrincipal User user, @PathVariable Integer rating_id, @RequestBody @Valid RatingHr ratingHr) {
        ratingHrService.updateRating(user.getId(), rating_id, ratingHr);
        return ResponseEntity.status(200).body(new APIResponse("Rating updated successfully"));
    }

    @DeleteMapping("/delete-interview/{rating_id}")
    public ResponseEntity<?> deleteRatingHr(@AuthenticationPrincipal User user, @PathVariable Integer rating_id) {
        ratingHrService.deleteRating(user.getId(), rating_id);
        return ResponseEntity.status(200).body(new APIResponse("Rating deleted successfully"));
    }

    @GetMapping("/get-rating-by-hr/{hr_id}")
    public ResponseEntity<?> findRatingHrByHrId(@PathVariable Integer hr_id){
        return ResponseEntity.status(200).body(ratingHrService.findRatingHrByHrId(hr_id));
    }

    @GetMapping("/get-rating-by-customer")
    public ResponseEntity<?> findRatingHrByCustomerId(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(ratingHrService.findRatingHrByCustomerId(user.getId()));
    }


    @GetMapping("/get-top-rating")
    public ResponseEntity<?> findTopByOrderByStarsDesc(){
        return ResponseEntity.status(200).body(ratingHrService.findTopByOrderByStarsDesc());
    }




}
