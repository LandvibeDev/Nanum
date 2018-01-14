package com.landvibe.nanum.repository;

import com.landvibe.nanum.model.post.IssueComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public
interface IssueCommentRepository extends JpaRepository<IssueComment, Long>{
    List<IssueComment> findAll();
}
