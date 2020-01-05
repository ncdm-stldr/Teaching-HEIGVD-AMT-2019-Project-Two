package ch.heigvd.amt.project_two.auth.api.endpoints;

import ch.heigvd.amt.project_two.auth.api.LoginApi;
import ch.heigvd.amt.project_two.auth.api.model.User;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class LoginApiController implements LoginApi {
    @Override
    public ResponseEntity<String> getJwt(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User user) {
        if(user.getPassword().equals("admin")) {
            //TODO return a real token
            return new ResponseEntity<>("fake token owuebfdiwhbedwi", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("wrong password or user does not exist", HttpStatus.UNAUTHORIZED);
        }
    }
}
