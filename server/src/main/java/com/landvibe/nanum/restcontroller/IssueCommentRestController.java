package com.landvibe.nanum.restcontroller;

import com.landvibe.nanum.model.User;
import com.landvibe.nanum.model.dto.IssueCommentDto;
import com.landvibe.nanum.model.post.IssueComment;
import com.landvibe.nanum.service.IssueCommentService;
import com.landvibe.nanum.service.IssueService;
import com.landvibe.nanum.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@RestController
@RequestMapping("/api/issue-comments")
public class IssueCommentRestController {

    private UserService userService;
    private IssueService issueService;
    private IssueCommentService issueCommentService;

    public IssueCommentRestController(UserService userService, IssueService issueService, IssueCommentService issueCommentService) {
        this.userService = userService;
        this.issueService = issueService;
        this.issueCommentService = issueCommentService;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<IssueComment> getAll(){
        return issueCommentService.getAll();
    }

    @GetMapping("/{issueCommentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public IssueComment getById(@PathVariable long issueCommentId) {
        return issueCommentService.getById(issueCommentId);
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public IssueComment create(HttpSession session, @RequestBody IssueCommentDto issueCommentDto) {
        User currentUser = (User) session.getAttribute("user");
        return issueCommentService.create(currentUser, issueCommentDto);
    }

    @PatchMapping("/{issueCommentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public IssueComment update(HttpSession session, @PathVariable long issueCommentId, @RequestBody IssueComment issueComment) {
        User currentUser = (User) session.getAttribute("user");
        return issueCommentService.update(issueComment, issueCommentId);
    }

    @DeleteMapping("/{issueCommentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(HttpSession session, @PathVariable long issueCommentId){
        User currentUser = (User) session.getAttribute("user");
        issueCommentService.delete(currentUser, issueCommentId);
    }

}
