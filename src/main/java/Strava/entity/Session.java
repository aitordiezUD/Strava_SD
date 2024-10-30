package Strava.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Session {
    private String title;
    private SportType sport; // Enum: CYCLING, RUNNING
    private double distance; // in kilometers
    private LocalDate startDate;
    private LocalTime startTime;
    private int duration; // in minutes
    private User user;

    public Session() {
    }

    public Session(double distance, int duration, SportType sport, LocalDate startDate, LocalTime startTime, String title, User user) {
        this.distance = distance;
        this.duration = duration;
        this.sport = sport;
        this.startDate = startDate;
        this.startTime = startTime;
        this.title = title;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public SportType getSport() {
        return sport;
    }

    public void setSport(SportType sport) {
        this.sport = sport;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(startTime, session.startTime) && Objects.equals(user, session.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, user);
    }


}
