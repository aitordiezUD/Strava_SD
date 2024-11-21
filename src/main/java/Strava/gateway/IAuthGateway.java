package Strava.gateway;

public interface IAuthGateway {
    public boolean checkUserExists(String email);
    public boolean userAuth(String email, String password);
}
