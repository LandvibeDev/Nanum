package com.landvibe.nanum.repository;

import com.landvibe.nanum.model.post.IssuePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuePostRepository extends JpaRepository<IssuePost, Long>{
    List<IssuePost> findAll();
    List<IssuePost> findByTitle(String title);
    IssuePost findById(long id);
    List<IssuePost> findAllByUserId(long userId);
//    findAllByProjectId
}
