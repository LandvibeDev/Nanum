package com.landvibe.nanum;

import com.landvibe.nanum.model.User;
import com.landvibe.nanum.model.dto.IssueCommentDto;
import com.landvibe.nanum.model.dto.IssueDto;
import com.landvibe.nanum.model.post.Issue;
import com.landvibe.nanum.model.post.IssueComment;
import com.landvibe.nanum.repository.*;
import com.landvibe.nanum.service.IssueCommentService;
import com.landvibe.nanum.service.IssueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IssueTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    IssueCommentRepository issueCommentRepository;
    @Autowired
    IssueService issueService;
    @Autowired
    IssueCommentService issueCommentService;

    @Test
    @Transactional
    public void issueCommentCreateTest() {
        User user = new User();
        user.setEmail("dltjddhs@gmail.com");

        // create issue
        System.out.println("Create issue Test ===============");
        Issue issue = new Issue();
        issue.setTitle("issue create test title");
        issue.setContent("issue create test content");
        IssueDto issueDto = new IssueDto();
        issueDto.setIssue(issue);
        issueDto.setProjectId(1);
        System.out.println("issue: " + issueService.create(user, issueDto));

        // create issueComment
        System.out.println("Create issueComment Test ===============");
        IssueCommentDto issueCommentDto = new IssueCommentDto();
        IssueCommentDto issueCommentDto2 = new IssueCommentDto();

        IssueComment issueComment = new IssueComment();
        issueComment.setContent("1th comment create test");
        issueCommentDto.setIssueComment(issueComment);
        issueCommentDto.setIssueId(issue.getId());
        System.out.println("issueComment: " + issueCommentService.create(user, issueCommentDto));

        IssueComment issueComment2 = new IssueComment();
        issueComment2.setContent("2nd comment create test");
        issueCommentDto2.setIssueComment(issueComment2);
        issueCommentDto2.setIssueId(issue.getId());
        System.out.println("issueComment: " + issueCommentService.create(user, issueCommentDto2));

        Issue fetchedIssue = issueRepository.findOne(issue.getId());

        int i = 0;
        StringBuffer sb = new StringBuffer();
        for (IssueComment elm : fetchedIssue.getIssueComments()) {
           sb.append(i + "th issuecomment: " + elm.getId() + " " + elm.getContent() + "\n");
            i += 1;
        }
        System.out.println(sb);

        // update issueComment
        System.out.println("Update issueComment Test ===============");
        IssueComment testIssueComment = new IssueComment();
        issueComment.setContent("update udpate update udpate udpate dupate update update");
        IssueComment updatedComment = issueCommentService.update(testIssueComment, issueComment.getId());
        System.out.println("updatedComment: " + updatedComment.getId() + updatedComment.getContent());
        System.out.println("issueComment: " + issueComment.getId() + issueComment.getContent());

        i = 0;
        sb = new StringBuffer();
        fetchedIssue = issueRepository.findOne(issue.getId());
        for (IssueComment elm : fetchedIssue.getIssueComments()) {
            sb.append(i + "th issuecomment: " + elm.getId() + " " + elm.getContent() + "\n");
            i += 1;
        }
        System.out.println(sb);

        // delete issueComment
        System.out.println("Delete issueComment Test ===============");
        issueCommentService.delete(user, issueComment2.getId());
        issueCommentService.delete(user, issueComment.getId());
        i = 0;
        sb = new StringBuffer();
        fetchedIssue = issueRepository.findOne(issue.getId());
        for (IssueComment elm : fetchedIssue.getIssueComments()) {
            sb.append(i + "th issuecomment: " + elm.getId() + " " + elm.getContent() + "\n");
            i += 1;
        }
        System.out.println(sb);



    }
}
