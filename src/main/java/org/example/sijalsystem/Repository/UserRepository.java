package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
