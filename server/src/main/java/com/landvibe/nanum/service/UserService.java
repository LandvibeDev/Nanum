package com.landvibe.nanum.service;

import com.landvibe.nanum.model.User;
import com.landvibe.nanum.repository.ProjectRepository;
import com.landvibe.nanum.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    public UserService(UserRepository userRepository,ProjectRepository projectRepository){
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Set<User> getByProjectId(long projectId){
        return projectRepository.findById(projectId).getUsers();
    }

}
