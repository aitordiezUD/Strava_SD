package Strava.facade;

import Strava.entity.Challenge;
import Strava.entity.SportType;
import Strava.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import Strava.service.AuthService;
import Strava.service.ChallengesService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/challenges")
public class ChallengesController{
	@Autowired
	private ChallengesService challengesService; 
	private AuthService authService;
	
	// CREATE CHALLENGE 
	
	@PostMapping
	public ResponseEntity<?> createChallenge(
			@RequestHeader("token") String token, 
			@RequestParam("name") String name, 
			@RequestParam("star_date") LocalDate startDate, 
			@RequestParam("end_date") LocalDate endDate, 
			@RequestParam(value= "target_distance", required = false) Double targetDistance, 
			@RequestParam( value = "target_time", required = false) Integer targetTime, 
			@RequestParam("sport") SportType sport){
		
			User creator = authService.getUserByToken(token);
			if(creator == null) {
				return ResponseEntity.status(403).body("Invalid Token");
			}
			
			challengesService.createChallenge(name, startDate, endDate, targetDistance, targetTime, sport, creator);	
			return ResponseEntity.ok("Challenge created succesfully");
		
	}
	
	// DOWNLOAD ACTIVE CHALLENGES 
	@GetMapping("/active")
	public ResponseEntity<?> downloadActiveChallenges(
			@RequestHeader("token") String token,
			@RequestParam(value = "date", required = false) Optional<LocalDate> date, 
			@RequestParam (value = "sport", required = false) Optional<SportType>sport){
			
			User user = authService.getUserByToken(token);
			if(user == null) {
				return ResponseEntity.status(403).body("Invalid Token");
			}
			LocalDate filterDate = date.orElse(null); 
			SportType filterSport = sport.orElse(null);
			
			List<Challenge> activeChallenges = challengesService.downloadActiveChallenges(filterDate, filterDate, filterSport);
			return ResponseEntity.ok(activeChallenges);
		
	}
	
	// ACEPT CHALLENGE 
	@PostMapping ("/accept")
	public ResponseEntity<?>acceptChallenge(
			@RequestHeader("token") String token, 
			@RequestParam("challenge_id") String challengeId){
		
		User user = authService.getUserByToken(token);
		if(user== null) {
			return ResponseEntity.status(403).body("Invalid Token");
		}
		
		Challenge challenge = challengesService.findChallengeById(challengeId);
		if(challenge == null) {
			return ResponseEntity.status(404).body("Chalenge not found");
		}
		
		challengesService.acceptChallenge(user, challenge);
		return ResponseEntity.ok("Challenge accepted succesfully");
	}
	
	// GET ACCEPTED CHALLENGES 
	@GetMapping("/accepted")
	public ResponseEntity<?>queryAcceptedChallenges(
			@RequestHeader("token") String token){
		
		User user = authService.getUserByToken(token); 
		if(user==null) {
			return ResponseEntity.status(403).body("Invalid Token");
		}
		
		List<Challenge> acceptedChallenges = challengesService.queryAcceptedChallenges(user);
		return ResponseEntity.ok(acceptedChallenges);
	}
		
	
}
	

