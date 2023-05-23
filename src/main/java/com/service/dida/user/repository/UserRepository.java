package com.service.dida.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.service.dida.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);

}
