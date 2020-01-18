package ch.heigvd.amt.project_two.auth.api.spec.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JWTUtils {

    public static boolean looksLikeJwt(String jwt) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$");
        Matcher matcher = pattern.matcher(jwt);
        return matcher.find() && !matcher.find();
    }

}
