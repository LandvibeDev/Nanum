package com.landvibe.nanum.restcontroller;

import com.landvibe.nanum.model.User;
import com.landvibe.nanum.model.dto.IssueDto;
import com.landvibe.nanum.model.post.Issue;
import com.landvibe.nanum.model.post.IssueComment;
import com.landvibe.nanum.service.IssueCommentService;
import com.landvibe.nanum.service.IssueService;
import com.landvibe.nanum.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueRestController {
    private UserService userService;
    private IssueService issueService;
    private IssueCommentService issueCommentService;

    public IssueRestController(UserService userService, IssueService issueService, IssueCommentService issueCommentService) {
        this.userService = userService;
        this.issueService = issueService;
        this.issueCommentService = issueCommentService;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public HashMap<String, List<Issue>> getAll() {
        return issueService.getAll();
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Issue create(HttpSession session, @RequestBody IssueDto issueDto) {
        User currentUser = (User) session.getAttribute("user");
        return issueService.create(currentUser, issueDto);
    }

    @GetMapping("/{issueId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Issue getById(@PathVariable long issueId) {
        return issueService.getById(issueId);
    }


//    @PatchMapping(value = "/{issueId}" ,params = {"issueId", "issue"})
    @PatchMapping("/{issueId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Issue update(HttpSession session, @PathVariable long issueId, @RequestBody Issue issue) {
//        System.out.println("issueID + " + issueId + "Issue request body: " + issue);
        return issueService.update(issue, issueId);
    }

    @DeleteMapping("/{issueId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public @ResponseBody void delete(HttpSession session, @PathVariable long issueId) {
        User currentUser = (User) session.getAttribute("user");
        issueService.delete(currentUser, issueId);
    }

    @GetMapping("/{issueId}/comments")
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<IssueComment> getAllIssueComments(@PathVariable long issueId) {
        return issueService.getAllIssueCommentsByIssueId(issueId);
    }

    // TODO:
    // getIssueByUserId
    // getIssueByUserIdAndId
    // getAllIssueByUserId

}
