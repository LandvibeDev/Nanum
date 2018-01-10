package com.landvibe.nanum.service;

import com.landvibe.nanum.model.GoogleUser;
import com.landvibe.nanum.model.User;
import com.landvibe.nanum.model.post.IssuePost;
import com.landvibe.nanum.model.post.Post;
import com.landvibe.nanum.repository.IssuePostRepository;
import com.landvibe.nanum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class IssuePostService {

    private IssuePostRepository issuePostRepository;
    private UserRepository userRepository;

    public IssuePostService(IssuePostRepository issuePostRepository, UserRepository userRepository) {
        this.issuePostRepository = issuePostRepository;
        this.userRepository = userRepository;
    }

    public HashMap<String, List<IssuePost>> getAll() {
        List<IssuePost> issuePostList = null;
        issuePostList = issuePostRepository.findAll();
//        HashMap<String, IssuePost> issuePostMap= new HashMap<String, IssuePost>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        HashMap<String, List<IssuePost>> issuePostMap = new HashMap<>();
        String dateTime, nextDateTime;
        if (!issuePostList.isEmpty()) {
//        if(issuePostList != null) {
            dateTime = dateFormat.format(issuePostList.get(0).getCreatedAt().getTime());
//            System.out.println("dateTime: " + dateTime);
            List<IssuePost> issuePostListOrderedByDate = new ArrayList<>();
            for (IssuePost issue : issuePostList) {
                nextDateTime = dateFormat.format(issue.getCreatedAt().getTime());
//                System.out.println("nextDateTime: " + nextDateTime);
                if (dateTime.equals(nextDateTime)) {
                    issuePostListOrderedByDate.add(issue);
                } else {
                    issuePostMap.put(dateTime, issuePostListOrderedByDate);
                    issuePostListOrderedByDate = new ArrayList<>();
                    issuePostListOrderedByDate.add(issue);
                    dateTime = nextDateTime;
                }
            }
            issuePostMap.put(dateTime, issuePostListOrderedByDate);
        }
        return issuePostMap;
    }

    public IssuePost getById(long id) {
        return issuePostRepository.findById(id);
    }

//    public List<IssuePost> getAllByUserUid(long id) {
//        return issuePostRepository.findAllByUserId(id);
//    }

    public IssuePost create(HttpSession session, IssuePost issuePost) {
//        System.out.println("session: " + session);
//        GoogleUser creator = googleUserRepository.findOne((long) session.getAttribute("userId"));
        User user = (User) session.getAttribute("user");
        User creator = userRepository.findByEmail(user.getEmail());
//        System.out.println("creator: " + creator.getEmail());
//        Object obj = session.getAttribute("user");
//        System.out.println("obj: " + obj);

        issuePost.setCreator(creator);
        return issuePostRepository.save(issuePost);
    }

    // getAllByProjectId

    // createIssuePost
    // updateIssuePost
    // deleteIssuePost

}
