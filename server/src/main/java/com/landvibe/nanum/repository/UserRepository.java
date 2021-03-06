package com.landvibe.nanum.repository;

import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    User findById(long id);
//    User findBySns(String sns);
    User findByEmail(String Email);
    User findByUsername(String username);
    List<User> findAllByProjects(Project project);

}
