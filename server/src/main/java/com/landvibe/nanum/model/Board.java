package com.landvibe.nanum.model;

import com.fasterxml.jackson.annotation.JsonFormat;


import javax.persistence.*;
import java.util.Calendar;


@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String title;

    @Column
    private int type;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Calendar createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Calendar updatedAt;

    @ManyToOne(targetEntity=Project.class, fetch=FetchType.EAGER)
    @JoinColumn(name="project_id")
    private Project project;

    public Board(){

    }

    public Board(String title,int type) {
        this.title = title;
        this.type = type;
        this.createdAt = Calendar.getInstance();
        this.updatedAt = this.createdAt;

    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", project=" + project +
                '}';
    }
}