package com.landvibe.nanum.repository;

import com.landvibe.nanum.model.Board;
import com.landvibe.nanum.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findAllByProjectId(long studyId);

}