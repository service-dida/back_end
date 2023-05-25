package com.service.dida.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.service.dida.domain.user.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);

    Optional<User> findByEmail(String email);

    Optional<Boolean> existsByEmail(String email);

    Optional<Boolean> existsByNickname(String nickname);
}
