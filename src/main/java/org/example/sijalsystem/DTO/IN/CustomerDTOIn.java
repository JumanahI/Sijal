package org.example.sijalsystem.DTO.IN;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.sijalsystem.vaildationGroups.ValidationGroup1;

@Data
@AllArgsConstructor
public class CustomerDTOIn {

    @NotBlank(message = "username must not be empty" , groups = ValidationGroup1.class)
    private String username;

    @NotBlank(message = "password must not be empty" , groups = ValidationGroup1.class)
    private String password;

    @NotBlank(message = "full name must not be empty" , groups = ValidationGroup1.class)
    private String fullName;

    @Email(message = "email must be valid" , groups = ValidationGroup1.class)
    @NotBlank(message = "email must not be empty" , groups = ValidationGroup1.class)
    private String email;

    @NotBlank(message = "phone number must not be empty" , groups = ValidationGroup1.class)
    private String phoneNumber;

    @NotEmpty(message = "age must not be null" , groups = ValidationGroup1.class)
    private String age;

    private String cvPath;

}
