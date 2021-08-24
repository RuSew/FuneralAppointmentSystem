package com.tutorial.funeralappointment;

public class MailConfig {
    // Sender credentials
    private final String email = "planfuneralnow@gmail.com";
    private final String password = "abc1234*";

//    Appointment Cancellation
//
//    Dear Sir/Madam,
//    Please note that the appointment that was scheduled on the - (date and time ) is cancelled. Please be kind enough to reschedule your appointment.
//    Your reference number is-
//
//    Thank you.

    // Email text
    private String subject;
    private String message;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
