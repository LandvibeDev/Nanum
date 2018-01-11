package com.landvibe.nanum.service;

import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    public Project getById(long id){
        return projectRepository.findById(id);
    }
}
