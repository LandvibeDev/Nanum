package com.landvibe.nanum;

import com.landvibe.nanum.model.Board;
import com.landvibe.nanum.model.Study;
import com.landvibe.nanum.model.User;
import com.landvibe.nanum.repository.BoardRepository;

import com.landvibe.nanum.repository.StudyRepository;
import com.landvibe.nanum.repository.UserRepository;
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

	@Autowired
    UserRepository userRepository;

	@Autowired
	BoardRepository boardRepository;


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

	@Test
	public void boardTest() {
//		Board board = new Board("Math",1);
//		Board board2 = new Board("English",1);
//		Study study = studyRepository.findById(1);
//		board.setStudy(study);
//		board2.setStudy(study);
//		boardRepository.save(board);
//		boardRepository.save(board2);
//		studyRepository.save(study);
		List<Board> boards = boardRepository.findAll();
		System.out.println(boards.get(0));


	}

	@Test
    @Transactional
    public void UserTest() {
		User user = new User("sol", "sol@nvarer.com");
		user.setSnsIdentity("288249");
		userRepository.save(user);

//        List<User> list = userRepository.findAll();
//        for (User user : list){
//            System.out.println(user);
//        }
    }

}
