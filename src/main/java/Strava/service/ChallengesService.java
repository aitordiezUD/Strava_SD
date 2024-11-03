package Strava.service;

import Strava.entity.Challenge;
import Strava.entity.Session;
import Strava.entity.SportType;
import Strava.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChallengesService {

    //Simulating challenge repository
    private static List<Challenge> challengeRepository = new ArrayList<>();
    private final SessionsService sessionsService;

    public ChallengesService(SessionsService sessionsService) {
        this.sessionsService = sessionsService;
    }

    // Method to create a new challenge
    public void createChallenge(String name, LocalDate startDate, LocalDate endDate,
                                     Double targetDistance, Integer targetTime,
                                     SportType sport, User creator) {
        Challenge challenge = new Challenge(endDate, name, sport, startDate, targetDistance, targetTime, creator);
        addChallenge(challenge);
        sendConfirmationEmail(creator, challenge); // Optional email notification
    }

    //Method to get active challenges
    public List<Challenge> downloadActiveChallenges(LocalDate startDate, LocalDate endDate, SportType sport) {
        List<Challenge> activeChallenges = new ArrayList<>(challengeRepository);
        if (startDate != null && endDate != null) { // Filter by start and end date
            activeChallenges.removeIf(challenge -> challenge.getEndDate().isBefore(startDate) || challenge.getStartDate().isAfter(endDate));
        } else{
            LocalDate today = LocalDate.now(); // Get the current date
            activeChallenges.removeIf(challenge -> challenge.getEndDate().isBefore(today) || challenge.getStartDate().isAfter(today));
            activeChallenges.sort((c1, c2) -> c2.getEndDate().compareTo(c1.getEndDate()));
            return activeChallenges.subList(0,5);
        }
        if (sport != null) {
            activeChallenges.removeIf(challenge -> challenge.getSport() != sport);
        }
        return activeChallenges;

    }

    // Method to accept a challenge
    public void acceptChallenge(User user, Challenge challenge) {
        if (user == null || challenge == null) {
            return;
        }
        boolean status = checkChallengeCompletion(user, challenge);
        user.addChallenge(challenge, status);
    }

    // Method to check if the challenge is completed
    public boolean checkChallengeCompletion(User user, Challenge challenge) {
        LocalDate startDate = challenge.getStartDate();
        LocalDate endDate = challenge.getEndDate();
        List<Session> sessions = sessionsService.queryAllSessions(user);
        for (Session session : sessions) {
            if (session.getStartDate().isAfter(startDate) && session.getStartDate().isBefore(endDate)) {
                if (challenge.getTargetDistance() != null && session.getDistance() >= challenge.getTargetDistance()
                && challenge.getTargetTime() != null && session.getDuration() <= challenge.getTargetTime()) {
                    return true;
                }
            }
        }

        return false;
    }

    // Method to query accepted challenges that haven't finished yet
    public ArrayList<Challenge> queryAcceptedChallenges(User user) {
        // Logic to query accepted challenges
        List<Challenge> acceptedChallenges = new ArrayList<>(user.getChallenges().keySet());
        acceptedChallenges.removeIf(challenge -> challenge.getEndDate().isBefore(LocalDate.now()));
        return new ArrayList<>(acceptedChallenges);
    }


    // Method to add challenge to the repository
    public void addChallenge(Challenge challenge) {
        if (challenge != null) {
            challengeRepository.add(challenge);
        }
    }

    private void sendConfirmationEmail(User user, Challenge challenge) {
        // Logic to send email to user about the created challenge
    }
    
    //Method to find challenges by id 
    public Challenge findChallengeById(String challengeId) {
    	return challengeRepository.stream()
    			.filter(challenge -> challenge.getChallengeId().equals(challengeId))
    			.findFirst()
    			.orElse(null);
    }
}
