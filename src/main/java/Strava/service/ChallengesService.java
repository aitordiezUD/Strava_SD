package Strava.service;

import Strava.dao.ChallengeRepository;
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
    private final UserRepository userRepository;  // Añadir el UserRepository

    public ChallengesService(ChallengeRepository challengeRepository, UserRepository userRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
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
                activeChallenges = challengeRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndSport(startDate, endDate, sport);
            }else{
                activeChallenges = challengeRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(startDate, endDate);
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
        }

        if (!user.getParticipatingChallenges().contains(challenge)) {
            user.addChallengeParticipation(challenge);
        }

        challengeRepository.save(challenge);
        userRepository.save(user);
    }



    // Method to query accepted challenges that haven't finished yet
    public ArrayList<Challenge> queryAcceptedChallenges(User user) {
        // Logic to query accepted challenges
        List<Challenge> acceptedChallenges = new ArrayList<>(user.getChallenges());
        acceptedChallenges.removeIf(challenge -> challenge.getEndDate().isBefore(LocalDate.now()));
        return new ArrayList<>(acceptedChallenges);
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
