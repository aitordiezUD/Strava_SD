package Strava.facade;

import Strava.dto.ChallengeDTO;
import Strava.dto.SessionDTO;
import Strava.entity.Challenge;
import Strava.entity.SportType;
import Strava.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import Strava.service.AuthService;
import Strava.service.ChallengesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/challenges")
public class ChallengesController{

	private final ChallengesService challengesService;
	private final AuthService authService;

	public ChallengesController(ChallengesService challengesService, AuthService authService) {
		this.challengesService = challengesService;
		this.authService = authService;
	}
	
	// CREATE CHALLENGE 
	
	@Operation(
			summary = "Create a new challenge", 
			description = "Creates a new challenge for the authenticated user", 
			responses = {
					@ApiResponse(responseCode = "201", description = "Challenge created succesfully"), 
					@ApiResponse(responseCode = "400", description = "Bad Request: Invalid data"),
					@ApiResponse(responseCode= "401", description = "Invalid Token"),
					@ApiResponse(responseCode = "500", description = "Internal server error")
			}
		)
	
	@PostMapping
	public ResponseEntity<Void> createChallenge(
			@Parameter(name = "challenge", description = "challenge data", required = true)
			@RequestBody ChallengeDTO challengeDTO,
			@Parameter(name = "token", description = "Authorization token", required = true, example = "1727786726773")
			@RequestHeader("token") String token)
	{
		
			User creator = authService.getUserByToken(token);
			if(creator == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

			challengesService.createChallenge(challengeDTO.getName(), challengeDTO.getStartDate(), challengeDTO.getEndDate(), challengeDTO.getTargetDistance(),
					challengeDTO.getTargetTime(), challengeDTO.getSportType(), creator);
			return new ResponseEntity<>(HttpStatus.CREATED);
	}
	

	
	// DOWNLOAD ACTIVE CHALLENGES 
	
	 @Operation(
	            summary = "Get active challenges",
	            description = "Returns a list of active challenges filtered by date and sport type",
	            responses = {
	                    @ApiResponse(responseCode = "200", description = "Active challenges retrieved successfully"),
	                    @ApiResponse(responseCode = "401", description = "Invalid Token"),
	                    @ApiResponse(responseCode = "500", description = "Internal server error")
	            }
	    )
	   
	@GetMapping("/active")
	 public ResponseEntity<List<ChallengeDTO>> downloadActiveChallenges(
		        @Parameter(name = "startDate", description = "Start date to filter the challenges", example = "2021-01-01")
		        @RequestParam(name = "startDate", required = false) LocalDate startDate,
		        @Parameter(name = "endDate", description = "End date to filter the challenges", example = "2021-12-31")
		        @RequestParam(name = "endDate", required = false) LocalDate endDate,
		        @Parameter(name = "sportType", description = "Type of sport to filter the challenges", example = "RUNNING")
		        @RequestParam(name = "sportType", required = false) String sport,
				@Parameter(name = "token", description = "Authorization token", required = true, example = "1727786726773")
				@RequestBody String token) {

		    try {
		        User user = authService.getUserByToken(token);
		        if (user == null) {
		            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		        }
				SportType sport_Type = SportType.valueOf(sport.toUpperCase());
				List<Challenge> activeChallenges = challengesService.downloadActiveChallenges(startDate, endDate, sport_Type);
		        if (activeChallenges.isEmpty()) {
		            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		        }

		        List<ChallengeDTO> challengeDTOs = new ArrayList<>();
		        for (Challenge challenge : activeChallenges) {
		            challengeDTOs.add(new ChallengeDTO(challenge.getChallengeId(), challenge.getName(), challenge.getStartDate(), challenge.getEndDate(), challenge.getTargetDistance(), challenge.getTargetTime(), challenge.getSport().toString(), challenge.getUser().getEmail()));
				}

		        return new ResponseEntity<>(challengeDTOs, HttpStatus.OK);

		    } catch (Exception e) {
		        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
	 
	
	
	
	// ACCEPT CHALLENGE
	@Operation(
	        
			summary = "Accept a challenge",
	        description = "Accepts a specific challenge for the authenticated user",
            responses = {
	                @ApiResponse(responseCode = "200", description = "Challenge accepted successfully"),
	                @ApiResponse(responseCode = "401", description = "Invalid Token"),
	                @ApiResponse(responseCode = "404", description = "Challenge not found"),
	                @ApiResponse(responseCode = "500", description = "Internal server error")
	         }
	  )   
	
	@PostMapping ("/accept")
	public ResponseEntity<?>acceptChallenge(
			@Parameter(description = "Authorization token", required = true, example = "172778798774")
			@RequestBody String token,
			@Parameter(description = "Challenge ID", required = true, example = "123456")
            @RequestParam String challengeId){
		
		User user = authService.getUserByToken(token);
		if(user== null) {
			return ResponseEntity.status(403).body("Invalid Token");
		}
		
		Challenge challenge = challengesService.findChallengeById(challengeId);
		if(challenge == null) {
			return ResponseEntity.status(404).body("Challenge not found");
		}
		
		challengesService.acceptChallenge(user, challenge);
		return ResponseEntity.ok("Challenge accepted succesfully");
	}
	
	// GET ACCEPTED CHALLENGES 
	 @Operation(
	            summary = "Get accepted challenges",
	            description = "Retrieves a list of challenges accepted by the authenticated user",
	            responses = {
	                    @ApiResponse(responseCode = "200", description = "Accepted challenges retrieved successfully"),
	                    @ApiResponse(responseCode = "401", description = "Invalid Token"),
	                    @ApiResponse(responseCode = "204", description = "No content: No accepted challenges found"),
	                    @ApiResponse(responseCode = "500", description = "Internal server error")
	            }
	    )
	 
	@GetMapping("/accepted")
	public ResponseEntity<List<ChallengeDTO>>queryAcceptedChallenges(
	 		@Parameter(description= "Authorization token", required = true, example = "172778789774")
			@RequestBody String token){
		try {
			User user = authService.getUserByToken(token); 
			if(user==null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			List<Challenge> acceptedChallenges = challengesService.queryAcceptedChallenges(user);
			if(acceptedChallenges.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
			}
			List<ChallengeDTO> challengeDtos = new ArrayList<>();
			for(Challenge challenge : acceptedChallenges) {
				challengeDtos.add(new ChallengeDTO(challenge.getChallengeId(),challenge.getName(), challenge.getStartDate(), challenge.getEndDate(), challenge.getTargetDistance(), challenge.getTargetTime(), challenge.getSport().toString(), challenge.getUser().getEmail()
				));
			}
			return new ResponseEntity<>(challengeDtos, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}


		
	
}
	

