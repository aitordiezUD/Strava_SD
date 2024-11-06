package Strava.facade;
import Strava.dto.CredentialsDTO;
import Strava.dto.RegisterRequestDTO;
import Strava.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Controller", description = "Login, logout and register operations.")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {this.authService = authService;}

    //Login endpoint
    @Operation(
            summary = "Login to the system",
            description = "Allows a user to login by providing email and password. Returns a token if successful.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK: Login successful, returns a token"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials, login failed"),
            }
    )

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(name="credentials", description="User's login credentials", required = true)
            @RequestBody CredentialsDTO credentials
    )
    {
        Optional<String> token = authService.login(credentials.getEmail(), credentials.getPassword());
        if (token.isPresent())
        {
            return new ResponseEntity<>(token.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    //Logout endpoint
    @Operation(
            summary = "Logout from the system.",
            description = "Allows the user to logout from the system by providing the authorization token.",
            responses =
                    {
                            @ApiResponse(responseCode = "204", description = "No content logout successful"),
                            @ApiResponse(responseCode = "401", description = "Unauthorized logout")
                    }
    )
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Parameter(name = "token", description = "authorization token")
            @RequestBody String token
    ){
        Optional<Boolean> result = authService.logout(token);
        if (result.isPresent() && result.get()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    //Register endpoint
    @Operation(
            summary = "Register in the system",
            description = "Allows the register in the system by providing details.",
            responses =
                    {
                            @ApiResponse(responseCode = "201", description = "Successful registration"),
                            @ApiResponse(responseCode = "400", description = "Bad request")
                    }
    )
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Parameter(name = "registerRequest", description = "User's register details", required = true)
            @RequestBody RegisterRequestDTO registerRequest
    ){
        if (authService.register(registerRequest.getEmail(), registerRequest.getName(), registerRequest.getBirthday(), registerRequest.getAuthProviderStr(), registerRequest.getPassword(),
                registerRequest.getWeight(), registerRequest.getHeight(), registerRequest.getMaxHeartRate(), registerRequest.getRestingHeartRate()))
        {return new ResponseEntity<>(HttpStatus.CREATED);}else {return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}

    }
}