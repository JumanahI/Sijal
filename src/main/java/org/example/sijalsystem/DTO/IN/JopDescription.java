package org.example.sijalsystem.DTO.IN;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JopDescription {

    @Column(columnDefinition = "TEXT")
    private String jobDescription;
}
