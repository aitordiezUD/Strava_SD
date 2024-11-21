package Strava.gateway;

import Strava.entity.AuthProvider;

public class AuthGatewayFactory {
    // Private static instance for Singleton
    private static AuthGatewayFactory instance;

    // Private constructor to prevent instantiation
    private AuthGatewayFactory() {}

    // Public method to get the single instance (Lazy Initialization)
    public static AuthGatewayFactory getInstance() {
        if (instance == null) {
            synchronized (AuthGatewayFactory.class) {
                if (instance == null) {
                    instance = new AuthGatewayFactory();
                }
            }
        }
        return instance;
    }

    // Method to create the AuthGateway
    public IAuthGateway createAuthGateway(AuthProvider provider) {
        return switch (provider) {
            case GOOGLE -> new GoogleGateway();
            case FACEBOOK -> new FacebookGateway();
        };
    }

}
