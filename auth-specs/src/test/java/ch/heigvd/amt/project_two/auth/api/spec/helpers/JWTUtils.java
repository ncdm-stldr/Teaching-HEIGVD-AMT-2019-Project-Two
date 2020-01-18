//package ch.heigvd.amt.project_two.auth.api.spec.helpers;
//
//public class JWTUtils {
//
//    /**
//     *
//     * @param bearerJWT a string beginning with Bearer and followed by a space and a jwt token
//     * @param email subject of the jwt (subject field found in payload of the jwt should match this email)
//     *              the subject is the person the jwt is meant to be for
//     * @return whether the jwt token is signed by auth service and linked to the provided email
//     */
//    public static boolean verifyToken(String bearerJWT, String email){
//        final String prefix = "Bearer ";
//        String jwtToken;
//        if(bearerJWT.length() > prefix.length()){
//            jwtToken = bearerJWT.substring(prefix.length());
//        } else {
//            return false;
//        }
//        JWTVerifier verifier = JWT.require(algorithm)
//                .withIssuer("auth0")
//                .withSubject(email)
//                .build(); //Reusable verifier instance
//        try{
//            DecodedJWT jwt = verifier.verify(jwtToken);
//        } catch (JWTVerificationException exception) {
//            return false;
//        }
//        return true;
//    }
//
//
//}
