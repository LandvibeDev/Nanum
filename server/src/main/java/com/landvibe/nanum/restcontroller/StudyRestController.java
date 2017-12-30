package com.landvibe.nanum.restcontroller;

import com.landvibe.nanum.model.Study;
import com.landvibe.nanum.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studies")
public class StudyRestController {

    private StudyService studyService;

    @Autowired
    public StudyRestController(StudyService studyService){
        this.studyService = studyService;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Study> studies() {
        return studyService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Study study(@PathVariable long id) {
        return studyService.getById(id);
    }

}