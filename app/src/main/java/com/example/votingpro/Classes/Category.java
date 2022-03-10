package com.example.votingpro.Classes;

import java.io.Serializable;

public class Category implements Serializable {

    private String CategoryDbId;
    private int Id;
    private String CategoryName;
    private String ProfileImage;
    private String Name;
    private String Status;
    private String Subject;
    private int LikeVotes;
    private int NeutralVotes;
    private int DislikeVotes;
    private int AllVotes;


    public Category() {
    }

    public Category(String categoryName) {
        CategoryName = categoryName;
    }

    public Category(int id, String categoryName, String profileImage) {
        Id = id;
        CategoryName = categoryName;
        ProfileImage = profileImage;
    }

    public String getCategoryDbId() {
        return CategoryDbId;
    }

    public void setCategoryDbId(String categoryDbId) {
        CategoryDbId = categoryDbId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public int getLikeVotes() {
        return LikeVotes;
    }

    public void setLikeVotes(int likeVotes) {
        LikeVotes = likeVotes;
    }

    public int getNeutralVotes() {
        return NeutralVotes;
    }

    public void setNeutralVotes(int neutralVotes) {
        NeutralVotes = neutralVotes;
    }

    public int getDislikeVotes() {
        return DislikeVotes;
    }

    public void setDislikeVotes(int dislikeVotes) {
        DislikeVotes = dislikeVotes;
    }

    public int getAllVotes() {
        return AllVotes;
    }

    public void setAllVotes(int allVotes) {
        AllVotes = allVotes;
    }

    @Override
    public String toString() {
        return "Category{" +
                "UserId=" + CategoryDbId +
                ", Id=" + Id +
                ", CategoryName='" + CategoryName + '\'' +
                ", ProfileImage='" + ProfileImage + '\'' +
                ", Name='" + Name + '\'' +
                ", Status='" + Status + '\'' +
                ", Subject='" + Subject + '\'' +
                ", LikeVotes=" + LikeVotes +
                ", NeutralVotes=" + NeutralVotes +
                ", DislikeVotes=" + DislikeVotes +
                ", AllVotes=" + AllVotes +
                '}';
    }
}
