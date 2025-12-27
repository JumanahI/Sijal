package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer , Integer> {
    Customer findCustomerByUser_Id(Integer userId);

    Customer findCustomerById(Integer id);
}
