package com.adj.happypet.Model;

public class Feedback_list {
    private String feedback, feedbackId, userId;

    public Feedback_list() {

    }

    public Feedback_list(String feedback, String feedbackId, String userId) {
        this.feedback = feedback;
        this.feedbackId = feedbackId;
        this.userId = userId;
    }


    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
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
}
