package com.landvibe.nanum.model.dto;

import com.landvibe.nanum.model.post.IssueComment;
import lombok.Data;

@Data
public class IssueCommentDto {
    IssueComment issueComment;
    long issueId;
}
