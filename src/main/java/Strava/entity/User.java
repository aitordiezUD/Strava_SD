package Strava.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String email;
    private String name;
    private LocalDate birthdate;
    private Double weight; // in kilograms
    private Double height; // in centimeters
    private Integer maxHeartRate; // beats per minute
    private Integer restingHeartRate; // beats per minute
    private AuthProvider authProvider; // Enum: GOOGLE, FACEBOOK
    private List<Session> sessions = new ArrayList<>();
    private List<Challenge> challenges = new ArrayList<>();

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
