package com.landvibe.nanum.restcontroller;

import com.landvibe.nanum.model.dto.IssueDto;
import com.landvibe.nanum.model.post.Issue;
import com.landvibe.nanum.service.IssueService;
import com.landvibe.nanum.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueRestController {
    private UserService userService;
    private IssueService issueService;

    public IssueRestController(UserService userService, IssueService issueService) {
        this.userService = userService;
        this.issueService = issueService;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public HashMap<String, List<Issue>> getAllIssuePost() {
        return issueService.getAll();
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Issue createIssuePost(HttpSession session, @RequestBody IssueDto issueDto) {
        System.out.println("issue create request: " + issueDto);
        return issueService.create(session, issueDto);
    }

    @GetMapping("/{issueId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Issue getIssuePostById(@PathVariable long issueId) {
        return issueService.getById(issueId);
    }

    //    @DeleteMapping("/{issueId}")
    @RequestMapping(value = "/{issueId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteIssuePostById(HttpSession session, @PathVariable long issueId) {
        issueService.delete(session, issueId);
    }

    // TODO:
    // getIssuePostByUserId
    // getIssuePostByProjectId
    // getIssuePostByUserIdAndPostId
    // getAllIssueByUserId

}
