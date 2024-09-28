package com.codingshuttle.linkedin.user_service.reposiotry;

import com.codingshuttle.linkedin.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}