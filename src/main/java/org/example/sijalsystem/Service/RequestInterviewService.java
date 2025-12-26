package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.Customer;
import org.example.sijalsystem.Model.HR;
import org.example.sijalsystem.Model.RequestInterview;
import org.example.sijalsystem.Repository.CustomerRepository;
import org.example.sijalsystem.Repository.HrRepository;
import org.example.sijalsystem.Repository.RequestInterviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestInterviewService {

    private final RequestInterviewRepository requestInterviewRepository;
    private final HrRepository hrRepository;
    private final CustomerRepository customerRepository;

    public List<RequestInterview> getRequestInterview(){
        return requestInterviewRepository.findAll();
    }

    public void addRequestInterview(Integer customer_id,Integer hr_id,RequestInterview requestInterview){
        HR hr = hrRepository.findHRById(hr_id);
        Customer customer = customerRepository.findCustomerByUser_Id(customer_id);
        if(hr == null || customer == null){
            throw new APIException("HR or Customer not found");
        }
        requestInterview.setStatus("PENDING");
        requestInterview.setHr(hr);
        requestInterview.setCustomer(customer);
        requestInterviewRepository.save(requestInterview);
    }

    public void updateRequestInterview(Integer requestInterview_id , RequestInterview requestInterview){
        RequestInterview oldRequestInterview = requestInterviewRepository.findRequestInterviewById(requestInterview_id);

        if(oldRequestInterview == null){
            throw new APIException("Request interview not found");
        }
        oldRequestInterview.setMessage(requestInterview.getMessage());
        oldRequestInterview.setStartTime(requestInterview.getStartTime());
        requestInterviewRepository.save(oldRequestInterview);
    }

    public void deleteRequestInterview(Integer requestInterview_id){
        RequestInterview requestInterview = requestInterviewRepository.findRequestInterviewById(requestInterview_id);
        if(requestInterview == null){
            throw new APIException("Request interview not found");
        }
        requestInterviewRepository.delete(requestInterview);
    }
}
