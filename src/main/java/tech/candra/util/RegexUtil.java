package tech.candra.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static Boolean isAlphaOnly(String data) {
        String regex = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    public static Boolean isEmail(String data) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
    public static String generatePassword(){
        SecureRandom sr = new SecureRandom();
        byte[] password = new byte[15];
        sr.nextBytes(password);
        return Base64.getEncoder().encodeToString(password).substring(0, 15);
    }
}
