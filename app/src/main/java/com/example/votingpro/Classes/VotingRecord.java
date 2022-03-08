package com.example.votingpro.Classes;

public class VotingRecord {

    private String categoryName;
    private String candidateName;
    private String status;
    private String VotingSubject;
    private String dayOfVotes;
    private String VotingTime;
    private String VotingComment;
    private boolean IsVoting;


    public VotingRecord() {
    }

    public VotingRecord(String votingSubject, String votingTime, String votingComment, boolean isVoting) {
        VotingSubject = votingSubject;
        VotingTime = votingTime;
        VotingComment = votingComment;
        IsVoting = isVoting;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVotingSubject() {
        return VotingSubject;
    }

    public void setVotingSubject(String votingSubject) {
        VotingSubject = votingSubject;
    }

    public String getDayOfVotes() {
        return dayOfVotes;
    }

    public void setDayOfVotes(String dayOfVotes) {
        this.dayOfVotes = dayOfVotes;
    }

    public String getVotingTime() {
        return VotingTime;
    }

    public void setVotingTime(String votingTime) {
        VotingTime = votingTime;
    }

    public String getVotingComment() {
        return VotingComment;
    }

    public void setVotingComment(String votingComment) {
        VotingComment = votingComment;
    }

    public boolean isVoting() {
        return IsVoting;
    }

    public void setVoting(boolean voting) {
        IsVoting = voting;
    }

    @Override
    public String toString() {
        return "VotingRecord{" +
                "categoryName='" + categoryName + '\'' +
                ", candidateName='" + candidateName + '\'' +
                ", status='" + status + '\'' +
                ", VotingSubject='" + VotingSubject + '\'' +
                ", dayOfVotes='" + dayOfVotes + '\'' +
                ", VotingTime='" + VotingTime + '\'' +
                ", VotingComment='" + VotingComment + '\'' +
                ", IsVoting=" + IsVoting +
                '}';
    }
}
