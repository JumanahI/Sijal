package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.RatingHr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingHrRepository extends JpaRepository<RatingHr,Integer> {

    RatingHr findRatingHrById(Integer id);

    List<RatingHr> findRatingHrByHrId(Integer hr_id);

    List<RatingHr> findRatingHrByCustomerId(Integer customer_id);

    RatingHr findTopByOrderByStarsDesc();


}
