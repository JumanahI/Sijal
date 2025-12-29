package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User createUser(String name, String username, String email,
                           String phoneNumber, String age, String password, String role) {

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setAge(age);
        user.setCreatedAt(LocalDate.now());

        // تشفير كلمة المرور
        user.setPassword(passwordEncoder.encode(password));

        user.setRole(role);
        return userRepository.save(user);
    }

}
