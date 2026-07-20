package com.harshit.pharmacy.user.repository;

import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByRole(UserRole userRole);

    boolean existsByEmailAndUserIdNot(String email, Integer userId);

    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
