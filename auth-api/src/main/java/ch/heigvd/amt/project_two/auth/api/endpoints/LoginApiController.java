package ch.heigvd.amt.project_two.auth.api.endpoints;

import ch.heigvd.amt.project_two.auth.api.LoginApi;
import ch.heigvd.amt.project_two.auth.api.model.User;
import ch.heigvd.amt.project_two.auth.entities.UserEntity;
import ch.heigvd.amt.project_two.auth.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import java.util.Optional;

import static ch.heigvd.amt.project_two.auth.api.util.JWTUtils.issueTokenFor;

@Controller
public class LoginApiController implements LoginApi {

    @Autowired
    UserRepository uR;

    @Override
    public ResponseEntity<String> getJwt(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User user) {
        if(user.getEmail() == null || user.getPassword() == null) {
            return new ResponseEntity<>("missing email or/and password", HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> ue = uR.findById(user.getEmail());

        if(ue.isPresent() && ue.get().getEmail().equals(user.getEmail()) &&
        ue.get().getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(issueTokenFor(user.getEmail()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("wrong password or user does not exist", HttpStatus.BAD_REQUEST);
        }
    }
}
