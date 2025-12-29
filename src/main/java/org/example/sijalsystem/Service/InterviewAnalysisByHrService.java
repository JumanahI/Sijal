package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.DTO.OUT.InterviewDevelopmentPlanDTO;
import org.example.sijalsystem.Model.Customer;
import org.example.sijalsystem.Model.InterviewAnalysisByHR;
import org.example.sijalsystem.Model.InterviewWithHR;
import org.example.sijalsystem.Repository.CustomerRepository;
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
    private final OpenAiService openAiService;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public List<InterviewAnalysisByHR> getAllInterviewAnalysisByHr(){
        return interviewAnalysisByHrRepository.findAll();
    }


    public void addInterviewAnalysisByHR(Integer interview_id,InterviewAnalysisByHR interviewAnalysisByHR){
        InterviewWithHR interviewWithHR = interviewWithHrRepository.findInterviewWithHrById(interview_id);
        if(interviewWithHR == null){
            throw new APIException("Interview with HR not found");
        }
        if(!interviewWithHR.getStatus().equalsIgnoreCase("COMPLETE")) {
            throw new APIException("Interview status is not COMPLETE");
        }
        interviewAnalysisByHR.setInterviewWithHR(interviewWithHR);
        interviewAnalysisByHrRepository.save(interviewAnalysisByHR);
    }

    public void updateInterviewAnalysisByHR(Integer interviewAnalysis_id , InterviewAnalysisByHR interviewAnalysisByHR){
        InterviewAnalysisByHR oldInterviewAnalysisByHR = interviewAnalysisByHrRepository.findByInterviewWithHR_Id(interviewAnalysis_id);

        if(oldInterviewAnalysisByHR == null){
            throw new APIException("Interview analysis by HR not found");
        }

        oldInterviewAnalysisByHR.setStrengths(interviewAnalysisByHR.getStrengths());
        oldInterviewAnalysisByHR.setWeaknesses(interviewAnalysisByHR.getWeaknesses());
        oldInterviewAnalysisByHR.setFinalScore(interviewAnalysisByHR.getFinalScore());
        interviewAnalysisByHrRepository.save(oldInterviewAnalysisByHR);
    }


    public void deleteInterviewAnalysisByHR(Integer interviewAnalysis_id){
        InterviewAnalysisByHR interviewAnalysisByHR = interviewAnalysisByHrRepository.findByInterviewWithHR_Id(interviewAnalysis_id);

        if(interviewAnalysisByHR == null){
            throw new APIException("Interview analysis by HR not found");
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
