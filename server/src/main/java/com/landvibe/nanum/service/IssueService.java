package com.landvibe.nanum.service;

import com.landvibe.nanum.model.Project;
import com.landvibe.nanum.model.User;
import com.landvibe.nanum.model.dto.IssueDto;
import com.landvibe.nanum.model.post.Issue;
import com.landvibe.nanum.model.post.IssueComment;
import com.landvibe.nanum.repository.IssueCommentRepository;
import com.landvibe.nanum.repository.IssueRepository;
import com.landvibe.nanum.repository.ProjectRepository;
import com.landvibe.nanum.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IssueService {

    private IssueRepository issueRepository;
    private IssueCommentRepository issueCommentRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    public IssueService(IssueRepository issuePostRepository,
                        IssueCommentRepository issueCommentRepository,
                        UserRepository userRepository,
                        ProjectRepository projectRepository) {
        this.issueRepository = issuePostRepository;
        this.issueCommentRepository = issueCommentRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public HashMap<String, List<Issue>> makeHashMap(long projectId) {
        List<Issue> issueList = null;
        if (projectId == -1){
            // getAll
            issueList = issueRepository.findAll();
        } else {
            // getAllByProjectId
            issueList = issueRepository.findByProjectId(projectId);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        HashMap<String, List<Issue>> issuePostMap = new HashMap<>();
        String dateTime, nextDateTime;
        if (!issueList.isEmpty()) {
            dateTime = dateFormat.format(issueList.get(0).getCreatedAt().getTime());
            List<Issue> issueListOrderedByDate = new ArrayList<>();
            for (Issue issue : issueList) {
                nextDateTime = dateFormat.format(issue.getCreatedAt().getTime());
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

    public HashMap<String, List<Issue>> getAll() {
        return makeHashMap(-1); // -1: all projects
    }

    public Issue getById(long issueId) {
        return issueRepository.findById(issueId);
    }

    public Collection<IssueComment> getAllIssueCommentsByIssueId(long issueId) {
        Issue issue  = issueRepository.findOne(issueId);
        return issue.getIssueComments();
    }

    public HashMap<String, List<Issue>> getAllByProjectId(long projectId) {
        return makeHashMap(projectId);
    }

    @Transactional
    public Issue create(User currentUser, IssueDto issueDto) {
        User creator = userRepository.findByEmail(currentUser.getEmail());
        Project project = projectRepository.findOne(issueDto.getProjectId());
        Issue issue = issueDto.getIssue();

        issue.setCreator(creator);
        issue.setProject(project);
        // issueComment 생성
        // issueComment 를 issueComments list에 추가
        IssueComment firstComment = new IssueComment();
        firstComment.setContent(issue.getContent());
        firstComment.setCreator(creator);

        issueCommentRepository.save(firstComment);
        firstComment.setCreator(creator);
        Collection<IssueComment> issueComments = issue.getIssueComments();
        issueComments.add(firstComment);
        return issueRepository.save(issue);
    }

    // For Test
    @Transactional
    public Issue create(User currentUser, Issue issue) {
        User creator = userRepository.findByEmail(currentUser.getEmail());

        issue.setCreator(creator);
        // issueComment 생성
        // issueComment 를 issueComments list에 추가
        IssueComment tempComment = new IssueComment();
        tempComment.setContent(issue.getContent());
        issueCommentRepository.save(tempComment);
        tempComment.setCreator(creator);
        Collection<IssueComment> issueComments = issue.getIssueComments();
        issueComments.add(tempComment);
        return issueRepository.save(issue);
    }

    @Transactional
    public Issue update(Issue issue, long issueId) {
        Issue fetchedIssue = issueRepository.findOne(issueId);
        if (fetchedIssue == null){
            return null;
        }
        fetchedIssue.setTitle(issue.getTitle());
        fetchedIssue.setContent(issue.getContent());
        fetchedIssue.setUpdatedAt(Calendar.getInstance());
        issueRepository.save(fetchedIssue);
        return fetchedIssue;
    }

    @Transactional
    public void delete(User currentUser, long issueId) {
        // TODO: User 가 쓴 게시글인지 확인 후 삭제
        Issue issue = issueRepository.findOne(issueId);
//        long userId = (long) session.getAttribute("userId");
//        if (blog == null) { // ceate class
//            throw new NotFoundException("해당 이슈는 존재하지 않습니다..");
//        }
//        if (user.getId() != blog.getCreator().getId()) { // use spring security
//            throw new AccessDeniedException("해당 게시물을 수정할 수 있는 권한이 없습니다.");
//        }
//        if (userId != issue.getCreator().getId())
        if (issue != null) {
            issueRepository.delete(issue);
        }
    }
}
