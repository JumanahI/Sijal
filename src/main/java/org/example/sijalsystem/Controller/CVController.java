package org.example.sijalsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sijalsystem.API.APIResponse;
import org.example.sijalsystem.DTO.IN.CvDataDTO;
import org.example.sijalsystem.DTO.IN.CvUploadResponse;
import org.example.sijalsystem.DTO.IN.SendCVEmailRequest;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Service.CVPdfGeneratorService;
import org.example.sijalsystem.Service.CVService;
import org.example.sijalsystem.Service.SendMailService;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cv")
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

    @PostMapping("/create-cv")
    public ResponseEntity<?> createCv(@AuthenticationPrincipal User customerId,
            @RequestBody CvDataDTO cvDataDTO
    ) {
        cvService.createCv(customerId.getId(), cvDataDTO);
        return ResponseEntity.ok(new APIResponse("cv created successfully"));
    }

    @PutMapping("/update-cv")
    public ResponseEntity<?> updateCv(@AuthenticationPrincipal User user,
            @RequestBody CvDataDTO cvDataDTO
    ) {
        cvService.updateCv(user.getId(), cvDataDTO);
        return ResponseEntity.ok(new APIResponse("cv updated successfully"));
    }

    @DeleteMapping("/delete-cv")
    public ResponseEntity<?> deleteCv(@AuthenticationPrincipal User customerId) {
        cvService.deleteCv(customerId.getId());
        return ResponseEntity.ok(new APIResponse("cv deleted successfully"));
    }




    //extra2
    @PostMapping("/upload-cv")
    public ResponseEntity<?> uploadCV(@RequestParam("file") MultipartFile file , @AuthenticationPrincipal User user ) {

        CvUploadResponse response = cvService.uploadAndParseCV(file, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/download-cv")
    public ResponseEntity<?> downloadCVAsPdf(@AuthenticationPrincipal User user) {
        log.info("Request to download CV as PDF, id: {}", user.getId());

        byte[] pdfBytes = cvPdfGeneratorService.generateCVPdf(user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder
                        ("attachment").filename("Your-CV" + user.getId() + ".pdf")
                        .build()
        );

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @PostMapping("/send-cv-to-email")
    public ResponseEntity<?> sendCVByEmail(@AuthenticationPrincipal User user,
                                           @Valid @RequestBody SendCVEmailRequest request) {
        sendMailService.sendCVByEmail(user.getId(), request.getRecipientEmail(), request.getMessage());
        return ResponseEntity.ok(new APIResponse("CV sent successfully to " + request.getRecipientEmail()));
    }

    @GetMapping("/get-recommendation")
    public ResponseEntity<?> recommendationFromAI (@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(cvService.recommendationFromAI(user.getId()));
    }


    @GetMapping("/get-my-cv")
    public ResponseEntity<?> getCVById(@AuthenticationPrincipal User id) {
        return ResponseEntity.ok(cvService.getCVById(id.getId()));
    }
}
