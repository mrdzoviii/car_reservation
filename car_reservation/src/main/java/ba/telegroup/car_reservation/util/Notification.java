package ba.telegroup.car_reservation.util;

import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.model.modelCustom.ReservationStateCarUserCompanyLocation;
import ba.telegroup.car_reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Notification {
    private JavaMailSender javaMailSender;
    @Value("${deleted.not}")
    private Byte notDeleted;
    @Value("${role.user}")
    private Integer roleUser;
    @Value("${mail-option.location}")
    private Integer mailOptionLocation;
    @Value("${mail-option.company}")
    private Integer mailOptionCompany;
    @Value("${mail-option.off}")
    private Integer mailOptionOff;
    @Autowired
    public Notification(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }
    @Async
    public void sendLink(String mailTo,String token){
        sendMail(mailTo,"Registration form available on link: \n https://localhost:8443/?q=reg&t="+token);
    }
    @Async
    public void sendNotification(ReservationStateCarUserCompanyLocation reservationInfo,Boolean canceled,List<User> users){
        String mailText="Reservation "+(canceled?"canceled":"made")+" \n Vehicle: "+reservationInfo.getModel()+
                " @"+reservationInfo.getLocationName()+" \n Period: "+
                reservationInfo.getPeriod()+" \nDirection: "+reservationInfo.getDirection()+" \n "+
                ((canceled?"Canceled by:":"Made by:"))+"\nFull name: "+reservationInfo.getFullName()+"\nUsername: "+
                reservationInfo.getUsername();
        users.stream()
                .filter(u->u.getMailOptionId().equals(mailOptionLocation) && u.getLocationId().equals(reservationInfo.getLocationId())
                        && !u.getId().equals(reservationInfo.getUserId()))
                .forEach(u->sendMail(u.getEmail(),mailText));
        users.stream().filter(u->u.getMailOptionId().equals(mailOptionCompany) && !u.getId().equals(reservationInfo.getUserId()))
                .forEach(u->sendMail(u.getEmail(),mailText));
    }
    @Async
    public void sendMail(String mailTo,String mailText){
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setFrom("no-reply@car-reservation.com");
        mail.setTo(mailTo);
        mail.setSubject("Car System Reservation");
        mail.setText(mailText);
        javaMailSender.send(mail);
    }
    @Async
    public void sendNewPassword(String mailTo,String newPassword){
        String text="Your new password: "+newPassword+" .Please change it after first login";
        sendMail(mailTo,text);
    }
}
