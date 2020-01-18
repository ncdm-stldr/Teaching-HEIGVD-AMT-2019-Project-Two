package ch.heigvd.amt.project_two.auth.api.spec.helpers;

import ch.heigvd.amt.project_two.auth.api.dto.User;

import java.nio.charset.Charset;
import java.util.Random;

public class UserHelper {

    private final static String lowercaseAlpha = "qwertzuioplkjhgfdsayxcvbnm";

    public static User getRandomUser() {
        User u = new User();
        u.setEmail(getRandomAlphaString(6, 15) + '@' + getRandomAlphaString(3, 8) + ".com");
        u.setUsername(getRandomAlphaString(5,10));
        u.setPassword(getRandomAlphaString(10, 10));
        return u;
    }

    public static String getRandomAlphaString(int minLength, int maxLength) {
        Random r = new Random();
        int diff = maxLength - minLength;
        int l = diff > 0 ? r.nextInt(maxLength - minLength) + minLength : minLength;
        byte[] array = new byte[l]; // length is bounded by 7
        for(int i = 0; i < l; ++i) {
            array[i] = (byte) lowercaseAlpha.charAt(r.nextInt(lowercaseAlpha.length()));
        }
        return new String(array, Charset.forName("UTF-8"));
    }

}
