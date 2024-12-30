package Strava.dao;

import Strava.entity.Challenge;
import Strava.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT c FROM User u JOIN u.participatingChallenges c WHERE u.id = :userId AND c.endDate >= :date")
    List<Challenge> findParticipatingChallengesByUserIdAndEndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

}
