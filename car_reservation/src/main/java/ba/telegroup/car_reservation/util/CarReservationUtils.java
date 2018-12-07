package ba.telegroup.car_reservation.util;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import org.apache.commons.codec.binary.Hex;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
}
