package org.example.sijalsystem.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Advice.CVProcessingException;
import org.example.sijalsystem.DTO.IN.CvDataDTO;
import org.example.sijalsystem.DTO.IN.CvUploadResponse;
import org.example.sijalsystem.Model.CV;
import org.example.sijalsystem.Model.Customer;
import org.example.sijalsystem.Repository.CVRepository;
import org.example.sijalsystem.Repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

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

    @Transactional
    public CvUploadResponse uploadAndParseCV(MultipartFile file, Integer customerId) {
        // Validate file
        validateFile(file);

        // Get customer
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

    public CV getCVById(Integer id) {
        CV cv = cvRepository.findCVById(id);
        if (cv == null){
            throw new APIException("cv not found");
        }
        return cvRepository.findCVById(id);
    }


    @Transactional
    public CvUploadResponse updateCustomerCV(MultipartFile file, Integer customerId) {
        // Validate file
        validateFile(file);

        // Get existing CV
        CV existingCV = cvRepository.findByCustomerId(customerId);
        if (existingCV == null){
            throw new APIException("No CV found for customer:  +" + customerId);
        }


        try {
            // Extract and parse new CV
            String cvText = pdfParserService.extractTextFromPDF(file);
            CvDataDTO cvData = n8nIntegrationService.parseCV(cvText, customerId);

            // Update existing CV
            existingCV.setSummary(cvData.getSummary());
            existingCV.setSkills(cvData.getSkills());
            existingCV.setEducation(cvData.getEducation());
            existingCV.setExperience(cvData.getExperience());
            existingCV.setCreatedAt(LocalDateTime.now());

            CV updatedCV = cvRepository.save(existingCV);
            log.info("CV updated successfully for customer: {}", customerId);

            return new CvUploadResponse(
                    updatedCV.getId(),
                    "CV updated successfully with AI",
                    cvData
            );

        } catch (IOException e) {
            log.error("Error updating CV for customer {}: {}", customerId, e.getMessage());
            throw new CVProcessingException("Failed to update CV file");
        }
    }

    public void deleteCustomerCV(Integer customerId) {
        CV cv = cvRepository.findByCustomerId(customerId);
        if (cv == null) {
            throw new APIException("No CV found for customer: " + customerId);
        }
        cvRepository.delete(cv);

        log.info("CV deleted successfully for customer: {}", customerId);
    }




    public CV addCv(Integer customerId, CV cv) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

        cv.setCreatedAt(LocalDateTime.now());
        cv.setCustomer(customer);

        return cvRepository.save(cv);
    }

}