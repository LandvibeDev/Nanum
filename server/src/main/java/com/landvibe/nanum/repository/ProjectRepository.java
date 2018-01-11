package com.landvibe.nanum.repository;

import com.landvibe.nanum.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByTitle(String title);
    Project findById(long id);

}