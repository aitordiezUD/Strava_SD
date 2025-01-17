package Strava.externals;

import Strava.entity.Challenge;
import Strava.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    public void sendChallengeCreationMessage(User user, Challenge challenge){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        String subject = user.getName() + ", you have set up a challenge!";
        message.setSubject(subject);
        message.setTo(user.getEmail());
        String text = "Hello " + user.getName() + ",\n\n" +
                "You have set up a new challenge: " + challenge.getName() + "\n" +
                "The challenge will start on " + challenge.getStartDate() + " and end on " + challenge.getEndDate() + "\n" +
                "Good luck!";
        message.setText(text);
        emailSender.send(message);
    }
}
