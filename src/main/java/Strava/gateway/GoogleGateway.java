package Strava.gateway;

import Strava.dto.CredentialsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.*;
import java.util.Properties;

public class GoogleGateway implements IAuthGateway{

    private static final String CONFIG_FILE = "src/main/java/Strava/config.properties";
//    private static final String BASE_URL = "http://localhost:8081";
    private static String BASE_URL;

    static {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(input);
            BASE_URL = prop.getProperty("google.address");
        } catch (IOException ex) {
            System.err.println("# GoogleGateway - Error loading configuration: " + ex.getMessage());
            BASE_URL = "http://localhost:8081"; // Defaults
        }
    }

    public GoogleGateway() {
    }

    @Override
    public boolean checkUserExists(String email) {
        HttpClient client = HttpClient.newHttpClient();

        String RestMapping = "/users/checking";
        // In this case we use a GET request with the email as a parameter
        String uri = BASE_URL + RestMapping + "?email=" + email;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpResponse<Void> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("response.statusCode() = " + response.statusCode());
        return response.statusCode() == 200;
    }

    @Override
    public boolean userAuth(String email, String password) {
//        System.out.println("GoogleGateway.userAuth");
//        System.out.println("email = " + email);
//        System.out.println("password = " + password);

        HttpClient client = HttpClient.newHttpClient();
        String RestMapping = "/users/identification";
        String uri = BASE_URL + RestMapping;
        // In this case we use a POST request with the email and password in the body
        CredentialsDTO credentials = new CredentialsDTO(email, password);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(credentials);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<Void> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("response.statusCode() = " + response.statusCode());

        return response.statusCode() == 200;
    }
}
