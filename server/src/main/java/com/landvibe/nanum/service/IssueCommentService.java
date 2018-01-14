package com.landvibe.nanum.service;

import com.landvibe.nanum.model.User;
import com.landvibe.nanum.model.dto.IssueCommentDto;
import com.landvibe.nanum.model.post.Issue;
import com.landvibe.nanum.model.post.IssueComment;
import com.landvibe.nanum.repository.IssueCommentRepository;
import com.landvibe.nanum.repository.IssueRepository;
import com.landvibe.nanum.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Collection;

@Service
public class IssueCommentService {

    private IssueRepository issueRepository;
    private UserRepository userRepository;
    private IssueCommentRepository issueCommentRepository;

    public IssueCommentService(IssueRepository issuePostRepository,
                               UserRepository userRepository,
                               IssueCommentRepository issueCommentRepository) {
        this.issueRepository = issuePostRepository;
        this.issueCommentRepository = issueCommentRepository;
        this.userRepository = userRepository;
    }

    public Collection<IssueComment> getAll() {
        return issueCommentRepository.findAll();
    }

    public IssueComment getById(long issueCommentId) {
        return issueCommentRepository.findOne(issueCommentId);
    }

    @Transactional
    public IssueComment create(User currentUser, IssueCommentDto issueCommentDto) {
        User creator = userRepository.findByEmail(currentUser.getEmail());
        Issue issue = issueRepository.getOne(issueCommentDto.getIssueId());
        IssueComment issueComment = issueCommentDto.getIssueComment();
        issue.getIssueComments().add(issueComment);
        issueComment.setCreator(creator);
        return issueCommentRepository.save(issueComment);
    }

    @Transactional
    public IssueComment update(IssueComment issueComment, long issueCommentId) {
        IssueComment fetchedIssueComment = issueCommentRepository.findOne(issueCommentId);
        if (fetchedIssueComment == null){
            System.out.println("nulllll");
            return null;
        }
        fetchedIssueComment.setUpdatedAt(Calendar.getInstance());
        issueCommentRepository.save(fetchedIssueComment);
        return fetchedIssueComment;
    }

    @Transactional
    public void delete(User currentUser, long issueCommentId) {
        // TODO: User 가 쓴 글인지 확인 후 삭제
        IssueComment issueComment = issueCommentRepository.findOne(issueCommentId);
        if (issueComment != null) {
            issueCommentRepository.delete(issueComment);
        }
    }
}
