package com.landvibe.nanum.restcontroller;

import com.landvibe.nanum.handler.CodeManager;
import com.landvibe.nanum.handler.CodeRoom;
import com.landvibe.nanum.model.Board;
import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.model.User;
import com.landvibe.nanum.model.dto.TreeFile;
import com.landvibe.nanum.model.post.Issue;
import com.landvibe.nanum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {

    private ProjectService projectService;
    private BoardService boardService;
    private IssueService issueService;
    private UserService userService;
    private CodeService codeService;

    @Autowired
    public ProjectRestController(ProjectService projectService, BoardService boardService, IssueService issueService,UserService userService,CodeService codeService) {
        this.projectService = projectService;
        this.boardService = boardService;
        this.issueService = issueService;
        this.userService = userService;
        this.codeService = codeService;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Project> projects() {
        return projectService.getAll();
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Project addProject(@RequestBody Project project,HttpSession session) {
        return projectService.create(project,(User)session.getAttribute("user"));
    }

    @GetMapping("/{projectId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Project project(@PathVariable long projectId) {
        return projectService.getById(projectId);
    }

    @GetMapping("/{projectId}/boards")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Board> board(@PathVariable long projectId) { return boardService.getByProject(projectId); }

    @GetMapping("/{projectId}/users")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<User> user(@PathVariable long projectId) { return userService.getByProjectId(projectId); }


    @GetMapping("/{projectId}/issues")
    @ResponseStatus(value =HttpStatus.OK)
    public HashMap<String, List<Issue>> issue(@PathVariable long projectId) { return issueService.getAllByProjectId(projectId); }

    @GetMapping("/{projectId}/directories")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TreeFile> getFiles(@PathVariable String projectId) {
        return codeService.getFiles(projectId);
    }

    @GetMapping("/{projectId}/directories/{directoryName}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TreeFile> getFilesByDirectory(@PathVariable String projectId,@PathVariable String directoryName,@RequestParam("path") String path) {
        return codeService.getFilesByDirectory(projectId,directoryName,path);
    }

    @GetMapping("/{projectId}/files/{fileName:.+}")
    @ResponseStatus(value = HttpStatus.OK)
    public String getFile(@PathVariable String projectId,@PathVariable String fileName,@RequestParam("path") String path) {
        return codeService.getFile(projectId,fileName,path);
    }

    @DeleteMapping("/{projectId}/files/{fileName:.+}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFile(@PathVariable String projectId,@PathVariable String fileName,@RequestParam("path") String path) {
        codeService.deleteFile(projectId,fileName,path);
    }

    @PostMapping("/{projectId}/files/{fileName:.+}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addFile(@PathVariable String projectId,@PathVariable String fileName,@RequestBody Map<String,String> body) {
        codeService.addFile(projectId,fileName,body.get("path"),body.get("type"));
    }

    @PutMapping("/{projectId}/files/{fileName:.+}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFile(@PathVariable String projectId,@PathVariable String fileName,@RequestBody Map<String,String> body) {
        codeService.updateFile(projectId,fileName,body.get("path"),body.get("content"));
    }

    @PutMapping("/{projectId}/moveFiles/{fileName:.+}")
    @ResponseStatus(value = HttpStatus.OK)
    public void moveFile(@PathVariable String projectId,@PathVariable String fileName,@RequestBody Map<String,String> body) {
        codeService.moveFile(projectId,fileName,body.get("path"),body.get("newPath"),body.get("type"));
    }

    @GetMapping("/{projectId}/modifyingFiles")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CodeRoom> getCodeRooms(@PathVariable String projectId) {
        return codeService.getCodeRooms();
    }
}
