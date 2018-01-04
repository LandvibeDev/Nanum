package com.landvibe.nanum.service;

import com.landvibe.nanum.model.Board;
import com.landvibe.nanum.model.Study;
import com.landvibe.nanum.repository.BoardRepository;
import com.landvibe.nanum.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<Board> getAll(){
        return boardRepository.findAll();
    }

    public Board getById(long boardId){
        return boardRepository.findOne(boardId);
    }

    public List<Board> getByStudy(long studyId){
        return boardRepository.findAllByStudyId(studyId);
    }

}
