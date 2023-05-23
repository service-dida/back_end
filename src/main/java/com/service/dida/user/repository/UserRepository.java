package com.service.dida.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);

}
