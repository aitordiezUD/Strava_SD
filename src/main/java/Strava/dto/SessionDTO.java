package Strava.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessionDTO {
    private String sessionId;
    private String title;
    private String sport; // Representado como String para ser fácilmente manejable en JSON
    private double distance; // en kilómetros
    private LocalDate startDate;
    private LocalTime startTime;
    private int duration; // en minutos

    public SessionDTO() {
    }

    public SessionDTO(String sessionId, String title, String sport, double distance, LocalDate startDate, LocalTime startTime, int duration) {
        this.sessionId = sessionId;
        this.title = title;
        this.sport = sport;
        this.distance = distance;
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
    }

    // Getters y setters

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
                "sessionId='" + sessionId + '\'' +
                ", title='" + title + '\'' +
                ", sport='" + sport + '\'' +
                ", distance=" + distance +
                ", startDate=" + startDate +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
