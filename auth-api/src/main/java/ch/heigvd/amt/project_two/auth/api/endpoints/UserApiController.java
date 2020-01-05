package ch.heigvd.amt.project_two.auth.api.endpoints;

import ch.heigvd.amt.project_two.auth.api.ApiUtil;
import ch.heigvd.amt.project_two.auth.api.UserApi;
import ch.heigvd.amt.project_two.auth.api.model.User;
import ch.heigvd.amt.project_two.auth.api.model.UserWithoutPassword;
import ch.heigvd.amt.project_two.auth.entities.UserEntity;
import ch.heigvd.amt.project_two.auth.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
public class UserApiController implements UserApi {

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<String> changePassword(@ApiParam(value = "email of a user",required=true) @PathVariable("email") String email, @ApiParam(value = "" ,required=true )  @Valid @RequestBody User user) {

        //TODO implement a filter to verify jwt token

        //retrieve the user whose password has to be changed
        Optional<UserEntity> oUE = userRepository.findById(user.getEmail());
        if(oUE.isPresent()) {
            // we update the user entity by setting the new password
            UserEntity uE = oUE.get();
            uE.setPassword(user.getPassword());
            userRepository.save(uE);
            return new ResponseEntity<>("password changed successfully", HttpStatus.OK);
        } else {
            // the user does not exist or jwt token is incorrect
            return new ResponseEntity<>("user not found or unauthorized access", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<String> createUser(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User user) {
        String problem = detectProblemInUser(user);
        if(problem != null) {
            return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
        }
        userRepository.save(userToUserEntity(user));
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserWithoutPassword> getUser(@ApiParam(value = "email of a user",required=true) @PathVariable("email") String email) {
        Optional<UserEntity> uE = userRepository.findById(email);
        if(uE.isPresent()) {
            return new ResponseEntity<>(UserEntityToUserWithoutPassword(uE.get()) ,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }

    UserEntity userToUserEntity(User u){
        UserEntity uE = new UserEntity();
        uE.setUserName(u.getUsername());
        uE.setEmail(u.getEmail());
        uE.setPassword(u.getPassword());
        return uE;
    }

    UserWithoutPassword UserEntityToUserWithoutPassword(UserEntity uE) {
        UserWithoutPassword uWP = new UserWithoutPassword();
        uWP.setEmail(uE.getEmail());
        uWP.setUsername(uE.getUserName());
        return uWP;
    }

    UserWithoutPassword UserToUserWithoutPassword(User u) {
        UserWithoutPassword uWP = new UserWithoutPassword();
        uWP.setEmail(u.getEmail());
        uWP.setUsername(u.getUsername());
        return uWP;
    }

    String detectProblemInUser(User u) {
        final Pattern pattern = Pattern.compile("([a-z0-9][-a-z0-9_\\+\\.]*[a-z0-9])@([a-z0-9][-a-z0-9\\.]*[a-z0-9]\\" +
                ".(arpa|root|aero|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|" +
                "ac|ad|ae|af|ag|ai|al|am|" +
                "an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|c" +
                "g|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga" +
                "|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|" +
                "it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|mg|mh|mk|ml|m" +
                "m|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl" +
                "|pm|pn|pr|ps|pt|pw|py|qa|re|ro|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|" +
                "td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|um|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|y" +
                "t|yu|za|zm|zw)|([0-9]{1,3}\\.{3}[0-9]{1,3}))");
        // we check an email is provided and is in  valid format
        if (u.getEmail() == null || !pattern.matcher(u.getEmail()).find()) {
            return "Invalid email";
        }
        // we check a password and username is provided
        if( u.getPassword() == null || u.getUsername()==null ) {
            return "Missing username or/and password";
        }

        //TODO check if username is already taken and maybe if password is in valid format

        // no problem has been found so we return null
        return null;
    }

}
