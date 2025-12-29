package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.HR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HrRepository extends JpaRepository<HR,Integer> {

    HR findHRById(Integer id);


    @Query("SELECT hr " +
            "FROM HR hr " +
            "LEFT JOIN hr.ratingHr r " +
            "GROUP BY hr " +
            "ORDER BY MAX(r.stars) DESC")
    List<HR> findAllHROrderByHighestRating();
}
