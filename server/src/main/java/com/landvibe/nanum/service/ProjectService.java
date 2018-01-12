package com.landvibe.nanum.service;

import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.model.User;
import com.landvibe.nanum.repository.ProjectRepository;
import com.landvibe.nanum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,UserRepository userRepository){
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    public Project getById(long id){
        return projectRepository.findById(id);
    }

    @Transactional
    public Project create(Project project,User user){
        User currentUser = userRepository.getOne(user.getId());
        project.setCreator(currentUser);
        project.addUser(currentUser);

        return projectRepository.save(project);
    }
}
