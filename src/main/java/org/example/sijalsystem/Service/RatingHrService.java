package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.Customer;
import org.example.sijalsystem.Model.HR;
import org.example.sijalsystem.Model.RatingHr;
import org.example.sijalsystem.Repository.CustomerRepository;
import org.example.sijalsystem.Repository.HrRepository;
import org.example.sijalsystem.Repository.RatingHrRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingHrService {

    private final RatingHrRepository ratingHrRepository;
    private final CustomerRepository customerRepository;
    private final HrRepository hrRepository;


    public List<RatingHr> getRatingsHr(){
        return ratingHrRepository.findAll();
    }

    public void addHrRating(Integer customer_id , Integer hr_id , RatingHr ratingHr){
        Customer customer = customerRepository.findCustomerById(customer_id);
        HR hr = hrRepository.findHRById(hr_id);
        if(customer == null || hr == null){
            throw new APIException("Customer or hr not found");
        }

        boolean customerHasInterview = customer.getRequestInterviewSet().stream()
                .anyMatch(request ->
                        request.getHr().getId().equals(hr_id)
                                && "APPROVE".equals(request.getStatus())
                                && request.getInterviewWithHR() != null
                );

        if (!customerHasInterview) {
            throw new APIException("You cannot rate this HR without completing an interview");
        }
        ratingHr.setCustomer(customer);
        ratingHr.setHr(hr);
        ratingHrRepository.save(ratingHr);
    }

    public void updateRating(Integer customer_id , Integer rating_id , RatingHr ratingHr){
        Customer customer = customerRepository.findCustomerById(customer_id);
        RatingHr oldRating = ratingHrRepository.findRatingHrById(rating_id);

        if(customer == null || oldRating == null){
            throw new APIException("Customer or rating not found");
        }

        if(!oldRating.getCustomer().getId().equals(customer_id)){
            throw new APIException("Customer not authorized to update this rating");
        }

        oldRating.setComment(ratingHr.getComment());
        oldRating.setStars(ratingHr.getStars());
        ratingHrRepository.save(oldRating);
    }

    public void deleteRating(Integer customer_id , Integer rating_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        RatingHr rating = ratingHrRepository.findRatingHrById(rating_id);

        if(customer == null || rating == null){
            throw new APIException("Customer or rating not found");
        }

        if(!rating.getCustomer().getId().equals(customer_id)){
            throw new APIException("Customer not authorized to delete this rating");
        }

        ratingHrRepository.delete(rating);
    }

    public List<RatingHr> findRatingHrByHrId(Integer hr_id){
        return ratingHrRepository.findRatingHrByHrId(hr_id);
    }

    public List<RatingHr> findRatingHrByCustomerId(Integer customer_id){
        return ratingHrRepository.findRatingHrByCustomerId(customer_id);
    }


    public RatingHr findTopByOrderByStarsDesc(){
        return ratingHrRepository.findTopByOrderByStarsDesc();
    }
}
