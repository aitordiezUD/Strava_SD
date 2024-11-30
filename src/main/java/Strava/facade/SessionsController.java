package Strava.facade;
import Strava.entity.Session;
import Strava.entity.User;
import Strava.service.AuthService;
import Strava.service.SessionsService;
import Strava.dto.SessionDTO;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/sessions")
public class SessionsController {

    private static final Logger log = LoggerFactory.getLogger(SessionsController.class);
    private final SessionsService sessionsService;
    private final AuthService authService;

    public SessionsController(SessionsService sessionsService, AuthService authService) {
        this.sessionsService = sessionsService;
        this.authService = authService;
    }

    @Operation(
        summary = "Get all sessions",
        description = "Returns a list of the last 5 completed sessions, or all completed sessions if retrieved by date",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK: List of sessions retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No Content: No sessions found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )

    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAllSessions(
        @Parameter(name = "startDate", description = "Start date to filter the sessions", example = "2021-01-01")
        @RequestParam(name = "startDate", required = false) LocalDate startDate,
        @Parameter(name = "endDate", description = "End date to filter the sessions", example = "2021-12-31")
        @RequestParam(name = "endDate",required = false) LocalDate endDate,
        @Parameter(name = "token", description = "Authorization token", required = true, example = "1727786726773")
        @RequestBody String token) {
        try {
            User user = authService.getUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            List<Session> sessions = sessionsService.querySessions(user, startDate, endDate);
            if (sessions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<SessionDTO> sessionDTOs = new ArrayList<>();
            for (Session session : sessions) {
                sessionDTOs.add(sessionToDTO(session));
            }
            return new ResponseEntity<>(sessionDTOs, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "Create a new session",
        description = "Allows a user to create a new session",
        responses = {
            @ApiResponse(responseCode = "201", description = "Created: Session created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )

    @PostMapping
    public  ResponseEntity<Void> createSession(
        @Parameter(name = "session", description = "Session data", required = true)
        @RequestBody SessionDTO sessionDTO,
        @Parameter(name = "token", description = "Authorization token", required = true, example = "1727786726773")
        @RequestHeader String token) {
        try {
            User user = authService.getUserByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            sessionsService.createSession(sessionDTO.getTitle(), sessionDTO.getSport(), sessionDTO.getDistance(),
                    sessionDTO.getStartDate(), sessionDTO.getStartTime(), sessionDTO.getDuration(), user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SessionDTO sessionToDTO(Session session) {
        return new SessionDTO(session.getTitle(), session.getSport().toString(), session.getDistance(),
                session.getStartDate(), session.getStartTime(), session.getDuration(), session.getUser().getEmail());
    }

}
