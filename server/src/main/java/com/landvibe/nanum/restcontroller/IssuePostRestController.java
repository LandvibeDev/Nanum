package com.landvibe.nanum.restcontroller;

import com.landvibe.nanum.model.post.IssuePost;
import com.landvibe.nanum.repository.IssuePostRepository;
import com.landvibe.nanum.service.IssuePostService;
import com.landvibe.nanum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssuePostRestController {
    private UserService userService;
    private IssuePostService issuePostService;

    public IssuePostRestController(UserService userService, IssuePostService issuePostService) {
        this.userService = userService;
        this.issuePostService = issuePostService;
    }

    // getAllIssueByUserId
    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public HashMap<String, List<IssuePost>> getAllIssuePost() {
        return issuePostService.getAll();
    }

    @GetMapping("/{issueId}")
    @ResponseStatus(value = HttpStatus.OK)
    public IssuePost getIssuePostById(@PathVariable long issueId) {
        return issuePostService.getById(issueId);
    }

    // getIssuePostByUserId
    // getIssuePostByProjectId
    // getIssuePostByUserIdAndPostId
    // createIssuePost
    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createIssuePost() {
        // issuePostSerice.create();
    }


}
