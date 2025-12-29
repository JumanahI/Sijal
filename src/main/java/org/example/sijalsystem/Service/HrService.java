package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.DTO.IN.HrDTOIn;
import org.example.sijalsystem.Model.HR;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Repository.HrRepository;
import org.example.sijalsystem.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HrService {

    private final HrRepository hrRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    public List<HR> getAllHrs(){
        return hrRepository.findAll();
    }


    public void register(HrDTOIn hrDTOIn){
        User user =  userService.createUser(hrDTOIn.getFullName(),hrDTOIn.getUsername(),hrDTOIn.getEmail(),
                hrDTOIn.getPhoneNumber(), hrDTOIn.getAge(),hrDTOIn.getPassword(),"HR");

        HR hr = new HR();
        hr.setAbout(hrDTOIn.getAbout());
        hr.setExperience(hrDTOIn.getExperience());
        hr.setUser(user);
        hrRepository.save(hr);

        user.setHr(hr);
        user.setCreatedAt(LocalDate.now());
        userRepository.save(user);
    }


    public void updateHr(Integer hr_id , HrDTOIn hrDTOIn){
        User user = userRepository.findUserById(hr_id);
        HR hr = hrRepository.findHRById(hr_id);

        if(hr == null ){
            throw new APIException("Hr not found");
        }

        user.setName(hrDTOIn.getFullName());
        user.setUsername(hrDTOIn.getUsername());
        user.setPassword(hrDTOIn.getPassword());
        user.setAge(hrDTOIn.getAge());
        user.setEmail(hrDTOIn.getEmail());
        user.setPhoneNumber(hrDTOIn.getPhoneNumber());

        hr.setUser(user);
        hr.setAbout(hrDTOIn.getAbout());
        hr.setExperience(hrDTOIn.getExperience());
        hrRepository.save(hr);
    }


    public void deleteHr(Integer hr_id){
        HR hr = hrRepository.findHRById(hr_id);
        if(hr == null){
            throw new APIException("Hr not found");
        }
        hrRepository.delete(hr);
    }

    public List<HR> findAllHROrderByHighestRating(){
        return hrRepository.findAllHROrderByHighestRating();
    }

}
