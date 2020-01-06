package ch.heigvd.amt.project_two.auth.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class JWTUtils {

    private static Algorithm algorithm = Algorithm.HMAC256("8sh37hd63g20wsja2uedfwwzsu");

    /**
     *
     * @param bearerJWT a string beginning with Bearer and followed by a space and a jwt token
     * @param email subject of the jwt (subject field found in payload of the jwt should match this email)
     *              the subject is the person the jwt is meant to be for
     * @return whether the jwt token is signed by auth service and linked to the provided email
     */
    public static boolean verifyToken(String bearerJWT, String email){
        final String prefix = "Bearer ";
        String jwtToken;
        if(bearerJWT.length() > prefix.length()){
            jwtToken = bearerJWT.substring(prefix.length());
        } else {
            return false;
        }
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .withSubject(email)
                .build(); //Reusable verifier instance
        try{
            DecodedJWT jwt = verifier.verify(jwtToken);
        } catch (JWTVerificationException exception) {
            return false;
        }
        return true;
    }

    public static String issueTokenFor(String email) {
        // compute date of day one month later than now
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date oneMonthLaterThanToday = calendar.getTime();

        //create the jwt token
        String jwtToken = JWT.create()
                .withIssuer("auth0")
                .withSubject(email)
                .withExpiresAt(oneMonthLaterThanToday)
                .sign(algorithm);

        return jwtToken;
    }

}
