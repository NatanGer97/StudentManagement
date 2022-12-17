package com.backend.StudentManagement.models;


public interface IEmailService {

    void sendSimpleMail(EmailDetails details);

    /*String sendMailWithAttachment(EmailDetails details);*/
}
