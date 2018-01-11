package com.landvibe.nanum.restcontroller;

import com.landvibe.nanum.model.Board;
import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.service.BoardService;
import com.landvibe.nanum.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardRestController {

    private ProjectService projectService;
    private BoardService boardService;

    @Autowired
    public BoardRestController(ProjectService projectService, BoardService boardService) {
        this.projectService = projectService;
        this.boardService = boardService;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Board> boards() {
        return boardService.getAll();
    }

    @GetMapping("/{boardId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Board board(@PathVariable long boardId) {
        return boardService.getById(boardId);
    }

}
