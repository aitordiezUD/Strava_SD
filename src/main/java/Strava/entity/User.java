package Strava.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.ArrayList;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private Double weight; // in kilograms

    @Column(nullable = false)
    private Double height; // in centimeters

    @Column(nullable = false)
    private Integer maxHeartRate; // beats per minute

    @Column(nullable = false)
    private Integer restingHeartRate; // beats per minute

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider; // Enum: GOOGLE, FACEBOOK

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_challenges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "challenge_id")
    )
    private List<Challenge> participatingChallenges = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Challenge> challenges = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();


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

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public void addChallenge(Challenge challenge) {this.challenges.add(challenge);}

    public void addChallengeParticipation(Challenge challenge) {
        if (!this.participatingChallenges.contains(challenge)) {
            this.participatingChallenges.add(challenge);
        }
    }

    public void removeChallenge(Challenge challenge) {
        challenges.remove(challenge);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Challenge> getParticipatingChallenges() {
        return participatingChallenges;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
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
