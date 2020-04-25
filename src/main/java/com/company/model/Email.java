package com.company.model;

public class Email {
    private String email;
    private String text;
    private String sendResult = "Email sent successfully";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }

    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
