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
    public Boolean sendLink(String mailTo,String token){
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setFrom("no-reply@car-reservation.com");
        mail.setTo(mailTo);
        mail.setSubject("Car System Reservation");
        String mailText="Registration form available on link: \n http://localhost:8100/?q=reg&t="+token;
        mail.setText(mailText);
        javaMailSender.send(mail);
        return true;
    }
}
