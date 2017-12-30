package com.landvibe.nanum.repository;

import com.landvibe.nanum.model.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    List<Study> findByTitle(String title);
    Study findById(long id);

}