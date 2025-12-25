package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String name,String username , String email , String phoneNumber , String age , String password ){

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setAge(age);
        user.setCreatedAt(LocalDate.now());
        user.setPassword(password); // todo must be added hash password

        return userRepository.save(user);
    }
}
