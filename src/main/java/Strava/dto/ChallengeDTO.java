package Strava.dto;
import java.time.LocalDate;

import Strava.entity.SportType;

public class ChallengeDTO {
    private String challengeId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double targetDistance;
    private Integer targetTime;
    private String sport;
    private String userEmail;

    public ChallengeDTO() {
    }

    public ChallengeDTO(String challengeId, String name, LocalDate startDate, LocalDate endDate, Double targetDistance, Integer targetTime, String sport, String userEmail) {
        this.challengeId = challengeId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetDistance = targetDistance;
        this.targetTime = targetTime;
        this.sport = sport;
        this.userEmail = userEmail;
    }

    // Getters y setters

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public String getSport() {
        return sport;
    }
    
    public SportType getSportType() {
    	return SportType.valueOf(sport.toUpperCase());
    }
    public void setSport(String sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return "ChallengeDTO{" +
                "challengeId='" + challengeId + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", targetDistance=" + targetDistance +
                ", targetTime=" + targetTime +
                ", sport='" + sport + '\'' +
                '}';
    }
}
