package Strava.dto;

import java.time.LocalDate;

public class AcceptedChallengeDTO {
    private Long challengeId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double targetDistance;
    private Integer targetTime;
    private String sport;
    private String userEmail;
    private double completionRate;

    public AcceptedChallengeDTO() {
    }

    public AcceptedChallengeDTO(Long challengeId, String name, LocalDate startDate, LocalDate endDate,
                                Double targetDistance, Integer targetTime, String sport, String userEmail,
                                Double completionRate) {
        this.challengeId = challengeId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetDistance = targetDistance;
        this.targetTime = targetTime;
        this.sport = sport;
        this.userEmail = userEmail;
        this.completionRate = completionRate;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
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

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}