package com.landvibe.nanum.service;

import com.landvibe.nanum.model.Study;
import com.landvibe.nanum.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyService {

    private StudyRepository studyRepository;

    @Autowired
    public StudyService(StudyRepository studyRepository){
        this.studyRepository = studyRepository;
    }

    public List<Study> getAll(){
        return studyRepository.findAll();
    }
}
