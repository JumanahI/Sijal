package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.DTO.IN.CvDataDTO;
import org.example.sijalsystem.DTO.IN.CvUploadResponse;
import org.example.sijalsystem.DTO.IN.SendCVEmailRequest;
import org.example.sijalsystem.Service.CVPdfGeneratorService;
import org.example.sijalsystem.Service.CVService;
import org.example.sijalsystem.Service.SendMailService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cvs")
@RequiredArgsConstructor
@Slf4j
public class CVController {

    private final CVService cvService;
    private final CVPdfGeneratorService cvPdfGeneratorService;
    private final SendMailService sendMailService;

    @GetMapping("/get-all-cv")
    public ResponseEntity<?> getAllCv() {
        return ResponseEntity.ok(cvService.getAllCvs());
    }

    @PostMapping("/create-cv/{customerId}")
    public ResponseEntity<?> createCv(@PathVariable Integer customerId,
            @RequestBody CvDataDTO cvDataDTO
    ) {
        cvService.createCv(customerId, cvDataDTO);
        return ResponseEntity.ok("CV created successfully");
    }

    @PutMapping("update-cv/{customerId}")
    public ResponseEntity<?> updateCv(
            @PathVariable Integer customerId,
            @RequestBody CvDataDTO cvDataDTO
    ) {
        cvService.updateCv(customerId, cvDataDTO);
        return ResponseEntity.ok(new APIResponse("CV updated successfully"));
    }

    @DeleteMapping("delete-cv/{customerId}")
    public ResponseEntity<?> deleteCv(
            @PathVariable Integer customerId
    ) {
        cvService.deleteCv(customerId);
        return ResponseEntity.ok(new APIResponse("CV deleted successfully"));
    }

    @GetMapping("get-cv-by-customer/{id}")
    public ResponseEntity<?> getCVById(@PathVariable Integer id) {
        return ResponseEntity.ok(cvService.getCVById(id));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCV(@RequestParam("file") MultipartFile file, @RequestParam("customerId") Integer customerId) {

        log.info("Received CV upload request for customer: {}", customerId);
        CvUploadResponse response = cvService.uploadAndParseCV(file, customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCV(
            @RequestParam("file") MultipartFile file,
            @RequestParam("customerId") Integer customerId) {

        log.info("Received CV update request for customer: {}", customerId);
        CvUploadResponse response = cvService.updateCustomerCV(file, customerId);
        return ResponseEntity.ok(response);
    }




    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadCVAsPdf(@PathVariable Integer id) {
        log.info("Request to download CV as PDF, id: {}", id);

        byte[] pdfBytes = cvPdfGeneratorService.generateCVPdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder
                        ("attachment").filename("CVFromSijal" + id + ".pdf")
                        .build()
        );

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @PostMapping("/send-cv-to-email/{customerId}")
    public ResponseEntity<?> sendCVByEmail(@PathVariable Integer customerId,
                                           @Valid @RequestBody SendCVEmailRequest request) {

        log.info("Request to send CV for customer {} to email: {}", customerId, request.getRecipientEmail());

        sendMailService.sendCVByEmail(customerId, request.getRecipientEmail(), request.getMessage());

        return ResponseEntity.ok(new APIResponse("CV sent successfully to " + request.getRecipientEmail()));
    }
}