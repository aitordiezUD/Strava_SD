package Strava;

import Strava.entity.*;
import Strava.service.AuthService;
import Strava.service.ChallengesService;
import Strava.service.SessionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;


@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initData(AuthService authService, ChallengesService challengesService, SessionsService sessionsService) {
        return args -> {
            logger.info("Initializing data...");

            // Create some users
            User user1 = new User(AuthProvider.GOOGLE, LocalDate.parse("2004-01-30"), "user1@opendeusto.es", 1.65, 200, "user01", 58, 57.5);
            User user2 = new User(AuthProvider.FACEBOOK, LocalDate.parse("2003-09-30"), "user2@opendeusto.es", 2.04, 200, "user02", 60, 85.9);
            User user3 = new User(AuthProvider.FACEBOOK, LocalDate.parse("2002-10-31"), "user3@opendeusto.es", 1.80, 210, "user03", 60, 70.0);
            User user4 = new User(AuthProvider.GOOGLE, LocalDate.parse("2001-07-31"), "user4@opendeusto.es", 1.75, 190, "user04", 55, 65.0);
            User user5 = new User(AuthProvider.FACEBOOK, LocalDate.parse("2006-01-31"), "user5@opendeusto.es", 1.90, 195, "user05", 62, 80.0);
            User user6 = new User(AuthProvider.GOOGLE, LocalDate.parse("1999-01-31"), "user6@opendeusto.es", 1.85, 185, "user06", 57, 75.0);
            User user7 = new User(AuthProvider.FACEBOOK, LocalDate.parse("2000-01-31"), "user7@opendeusto.es", 1.70, 200, "user07", 60, 68.0);
            User user8 = new User(AuthProvider.GOOGLE, LocalDate.parse("2007-01-31"), "user8@opendeusto.es", 1.95, 205, "user08", 65, 90.0);

            authService.addUserGoogle(user1.getEmail(), "user1");
            authService.register(user1.getEmail(), user1.getName(), user1.getBirthdate(), user1.getAuthProvider().toString(), "user1", user1.getWeight(), user1.getHeight(), user1.getMaxHeartRate(), user1.getRestingHeartRate());
            authService.addUserFacebook(user2.getEmail(), "user2");
            authService.register(user2.getEmail(), user2.getName(), user2.getBirthdate(), user2.getAuthProvider().toString(), "user2", user2.getWeight(), user2.getHeight(), user2.getMaxHeartRate(), user2.getRestingHeartRate());
            authService.addUserFacebook(user3.getEmail(), "user3");
            authService.register(user3.getEmail(), user3.getName(), user3.getBirthdate(), user3.getAuthProvider().toString(), "user3", user3.getWeight(), user3.getHeight(), user3.getMaxHeartRate(), user3.getRestingHeartRate());
            authService.addUserGoogle(user4.getEmail(), "user4");
            authService.register(user4.getEmail(), user4.getName(), user4.getBirthdate(), user4.getAuthProvider().toString(), "user4", user4.getWeight(), user4.getHeight(), user4.getMaxHeartRate(), user4.getRestingHeartRate());
            authService.addUserFacebook(user5.getEmail(), "user5");
            authService.register(user5.getEmail(), user5.getName(), user5.getBirthdate(), user5.getAuthProvider().toString(), "user5", user5.getWeight(), user5.getHeight(), user5.getMaxHeartRate(), user5.getRestingHeartRate());
            authService.addUserGoogle(user6.getEmail(), "user6");
            authService.register(user6.getEmail(), user6.getName(), user6.getBirthdate(), user6.getAuthProvider().toString(), "user6", user6.getWeight(), user6.getHeight(), user6.getMaxHeartRate(), user6.getRestingHeartRate());
            authService.addUserFacebook(user7.getEmail(), "user7");
            authService.register(user7.getEmail(), user7.getName(), user7.getBirthdate(), user7.getAuthProvider().toString(), "user7", user7.getWeight(), user7.getHeight(), user7.getMaxHeartRate(), user7.getRestingHeartRate());
            authService.addUserGoogle(user8.getEmail(), "user8");
            authService.register(user8.getEmail(), user8.getName(), user8.getBirthdate(), user8.getAuthProvider().toString(), "user8", user8.getWeight(), user8.getHeight(), user8.getMaxHeartRate(), user8.getRestingHeartRate());

            logger.info("Users saved!");


            // Create some challenges
            Challenge challenge1 = new Challenge(LocalDate.parse("2024-12-31"), "Marathon Training", SportType.RUNNING, LocalDate.parse("2024-01-01"), 42.195, 240, user1);
            Challenge challenge2 = new Challenge(LocalDate.parse("2024-06-30"), "Cycling Challenge", SportType.CYCLING, LocalDate.parse("2024-03-01"), 100.0, 300, user2);
            Challenge challenge3 = new Challenge(LocalDate.parse("2024-09-15"), "Swimming Sprint", SportType.RUNNING, LocalDate.parse("2024-07-01"), 1.5, 30, user3);
            Challenge challenge4 = new Challenge(LocalDate.parse("2024-11-01"), "Triathlon Prep", SportType.CYCLING, LocalDate.parse("2024-05-01"), 51.5, 180, user4);
            Challenge challenge5 = new Challenge(LocalDate.parse("2024-08-20"), "Hiking Adventure", SportType.RUNNING, LocalDate.parse("2024-06-01"), 20.0, 240, user5);

            challengesService.addChallenge(challenge1);
            challengesService.addChallenge(challenge2);
            challengesService.addChallenge(challenge3);
            challengesService.addChallenge(challenge4);
            challengesService.addChallenge(challenge5);

            logger.info("Challenges saved!");


            // Create some sessions
            Session session1 = new Session(10.0, 60, SportType.RUNNING, LocalDate.parse("2024-10-31"), LocalTime.parse("06:00"), "Morning Run", user1);
            Session session2 = new Session(25.0, 90, SportType.CYCLING, LocalDate.parse("2024-07-04"), LocalTime.parse("07:30"), "Cycling Session", user2);
            Session session3 = new Session(5.0, 30, SportType.RUNNING, LocalDate.parse("2021-10-31"), LocalTime.parse("18:00"), "Evening Jog", user3);
            Session session4 = new Session(15.0, 75, SportType.CYCLING, LocalDate.parse("2024-10-29"), LocalTime.parse("05:45"), "Early Ride", user4);
            Session session5 = new Session(8.0, 45, SportType.RUNNING, LocalDate.parse("2023-10-15"), LocalTime.parse("19:00"), "Night Run", user5);
            Session session6 = new Session(12.0, 70, SportType.RUNNING, LocalDate.parse("2024-08-01"), LocalTime.parse("06:30"), "Morning Run", user6);
            Session session7 = new Session(30.0, 120, SportType.CYCLING, LocalDate.parse("2024-08-02"), LocalTime.parse("08:00"), "Long Ride", user7);
            Session session8 = new Session(7.0, 40, SportType.RUNNING, LocalDate.parse("2024-08-03"), LocalTime.parse("19:30"), "Evening Run", user8);

            sessionsService.addSession(session1);
            sessionsService.addSession(session2);
            sessionsService.addSession(session3);
            sessionsService.addSession(session4);
            sessionsService.addSession(session5);
            sessionsService.addSession(session6);
            sessionsService.addSession(session7);
            sessionsService.addSession(session8);

            logger.info("Sessions saved!");
        };
    }
}
