package com.landvibe.nanum.repository;

import com.landvibe.nanum.model.post.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>{
    List<Issue> findAll();
    Issue findById(long id);
    List<Issue> findByProjectId(long projectId);
//    List<Issue> findAllByUserId(long userId);
//    findAllByProjectId
}
