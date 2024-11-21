package Strava.gateway;

public class GoogleGateway implements IAuthGateway{

    public GoogleGateway() {
    }

    @Override
    public boolean checkUserExists(String email) {
        return false;
    }

    @Override
    public boolean userAuth(String email, String password) {
        return false;
    }
}
