package com.landvibe.nanum.model.dto;

import com.landvibe.nanum.model.post.Issue;
import lombok.Data;

@Data
public class IssueDto {
    Issue issue;
    long projectId;
}
