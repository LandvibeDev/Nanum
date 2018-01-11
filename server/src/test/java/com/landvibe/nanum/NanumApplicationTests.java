package com.landvibe.nanum;

import com.landvibe.nanum.model.Board;
import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.model.User;
import com.landvibe.nanum.repository.BoardRepository;

import com.landvibe.nanum.repository.ProjectRepository;
import com.landvibe.nanum.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NanumApplicationTests {

	@Autowired
    ProjectRepository projectRepository;

	@Autowired
    UserRepository userRepository;

	@Autowired
	BoardRepository boardRepository;


	@Test
	public void projectTest() {
		projectRepository.save(new Project("Jeong's Project"));
		projectRepository.save(new Project("LOL Project"));

	}

	@Test
	public void boardTest() {
//		Board board = new Board("Math",1);
//		Board board2 = new Board("English",1);
//		Project project = projectRepository.findById(1);
//		board.setProject(project);
//		board2.setProject(project);
//		boardRepository.save(board);
//		boardRepository.save(board2);
//		projectRepository.save(project);
		List<Board> boards = boardRepository.findAll();
		System.out.println(boards.get(0));


	}

	@Test
    @Transactional
    public void UserTest() {
	    userRepository.save(new User("sol", "sol@naver.com"));
        userRepository.save(new User("jdb", "jgb@naver.com"));

        List<User> list = userRepository.findAll();
        for (User user : list){
            System.out.println(user);
        }
    }

}
