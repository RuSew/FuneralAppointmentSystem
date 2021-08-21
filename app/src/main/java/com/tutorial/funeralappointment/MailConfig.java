package com.tutorial.funeralappointment;

public class MailConfig {
    // Sender credentials
    private final String email = "planfuneralnow@gmail.com";
    private final String password = "abc1234*";

    // Email text
    private String subject = "";
    private String message = "";

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
