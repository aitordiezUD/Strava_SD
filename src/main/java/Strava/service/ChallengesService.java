package Strava.service;

import Strava.dao.ChallengeRepository;
import Strava.dao.SessionRepository;
import Strava.dao.UserRepository;
import Strava.entity.Challenge;
import Strava.entity.Session;
import Strava.entity.SportType;
import Strava.entity.User;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChallengesService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public ChallengesService(ChallengeRepository challengeRepository, UserRepository userRepository, SessionRepository sessionRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    // Method to create a new challenge
    public Challenge createChallenge(String name, LocalDate startDate, LocalDate endDate,
                                     Double targetDistance, Integer targetTime,
                                     SportType sport, User creator) {
        Challenge challenge = new Challenge(endDate, name, sport, startDate, targetDistance, targetTime, creator);
        addChallenge(challenge);
        sendConfirmationEmail(creator, challenge); // Optional email notification
        return challenge;
    }

    //Method to get active challenges
    public List<Challenge> downloadActiveChallenges(LocalDate startDate, LocalDate endDate, SportType sport) {
        List<Challenge> activeChallenges = new ArrayList<>();
        if(startDate != null && endDate != null) {
            if (sport != null) {
                activeChallenges = challengeRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndSport(startDate, endDate, sport);
            }else{
                activeChallenges = challengeRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate);
            }
        }else{
            LocalDate today = LocalDate.now();
            if(sport != null) {
                activeChallenges = challengeRepository.findByEndDateGreaterThanEqualAndSport(today, sport);
            }else{
                activeChallenges = challengeRepository.findByEndDateGreaterThanEqual(today);
            }
        }

        return activeChallenges;

    }

    // Method to accept a challenge
    public void acceptChallenge(User user, Challenge challenge) {
        if (user == null || challenge == null) {
            return;
        }

        if (!challenge.getParticipants().contains(user)) {
            challenge.addParticipant(user);
//            challengeRepository.save(challenge);
        }

        if (!user.getParticipatingChallenges().contains(challenge)) {
            user.addChallengeParticipation(challenge);
            userRepository.save(user);
        }

    }



    // Method to query accepted challenges that haven't finished yet
    public List<Object> queryAcceptedChallenges(User user) {
        // Logic to query accepted challenges
        List<Object> result = new ArrayList<>();
        List<Challenge> acceptedChallenges = userRepository.findParticipatingChallengesByUserIdAndEndDate(user.getId(), LocalDate.now());
        List<Double> completionRates = new ArrayList<>();
        for (Challenge challenge : acceptedChallenges) {
            completionRates.add(calculateCompletionRate(challenge));
        }
        result.add(acceptedChallenges);
        result.add(completionRates);
        return result;
    }

    private double calculateCompletionRate(Challenge challenge) {
        User user = challenge.getUser();
        List<Session> sessions = sessionRepository.findByUserAndStartDateBetween(user, challenge.getStartDate(), challenge.getEndDate());
        double totalDistance = 0.0;
        double totalTime = 0.0;
        for (Session session : sessions) {
            totalDistance += session.getDistance();
            totalTime += session.getDuration();
        }
        double targetDistance = challenge.getTargetDistance();
        double targetTime = challenge.getTargetTime();
        double distanceRate = totalDistance / targetDistance;
        double timeRate = totalTime / targetTime;
        double completionRate = ((distanceRate + timeRate) / 2)*100;
        return  (completionRate > 100) ? 100 : completionRate;
    }



    // Method to add challenge to the repository
    public void addChallenge(Challenge challenge) {
        if (challenge != null) {

            challengeRepository.save(challenge);
        }
    }

    private void sendConfirmationEmail(User user, Challenge challenge) {
        // Logic to send email to user about the created challenge
    }
    
    //Method to find challenges by id 
    public Challenge findChallengeById(Long challengeId) {
    	return challengeRepository.findById(challengeId).orElse(null);
    }
}
