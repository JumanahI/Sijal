package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.DTO.OUT.InterviewDevelopmentPlanDTO;
import org.example.sijalsystem.Model.Customer;
import org.example.sijalsystem.Model.HR;
import org.example.sijalsystem.Model.InterviewAnalysisByHR;
import org.example.sijalsystem.Model.InterviewWithHR;
import org.example.sijalsystem.Repository.CustomerRepository;
import org.example.sijalsystem.Repository.HrRepository;
import org.example.sijalsystem.Repository.InterviewAnalysisByHrRepository;
import org.example.sijalsystem.Repository.InterviewWithHrRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;



import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewAnalysisByHrService {

    private final InterviewAnalysisByHrRepository interviewAnalysisByHrRepository;
    private final InterviewWithHrRepository interviewWithHrRepository;
    private final HrRepository hrRepository;
    private final OpenAiService openAiService;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public List<InterviewAnalysisByHR> getAllInterviewAnalysisByHr(){
        return interviewAnalysisByHrRepository.findAll();
    }


    public void addInterviewAnalysisByHR(Integer hr_id, Integer interview_id,InterviewAnalysisByHR interviewAnalysisByHR){
        InterviewWithHR interviewWithHR = interviewWithHrRepository.findInterviewWithHrById(interview_id);
        HR hr = hrRepository.findHRById(hr_id);

        if(interviewWithHR == null || hr ==null){
            throw new APIException("Interview with HR or hr not found");
        }
        if(!interviewWithHR.getRequest().getHr().getId().equals(hr_id)){
           throw new APIException("this hr not authorized to add analysis to the interview");
        }
        if(!interviewWithHR.getStatus().equalsIgnoreCase("COMPLETE")) {
            throw new APIException("Interview status is not COMPLETE");
        }
        interviewAnalysisByHR.setInterviewWithHR(interviewWithHR);
        interviewAnalysisByHrRepository.save(interviewAnalysisByHR);
    }

    public void updateInterviewAnalysisByHR(Integer hr_id,Integer interviewAnalysis_id , InterviewAnalysisByHR interviewAnalysisByHR){
        InterviewAnalysisByHR oldInterviewAnalysisByHR = interviewAnalysisByHrRepository.findByInterviewWithHR_Id(interviewAnalysis_id);
        HR hr = hrRepository.findHRById(hr_id);
        if(oldInterviewAnalysisByHR == null || hr == null){
            throw new APIException("Interview analysis by HR or hr not found");
        }
        if(interviewAnalysisByHR.getInterviewWithHR().getRequest().getHr().getId().equals(hr_id)){
            throw new APIException("this hr not authorized to update analysis to the interview");
        }
        oldInterviewAnalysisByHR.setStrengths(interviewAnalysisByHR.getStrengths());
        oldInterviewAnalysisByHR.setWeaknesses(interviewAnalysisByHR.getWeaknesses());
        oldInterviewAnalysisByHR.setFinalScore(interviewAnalysisByHR.getFinalScore());
        interviewAnalysisByHrRepository.save(oldInterviewAnalysisByHR);
    }


    public void deleteInterviewAnalysisByHR(Integer hr_id,Integer interviewAnalysis_id){
        InterviewAnalysisByHR interviewAnalysisByHR = interviewAnalysisByHrRepository.findByInterviewWithHR_Id(interviewAnalysis_id);
        HR hr = hrRepository.findHRById(hr_id);
        if(interviewAnalysisByHR == null || hr == null){
            throw new APIException("Interview analysis by HR or hr not found");
        }
        if(interviewAnalysisByHR.getInterviewWithHR().getRequest().getHr().getId().equals(hr_id)){
            throw new APIException("this hr not authorized to delete analysis to the interview");
        }
        interviewAnalysisByHrRepository.delete(interviewAnalysisByHR);
    }

    public List<InterviewAnalysisByHR> getInterviewAnalysisByHrId(Integer hr_id){
        return interviewAnalysisByHrRepository.findByInterviewWithHR_Request_Hr_Id(hr_id);
    }

    public List<InterviewAnalysisByHR> getInterviewAnalysisByCustomerId(Integer customer_id){
        return interviewAnalysisByHrRepository.findByInterviewWithHR_Request_Customer_Id(customer_id);
    }

    public InterviewDevelopmentPlanDTO getInterviewDevelopmentPlanForCustomer(Integer customerId) {

        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new APIException("Customer not found");
        }

        List<InterviewAnalysisByHR> analyses =
                interviewAnalysisByHrRepository.findByInterviewWithHR_Request_Customer_Id(customerId);

        if (analyses.isEmpty()) {
            throw new APIException("No interview analysis found for this customer");
        }

        String AIResponse = openAiService.interviewDevelopmentPlanForCustomer(analyses);
        return objectMapper.readValue(AIResponse , InterviewDevelopmentPlanDTO.class);
    }
}
