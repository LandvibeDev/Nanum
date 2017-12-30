package com.landvibe.nanum;

import com.landvibe.nanum.model.Study;
import com.landvibe.nanum.repository.StudyRepository;
import com.sun.corba.se.impl.presentation.rmi.StubFactoryFactoryDynamicBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NanumApplicationTests {

	@Autowired
	StudyRepository studyRepository;

	@Test
	@Transactional
	public void studyTest() {
		studyRepository.save(new Study("Java Study"));
		studyRepository.save(new Study("C++ Study"));

		List<Study> list = studyRepository.findAll();

		for(Study study : list){
			System.out.println(study);
		}


	}

}
