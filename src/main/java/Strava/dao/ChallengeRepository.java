package Strava.dao;

import Strava.entity.Challenge;
import Strava.entity.SportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndSport(LocalDate today, LocalDate todayAgain, SportType sportType);
}
