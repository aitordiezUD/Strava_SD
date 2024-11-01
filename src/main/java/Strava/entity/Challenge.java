package Strava.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

public class Challenge {
    private static long counter = 0;

    private String challengeId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double targetDistance; // in kilometers (optional)
    private Integer targetTime; // in minutes (optional)
    private SportType sport;
    private User user;

    public Challenge() {
    }

    public Challenge(LocalDate endDate, String name, SportType sport, LocalDate startDate, Double targetDistance, Integer targetTime, User user) {
        this.challengeId = generateID();
        this.endDate = endDate;
        this.name = name;
        this.sport = sport;
        this.startDate = startDate;
        this.targetDistance = targetDistance;
        this.targetTime = targetTime;
        this.user = user;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
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

    public static synchronized String generateID() {
        return Instant.now().toEpochMilli() + "-" + System.nanoTime() + "-" + (counter++);
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
