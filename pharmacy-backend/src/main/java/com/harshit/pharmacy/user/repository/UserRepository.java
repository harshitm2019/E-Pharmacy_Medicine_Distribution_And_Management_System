package com.harshit.pharmacy.user.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.enums.UserRole;
import com.harshit.pharmacy.user.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByRole(UserRole userRole);

    boolean existsByEmailAndUserIdNot(String email, Integer userId);

    long countByRoleAndStatus(
            UserRole role,
            UserStatus status
    );

    Page<User> findByRole(UserRole role, Pageable pageable);

    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    Page<User> findByRoleAndEmailContainingIgnoreCase(
            UserRole role,
            String email,
            Pageable pageable
    );


}
