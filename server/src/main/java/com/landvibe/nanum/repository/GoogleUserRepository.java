package com.landvibe.nanum.repository;

import com.landvibe.nanum.model.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoogleUserRepository extends JpaRepository<GoogleUser, Long> {
    GoogleUser findBySnsId(String SnsId);
//    boolean existsBysnSId(String SnsId);
}
