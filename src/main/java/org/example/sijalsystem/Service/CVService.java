package org.example.sijalsystem.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Advice.CVProcessingException;
import org.example.sijalsystem.DTO.IN.CvDataDTO;
import org.example.sijalsystem.DTO.IN.CvUploadResponse;
import org.example.sijalsystem.DTO.OUT.CVRecommendationDTO;
import org.example.sijalsystem.Model.CV;
import org.example.sijalsystem.Model.Customer;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Repository.CVRepository;
import org.example.sijalsystem.Repository.CustomerRepository;
import org.example.sijalsystem.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CVService {

    private final CVRepository cvRepository;
    private final CustomerRepository customerRepository;
    private final PDFParserService pdfParserService;
    private final N8nIntegrationService n8nIntegrationService;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String ALLOWED_CONTENT_TYPE = "application/pdf";
    private final OpenAiService openAiService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;


    public List<CV> getAllCvs(){
        return cvRepository.findAll();
    }

    public void createCv(Integer customerId,CvDataDTO cvDataDTO){
        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null){
            throw new APIException("customer not found");
        }
        CV cv = new CV();
        cv.setCustomer(customer);
        cv.setEducation(cvDataDTO.getEducation());
        cv.setExperience(cvDataDTO.getExperience());
        cv.setSkills(cvDataDTO.getSkills());
        cv.setSummary(cvDataDTO.getSummary());
        cv.setCreatedAt(LocalDateTime.now());
        customer.setCv(cv);

        cvRepository.save(cv);
        customerRepository.save(customer);
    }

    public void updateCv(Integer customerId , CvDataDTO cvDataDTO){
        Customer customer = customerRepository.findCustomerById(customerId);
        CV cv = cvRepository.findCVByCustomerId(customerId);
        if (customer == null){
            throw new APIException("customer not found");
        }

        if (cv == null){
            throw new APIException("cv not found");
        }

        cv.setEducation(cvDataDTO.getEducation());;
        cv.setExperience(cvDataDTO.getExperience());
        cv.setSkills(cvDataDTO.getSkills());
        cv.setSummary(cvDataDTO.getSummary());
        cvRepository.save(cv);
    }

    public void deleteCv(Integer customerId){
        Customer customer = customerRepository.findCustomerById(customerId);
        CV cv = cvRepository.findCVByCustomerId(customerId);
        if (customer == null){
            throw new APIException("customer not found");
        }

        if (cv == null){
            throw new APIException("cv not found");
        }
        cvRepository.delete(cv);
    }

    @Transactional
    public CvUploadResponse uploadAndParseCV(Integer userId ,MultipartFile file, Integer customerId) {
        User user = userRepository.findUserById(userId);
        validateFile(file);
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new APIException("customer not found");
        }

        try {
            // Step 1: Extract text from PDF
            String cvText = pdfParserService.extractTextFromPDF(file);
            System.out.println(cvText);
            log.info("Extracted {} characters from CV", cvText.length());

            // Step 2: Send to n8n for AI parsing
            CvDataDTO cvData = n8nIntegrationService.parseCV(cvText, customerId);

            // Step 3: Create and save CV entity
            CV cv = new CV();
            cv.setSummary(cvData.getSummary());
            cv.setSkills(cvData.getSkills());
            cv.setEducation(cvData.getEducation());
            cv.setExperience(cvData.getExperience());
            cv.setCustomer(customer);

            CV savedCV = cvRepository.save(cv);
            log.info("CV saved successfully with id: {} for customer: {}", savedCV.getId(), customerId);

            return new CvUploadResponse(
                    savedCV.getId(),
                    "CV uploaded and parsed successfully with AI",
                    cvData
            );

        } catch (IOException e) {
            log.error("Error processing CV for customer {}: {}", customerId, e.getMessage());
            throw new CVProcessingException("Failed to process CV file");
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new APIException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new APIException("File size exceeds maximum allowed size of 5MB");
        }

        if (!ALLOWED_CONTENT_TYPE.equals(file.getContentType())) {
            throw new APIException("Only PDF files are allowed");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            throw new APIException("File must have .pdf extension");
        }
    }

    public CV getCVById(Integer customerId) {
        CV cv = cvRepository.findCVByCustomerId(customerId);
        if (cv == null){
            throw new APIException("cv not found");
        }
        return cv;
    }

    public CVRecommendationDTO recommendationFromAI(Integer customerId){
        Customer customer = customerRepository.findCustomerById(customerId);
        CV cv = cvRepository.findCVByCustomerId(customerId);
        if (customer == null){
            throw new APIException("customer not found");
        }
        if (cv == null){
            throw new APIException("you don't has cv , create now!");
        }
        String AIResponse = openAiService.cvImprovementSuggestions(customer.getCv());
        return objectMapper.readValue(AIResponse , CVRecommendationDTO.class);
    }

}