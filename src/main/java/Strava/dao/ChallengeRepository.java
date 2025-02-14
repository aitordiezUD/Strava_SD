package Strava.dao;

import Strava.entity.Challenge;
import Strava.entity.SportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate effectiveStartDate, LocalDate effectiveEndDate);
    List<Challenge> findByEndDateGreaterThanEqual(LocalDate date);
    List<Challenge> findByEndDateGreaterThanEqualAndSport(LocalDate date, SportType sport);
    List<Challenge> findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndSport(LocalDate effectiveStartDate, LocalDate effectiveEndDate, SportType sport);
}