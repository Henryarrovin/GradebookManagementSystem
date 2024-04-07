package com.project.gradebookmanagementsystem.repositories;

import com.project.gradebookmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
