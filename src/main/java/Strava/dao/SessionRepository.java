package Strava.dao;

import Strava.entity.Session;
import Strava.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, String> {
    List<Session> findSessionByUser(User user);
    List<Session> findByUserAndStartDateBetween(User user, LocalDate startDate, LocalDate endDate);
}

