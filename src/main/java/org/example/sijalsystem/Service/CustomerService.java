package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.DTO.IN.CustomerDTOIn;
import org.example.sijalsystem.Model.Customer;
import org.example.sijalsystem.Model.User;
import org.example.sijalsystem.Repository.CustomerRepository;
import org.example.sijalsystem.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    public void addCustomer(CustomerDTOIn customerDTOIn){
        User user = userService.createUser(customerDTOIn.getFullName() , customerDTOIn.getUsername() , customerDTOIn.getEmail()
                , customerDTOIn.getPhoneNumber() , customerDTOIn.getAge() , customerDTOIn.getPassword() , "CUSTOMER");

        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);

        user.setCustomer(customer);
        user.setCreatedAt(LocalDate.now());
    }

    public void updateCustomer(Integer userId , CustomerDTOIn customerDTOIn){
        User user = userRepository.findUserById(userId);

        if (user == null){
            throw new APIException("User not register");
        }
        user.setEmail(customerDTOIn.getEmail());
        user.setName(customerDTOIn.getFullName());
        user.setPhoneNumber(customerDTOIn.getPhoneNumber());
        user.setUsername(customerDTOIn.getUsername());
        user.setUsername(customerDTOIn.getUsername());
        user.setPassword(customerDTOIn.getPassword());
        userRepository.save(user);
    }


    public void deleteCustomer(Integer userId){
        Customer customer = customerRepository.findCustomerById(userId);

        if (customer == null){
            throw new APIException("customer not found");
        }
        customerRepository.delete(customer);
    }
}
