package com.landvibe.nanum.service;

import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.model.User;
import com.landvibe.nanum.model.dto.IssueDto;
import com.landvibe.nanum.model.post.Issue;
import com.landvibe.nanum.repository.IssueRepository;
import com.landvibe.nanum.repository.ProjectRepository;
import com.landvibe.nanum.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class IssueService {

    private IssueRepository issueRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    public IssueService(IssueRepository issuePostRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.issueRepository = issuePostRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public HashMap<String, List<Issue>> getAll() {
        List<Issue> issueList = null;
        issueList = issueRepository.findAll();
//        HashMap<String, Issue> issuePostMap= new HashMap<String, Issue>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        HashMap<String, List<Issue>> issuePostMap = new HashMap<>();
        String dateTime, nextDateTime;
        if (!issueList.isEmpty()) {
//        if(issueList != null) {
            dateTime = dateFormat.format(issueList.get(0).getCreatedAt().getTime());
//            System.out.println("dateTime: " + dateTime);
            List<Issue> issueListOrderedByDate = new ArrayList<>();
            for (Issue issue : issueList) {
                nextDateTime = dateFormat.format(issue.getCreatedAt().getTime());
//                System.out.println("nextDateTime: " + nextDateTime);
                if (dateTime.equals(nextDateTime)) {
                    issueListOrderedByDate.add(issue);
                } else {
                    issuePostMap.put(dateTime, issueListOrderedByDate);
                    issueListOrderedByDate = new ArrayList<>();
                    issueListOrderedByDate.add(issue);
                    dateTime = nextDateTime;
                }
            }
            issuePostMap.put(dateTime, issueListOrderedByDate);
        }
        return issuePostMap;
    }

    public Issue getById(long id) {
        return issueRepository.findById(id);
    }

//    public List<Issue> getAllByUserUid(long id) {
//        return issuePostRepository.findAllByUserId(id);
//    }

    public Issue create(HttpSession session, IssueDto issueDto) {
        User user = (User) session.getAttribute("user");
        User creator = userRepository.findByEmail(user.getEmail());
        Project project = projectRepository.findOne(issueDto.getProjectId());
        Issue issue = issueDto.getIssue();

        issue.setCreator(creator);
        issue.setProject(project);
        return issueRepository.save(issue);
    }

    // getAllByProjectId

    // createIssuePost
    // updateIssuePost
    // deleteIssuePost
    @Transactional
    public void delete(HttpSession session, long id) {
        // TODO: User 가 쓴 게시글인지 확인 후 삭제
        Issue issue = issueRepository.findOne(id);
        long userId = (long) session.getAttribute("userId");
//        if (blog == null) { // ceate class
//            throw new NotFoundException("해당 이슈는 존재하지 않습니다..");
//        }
//        if (user.getId() != blog.getCreator().getId()) { // use spring security
//            throw new AccessDeniedException("해당 게시물을 수정할 수 있는 권한이 없습니다.");
//        }
        if (userId != issue.getCreator().getId())
            if (issue != null) {
                issueRepository.delete(issue);
            }
    }

    public List<Issue> getAllByProjectId(long projectId) {
        return issueRepository.findByProjectId(projectId);
    }
}
