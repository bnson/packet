/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author bnson
 */
public class mail {

    
    public static boolean checkConnect(Properties props) {
        boolean rs = false;
        try {
            
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(props.getProperty("mail.smtp.username").trim(), props.getProperty("mail.smtp.password").trim());
                }
            });        
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.close();
            rs = true;
        } catch (MessagingException e) {
            System.out.println("Error: Can't send mail!");
            System.out.println("Error: " + e.getMessage());
        }        
        
        if (rs) {
            System.out.println("Message: Connect to mail server is success.");
        }
        
        return rs;
    }
    
    public static void send(Properties props, boolean isErrorMail, String subject, String content, boolean priority) {

        try {
            
            Boolean enable = props.getProperty("mail.smtp.send.enable").trim().equalsIgnoreCase("true");
            String subj = props.getProperty("mail.smtp.subject").trim().replaceAll("[ ]+", "") + " " + subject;
            String sendWhen = props.getProperty("mail.smtp.send.when"); 
            if (enable) {
                
                if (sendWhen.equalsIgnoreCase("error")) {
                    if (!isErrorMail) {
                        return;
                    }
                }
                
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty("mail.smtp.username").trim(), props.getProperty("mail.smtp.password").trim());
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(props.getProperty("mail.smtp.from")));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(props.getProperty("mail.smtp.to")));

                if (priority) {
                    message.setHeader("X-Priority", "1");
                }

                message.setSubject(subj);
                //message.setText(content);
                message.setContent(content, "text/html");

                Transport.send(message);
                //System.out.println("Message: Send mail is succeed.");                
            }
            

        } catch (MessagingException e) {
            System.out.println("Error: Can't send mail!");
            System.out.println("Error: " + e.getMessage() + "");
        }
    }

}
