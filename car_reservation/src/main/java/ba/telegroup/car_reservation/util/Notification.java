package ba.telegroup.car_reservation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Notification {
    private JavaMailSender javaMailSender;

    @Autowired
    public Notification(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }
    @Async
    public Boolean sendMail(String mailTo,String subject,String message){
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setFrom("no-reply@car-reservation.com");
        mail.setTo(mailTo);
        mail.setSubject(subject);
        mail.setText(message);
        javaMailSender.send(mail);
        return true;
    }
}
