package Strava.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Map;


public class User {
    private String email;
    private String name;
    private LocalDate birthdate;
    private Double weight; // in kilograms
    private Double height; // in centimeters
    private Integer maxHeartRate; // beats per minute
    private Integer restingHeartRate; // beats per minute
    private AuthProvider authProvider; // Enum: GOOGLE, FACEBOOK
    private Map<Challenge,Boolean> challenges = new HashMap<>();

    public User() {
    }

    public User(AuthProvider authProvider, LocalDate birthdate, String email, Double height, Integer maxHeartRate, String name, Integer restingHeartRate, Double weight) {
        this.authProvider = authProvider;
        this.birthdate = birthdate;
        this.email = email;
        this.height = height;
        this.maxHeartRate = maxHeartRate;
        this.name = name;
        this.restingHeartRate = restingHeartRate;
        this.weight = weight;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getMaxHeartRate() {
        return maxHeartRate;
    }

    public void setMaxHeartRate(Integer maxHeartRate) {
        this.maxHeartRate = maxHeartRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRestingHeartRate() {
        return restingHeartRate;
    }

    public void setRestingHeartRate(Integer restingHeartRate) {
        this.restingHeartRate = restingHeartRate;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Map<Challenge, Boolean> getChallenges() {
        return challenges;
    }

    public void setChallenges(Map<Challenge, Boolean> challenges) {
        this.challenges = challenges;
    }

    public void addChallenge(Challenge challenge, Boolean achieved) {
        challenges.put(challenge,achieved);
    }

    public void removeChallenge(Challenge challenge) {
        challenges.remove(challenge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}
