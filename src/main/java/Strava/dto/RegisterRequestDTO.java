package Strava.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class RegisterRequestDTO {

    private String name;
    private String email;
    private LocalDate birthday;
    private String authProviderStr;
    private double weight;
    private double height;
    private Integer maxHeartRate;
    private Integer restingHeartRate;

    public RegisterRequestDTO(String name, String email, LocalDate birthday, String authProviderStr, double weight, double height, Integer maxHeartRate, Integer restingHeartRate){
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.authProviderStr = authProviderStr;
        this.weight = weight;
        this.height = height;
        this.maxHeartRate = maxHeartRate;
        this.restingHeartRate = restingHeartRate;
    }

    //Getters and setters
    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public LocalDate getBirthday(){return birthday;}
    public void setBirthday(LocalDate birthday){this.birthday = birthday;}

    public String getAuthProviderStr(){return authProviderStr;}
    public void setAuthProviderStr(String authProviderStr){this.authProviderStr = authProviderStr;}

    public double getWeight(){return weight;}
    public void setWeight(double weight){this.weight = weight;}

    public double getHeight(){return height;}
    public void setHeight(double height){this.height = height;}

    public Integer getMaxHeartRate(){return maxHeartRate;}
    public void setMaxHeartRate(Integer maxHeartRate){this.maxHeartRate = maxHeartRate;}

    public Integer getRestingHeartRate(){return restingHeartRate;}
    public void setRestingHeartRate(Integer restingHeartRate){this.restingHeartRate = restingHeartRate;}


}
