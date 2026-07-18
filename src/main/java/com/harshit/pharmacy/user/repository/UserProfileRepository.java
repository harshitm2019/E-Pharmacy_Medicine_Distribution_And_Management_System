package com.harshit.pharmacy.user.repository;

import com.harshit.pharmacy.user.entity.User;
import com.harshit.pharmacy.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {


    Optional<UserProfile> findByUser(User user);
}
