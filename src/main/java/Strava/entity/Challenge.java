package Strava.entity;

import jakarta.persistence.*;
import org.hibernate.id.factory.spi.GenerationTypeStrategy;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Double targetDistance; // in kilometers (optional)

    @Column(nullable = false)
    private Integer targetTime; // in minutes (optional)

    @Enumerated(EnumType.STRING)
    private SportType sport;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) // Adjust cascade type if needed
    private User user;

    @ManyToMany(mappedBy = "participatingChallenges", fetch = FetchType.EAGER)
    private List<User> participants = new ArrayList<>();

    public Challenge() {
    }

    public Challenge(LocalDate endDate, String name, SportType sport, LocalDate startDate, Double targetDistance, Integer targetTime, User user) {
        this.endDate = endDate;
        this.name = name;
        this.sport = sport;
        this.startDate = startDate;
        this.targetDistance = targetDistance;
        this.targetTime = targetTime;
        this.user = user;
        this.participants = new ArrayList<User>();
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SportType getSport() {
        return sport;
    }

    public void setSport(SportType sport) {
        this.sport = sport;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Double getTargetDistance() {
        return targetDistance;
    }

    public void setTargetDistance(Double targetDistance) {
        this.targetDistance = targetDistance;
    }

    public Integer getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Integer targetTime) {
        this.targetTime = targetTime;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User user) {
        this.participants.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Challenge challenge = (Challenge) o;
        return Objects.equals(challengeId, challenge.challengeId) && Objects.equals(name, challenge.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(challengeId, name);
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "challengeId='" + challengeId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
