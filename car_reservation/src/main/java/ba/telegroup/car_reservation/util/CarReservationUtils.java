package ba.telegroup.car_reservation.util;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import org.apache.commons.codec.binary.Hex;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CarReservationUtils {
    public static String hashPassword( String plainText)  {
        MessageDigest digest= null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            return Hex.encodeHexString(digest.digest(plainText.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static <T>  void validate(T info, Validator validator) throws BadRequestException {
        List<ConstraintViolation<T>> violationList=new ArrayList<>();
        violationList.addAll(validator.validate(info));
        if(violationList.size()!=0){
            throw new BadRequestException(violationList.get(0).getMessage());
        }
    }

    public static LocalDate convertToLocalDate(Date date){
        if(date!=null) {
            return LocalDate.ofInstant(date.toInstant(), ZoneId.of("Europe/Belgrade"));
        }
        return null;
    }

    public static String getWeekNumber(LocalDate date){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        return weekNumber+"/"+date.getYear();
    }
}
