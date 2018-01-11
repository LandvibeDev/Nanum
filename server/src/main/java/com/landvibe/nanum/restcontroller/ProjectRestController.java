package com.landvibe.nanum.restcontroller;

import com.landvibe.nanum.model.Board;
import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.model.post.Issue;
import com.landvibe.nanum.service.BoardService;
import com.landvibe.nanum.service.IssueService;
import com.landvibe.nanum.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {

    private ProjectService projectService;
    private BoardService boardService;
    private IssueService issueService;

    @Autowired
    public ProjectRestController(ProjectService projectService, BoardService boardService, IssueService issueService) {
        this.projectService = projectService;
        this.boardService = boardService;
        this.issueService = issueService;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Project> projects() {
        return projectService.getAll();
    }

    @GetMapping("/{projectId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Project project(@PathVariable long projectId) {
        return projectService.getById(projectId);
    }

    @GetMapping("/{projectId}/boards")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Board> board(@PathVariable long projectId) { return boardService.getByProject(projectId); }

    @GetMapping("/{projectId}/issues")
    @ResponseStatus(value =HttpStatus.OK)
    public List<Issue> issue(@PathVariable long projectId) { return issueService.getAllByProjectId(projectId); }

}
