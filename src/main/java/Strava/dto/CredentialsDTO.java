package Strava.dto;

public class CredentialsDTO {
    private String email;
    private String password;
    private String provider;

    //Getters and Setters
    public String  getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public String getProvider(){return provider;}

    public void setProvider(String provider){this.provider = provider;}
}
