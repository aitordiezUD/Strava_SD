package Strava.service;

import Strava.entity.AuthProvider;
import Strava.entity.User;
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

    // Registration method that adds a new user to the repository
    // Includes the following basic information: email, name, birthdate;
    // and optionally: weight in kilograms, height in centimeters, maximum heart rate, heart rate at rest
    public void register(String email,
                         String name,
                         LocalDate birthdate,
                         String authProviderStr,
                         String password,
                         Double weight,
                         Double height,
                         Integer maxHeartRate,
                         Integer restingHeartRate) {

        AuthProvider authProvider = AuthProvider.valueOf(authProviderStr.toUpperCase());

        User user = new User(authProvider, birthdate, email, height, maxHeartRate, name, restingHeartRate, weight);

        if (!userRepository.containsKey(user.getEmail()) && checkPassword(email,password, authProvider)) {
            userRepository.putIfAbsent(user.getEmail(), user);
        }
    }

    // Login method that checks if the user exists in the database and validates the password
    public Optional<String> login(String email, String password) {
        User user = userRepository.get(email);

        if (user != null && checkPassword(email,password, user.getAuthProvider())) {
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
            return googleUsers.get(email).equals(password);
        } else if (authProvider == AuthProvider.FACEBOOK) {
            return facebookUsers.get(email).equals(password);
        } else {
            return false;
        }
    }

    // Method to get the user based on the token
    public User getUserByToken(String token) {
        return tokenStore.get(token);
    }

    // Method to get the user based on the email
    public User getUserByEmail(String email) {
        return userRepository.get(email);
    }

    // Synchronized method to guarantee unique token generation
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }
}
