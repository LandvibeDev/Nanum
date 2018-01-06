package com.landvibe.nanum.service;

import com.landvibe.nanum.model.post.IssuePost;
import com.landvibe.nanum.repository.IssuePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class IssuePostService {

    private IssuePostRepository issuePostRepository;

    public IssuePostService(IssuePostRepository issuePostRepository) {
        this.issuePostRepository = issuePostRepository;
    }

    public HashMap<String, List<IssuePost>> getAll() {
        List<IssuePost> issuePostList = issuePostRepository.findAll();
//        HashMap<String, IssuePost> issuePostMap= new HashMap<String, IssuePost>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        HashMap<String, List<IssuePost>> issuePostMap = new HashMap<>();
        String dateTime, nextDateTime;
        if (!issuePostList.isEmpty()) {
            dateTime = dateFormat.format(issuePostList.get(0).getCreatedAt().getTime());
//            System.out.println("dateTime: " + dateTime);
            List<IssuePost> issuePostListOrderedByDate = new ArrayList<>();
            for (IssuePost issue : issuePostList) {
                nextDateTime = dateFormat.format(issue.getCreatedAt().getTime());
//                System.out.println("nextDateTime: " + nextDateTime);
                if (dateTime.equals(nextDateTime)){
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

    public List<IssuePost> getAllByUserId(long id) {
        return issuePostRepository.findAllByUserId(id);
    }

    // getAllByProjectId

    // createIssuePost
    // updateIssuePost
    // deleteIssuePost

}
