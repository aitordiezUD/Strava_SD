package Strava.service;

import Strava.dao.SessionRepository;
import Strava.entity.Session;
import Strava.entity.SportType;
import Strava.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionsService {

    private final SessionRepository sessionRepository;

    public SessionsService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // Method to create a new session
    public void createSession(String title, String sport, double distance, LocalDate startDate,
                                 LocalTime startTime, int duration, User user) {
        SportType sportType = SportType.valueOf(sport.toUpperCase());
        Session session = new Session(distance, duration, sportType, startDate, startTime, title, user);
        this.addSession(session);
    }

//    Querying my training session: Each individual can view their completed training sessions. By default, only the
//    last 5 sessions will be displayed, but it is possible to retrieve and view all sessions within a specified
//    start and end date.

    // Method to query the last all sessions of a user
    public List<Session> queryAllSessions(User user) {
        return sessionRepository.findSessionByUser(user);
    }


    // Method to query the last 5 sessions of a user
    public List<Session> querySessions(User user, LocalDate startDate, LocalDate endDate) {
        List<Session> sessions = new ArrayList<>();
        if(startDate != null && endDate != null) {
            sessions = sessionRepository.findByUserAndStartDateBetween(user, startDate, endDate);
        }else{
            sessions = sessionRepository.findSessionByUser(user);
        }
        // Sort sessions by start date
        sessions.sort((s1,s2) ->  s2.getStartDate().compareTo(s1.getStartDate()));

        // Return the 5 first sessions
        if(sessions.size()<=5){return sessions;}else{return sessions.subList(0, 5);}
    }

    // Method to add a new session to the repository
    public void addSession(Session session) {
        if (session != null) {
            sessionRepository.save(session);
        }
    }

    // Method to get all sessions
    public List<Session> getSessions(){return sessionRepository.findAll();}


}
