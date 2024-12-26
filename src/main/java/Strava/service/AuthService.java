package Strava.service;

import Strava.dao.UserRepository;
import Strava.entity.AuthProvider;
import Strava.entity.User;
import Strava.gateway.AuthGatewayFactory;
import Strava.gateway.IAuthGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService {

    // Simulating a user repository
    private final UserRepository userRepository;

    // Storage to keep the session of the users that are logged in
    private static Map<String, User> tokenStore = new HashMap<>();
    private static IAuthGateway authGateway;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Registration method that adds a new user to the repository
    // Includes the following basic information: email, name, birthdate;
    // and optionally: weight in kilograms, height in centimeters, maximum heart rate, heart rate at rest
    public boolean register(String email,
                            String name,
                            LocalDate birthdate,
                            String authProviderStr,
                            Double weight,
                            Double height,
                            Integer maxHeartRate,
                            Integer restingHeartRate) {

        AuthProvider authProvider = AuthProvider.valueOf(authProviderStr.toUpperCase());
        if (checkUserExists(email, authProvider)){
            User user = new User(authProvider, birthdate, email, height, maxHeartRate, name, restingHeartRate, weight);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }


    // Login method that checks if the user exists in the database and validates the password
    public Optional<String> login(String email, String password) {
        // Retrieve the user from the database
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && checkPassword(email, password , user.get().getAuthProvider())) {
            String token = generateToken();  // Generate a random token for the session
            tokenStore.put(token, user.get());     // Store the token and associate it with the user
            return Optional.of(token);
        } else {
            return Optional.empty();
        }
    }

    // Logout method to remove the token from the session store
    public Optional<Boolean> logout(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);

            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }

    // Method to check the password based on the authentication provider
    private boolean checkPassword(String email, String password, AuthProvider authProvider) {
        authGateway = AuthGatewayFactory.getInstance().createAuthGateway(authProvider);
        return authGateway.userAuth(email, password);
    }

    private boolean checkUserExists(String email, AuthProvider authProvider) {
        authGateway = AuthGatewayFactory.getInstance().createAuthGateway(authProvider);
        return authGateway.checkUserExists(email);
    }


    // Method to get the user based on the token
    public User getUserByToken(String token) {
        return tokenStore.get(token);
    }


    // Synchronized method to guarantee unique token generation
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}
