package Strava.service;

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
    private static Map<String, User> userRepository = new HashMap<>();

    // Storage to keep the session of the users that are logged in
    private static Map<String, User> tokenStore = new HashMap<>();

    // Simulate the users registered in Google
    private static Map<String,String> googleUsers = new HashMap<>();

    // Simulate the users registered in Facebook
    private static Map<String,String> facebookUsers = new HashMap<>();

    private static final IAuthGateway facebookGateway = AuthGatewayFactory.getInstance().createAuthGateway(AuthProvider.FACEBOOK);


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

        User user = new User(authProvider, birthdate, email, height, maxHeartRate, name, restingHeartRate, weight);

        if (!userRepository.containsKey(user.getEmail()) && checkUserExists(email, authProvider)) {
            userRepository.putIfAbsent(user.getEmail(), user);
            return true;
        }
        return false;
    }

    // Login method that checks if the user exists in the database and validates the password
    public Optional<String> login(String email, String password) {
        User user = userRepository.get(email);
        if (user != null && checkPassword(email, password , user.getAuthProvider())) {
            String token = generateToken();  // Generate a random token for the session
            tokenStore.put(token, user);     // Store the token and associate it with the user
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

    // Method to add a new Google user
    public void addUserGoogle(String email, String password) {
        googleUsers.put(email,password);
    }

    // Method to add a new Facebook user
    public void addUserFacebook(String email, String password) {
        facebookUsers.put(email,password);
    }

    // Method to check the password based on the authentication provider
    private boolean checkPassword(String email, String password, AuthProvider authProvider) {
        if (authProvider == AuthProvider.GOOGLE) {
            return checkPasswordGoogle(email, password);
        } else if (authProvider == AuthProvider.FACEBOOK) {
            return checkPasswordFacebook(email, password);
        } else {
            return false;
        }
    }

    private boolean checkPasswordGoogle(String email, String password) {
        return googleUsers.get(email).equals(password);
    }

    private boolean checkPasswordFacebook(String email, String password) {
        return facebookGateway.userAuth(email, password);
    }

    private boolean checkUserExists(String email, AuthProvider authProvider) {
        if (authProvider == AuthProvider.GOOGLE) {
            return checkUserExistsGoogle(email);
        } else if (authProvider == AuthProvider.FACEBOOK) {
            return checkUserExistsFacebook(email);
        } else {
            return false;
        }
    }

    private boolean checkUserExistsGoogle(String email) {
        return googleUsers.containsKey(email);
    }

    private boolean checkUserExistsFacebook(String email) {
        return facebookGateway.checkUserExists(email);
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
