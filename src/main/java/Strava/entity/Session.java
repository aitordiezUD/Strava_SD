package Strava.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Session {
    private String sessionId;
    private String title;
    private SportType sport; // Enum: CYCLING, RUNNING
    private double distance; // in kilometers
    private LocalDate startDate;
    private LocalTime startTime;
    private int duration; // in minutes

    public Session() {
    }

    public Session(double distance, int duration, String sessionId, SportType sport, LocalDate startDate, LocalTime startTime, String title) {
        this.distance = distance;
        this.duration = duration;
        this.sessionId = sessionId;
        this.sport = sport;
        this.startDate = startDate;
        this.startTime = startTime;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sessionId);
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId='" + sessionId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
