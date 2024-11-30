package Strava.dao;

import Strava.entity.Challenge;
import Strava.entity.SportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate effectiveStartDate, LocalDate effectiveEndDate);
    List<Challenge> findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndSport(LocalDate effectiveStartDate, LocalDate effectiveEndDate, SportType sport);

    List<Challenge> findBySport(SportType sport);

    List<Challenge> findByEndDateLessThanEqualAndAndSport(LocalDate endDate, SportType sport);
}
