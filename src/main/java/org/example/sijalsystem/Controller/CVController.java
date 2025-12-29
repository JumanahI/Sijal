package org.example.sijalsystem.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sijalsystem.DTO.IN.CvUploadResponse;
import org.example.sijalsystem.Model.CV;
import org.example.sijalsystem.Service.CVService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cvs")
@RequiredArgsConstructor
@Slf4j
public class CVController {

    private final CVService cvService;

    @GetMapping("/{id}")
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
    public ResponseEntity<?> updateCV(@RequestParam("file") MultipartFile file, @RequestParam("customerId") Integer customerId) {

        log.info("Received CV update request for customer: {}", customerId);
        CvUploadResponse response = cvService.updateCustomerCV(file, customerId);
        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<?> deleteCustomerCV(@PathVariable Integer customerId) {
        cvService.deleteCustomerCV(customerId);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/customer/{customerId}")
    public CV addCv(@PathVariable Integer customerId, @RequestBody CV cv) {
        return cvService.addCv(customerId, cv);
    }

}