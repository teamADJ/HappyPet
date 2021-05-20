package com.adj.happypet.Model;

public class Feedback_list {
    private String feedbackId,userId,adminId,email_admin,feedback;

    public Feedback_list() {

    }

    public Feedback_list(String feedbackId, String userId, String adminId, String email_admin, String feedback) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.adminId = adminId;
        this.email_admin = email_admin;
        this.feedback = feedback;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getEmail_admin() {
        return email_admin;
    }

    public void setEmail_admin(String email_admin) {
        this.email_admin = email_admin;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
