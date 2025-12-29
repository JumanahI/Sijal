package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.Model.RatingHr;
import org.example.sijalsystem.Service.RatingHrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ratingHr")
@RequiredArgsConstructor
public class RatingHrController {

    private final RatingHrService ratingHrService;

    @GetMapping("/get-rating")
    public ResponseEntity<?> getRatingsHr() {
        return ResponseEntity.status(200).body(ratingHrService.getRatingsHr());
    }

    @PostMapping("/add-rating/{customer_id}/{hr_id}")
    public ResponseEntity<?> addRatingHr(@PathVariable Integer customer_id, @PathVariable Integer hr_id, @RequestBody @Valid RatingHr ratingHr) {
        ratingHrService.addHrRating(customer_id, hr_id, ratingHr);
        return ResponseEntity.status(200).body(new APIResponse("Rating added successfully"));
    }

    @PutMapping("/update-rating/{customer_id}/{rating_id}")
    public ResponseEntity<?> updateRatingHr(@PathVariable Integer customer_id, @PathVariable Integer rating_id, @RequestBody @Valid RatingHr ratingHr) {
        ratingHrService.updateRating(customer_id, rating_id, ratingHr);
        return ResponseEntity.status(200).body(new APIResponse("Rating updated successfully"));
    }

    @DeleteMapping("/delete-interview/{customer_id}/{rating_id}")
    public ResponseEntity<?> deleteRatingHr(@PathVariable Integer customer_id, @PathVariable Integer rating_id) {
        ratingHrService.deleteRating(customer_id, rating_id);
        return ResponseEntity.status(200).body(new APIResponse("Rating deleted successfully"));
    }

    @GetMapping("/get-rating-by-hr/{hr_id}")
    public ResponseEntity<?> findRatingHrByHrId(@PathVariable Integer hr_id){
        return ResponseEntity.status(200).body(ratingHrService.findRatingHrByHrId(hr_id));
    }

    @GetMapping("/get-rating-by-customer/{customer_id}")
    public ResponseEntity<?> findRatingHrByCustomerId(@PathVariable Integer customer_id){
        return ResponseEntity.status(200).body(ratingHrService.findRatingHrByCustomerId(customer_id));
    }


    @GetMapping("/get-top-rating")
    public ResponseEntity<?> findTopByOrderByStarsDesc(){
        return ResponseEntity.status(200).body(ratingHrService.findTopByOrderByStarsDesc());
    }




}
