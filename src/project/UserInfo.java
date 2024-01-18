package project;

import java.util.ArrayList;

public abstract class UserInfo {
    private String username;
    private String password;
    private String age;
    private String email;
    private String profilePicture;

    public UserInfo(String username, String password, String age, String email, String profilePicture){
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.profilePicture = profilePicture;
    }
    
    public String getUsername(){return this.username;}
    public String getPass(){return this.password;}
    public String getAge(){return this.age;}
    public String getEmail(){return this.email;}
    public String getProfilePic(){return this.profilePicture;}
    
    public void setUsername(String username){this.username = username;}
    public void setPass(String password){this.password = password;}
    public void setAge(String age){this.age = age;}
    public void setEmail(String email){this.email = email;}
    public void setProfilePic(String profilePicture){this.profilePicture = profilePicture;}

    public abstract void userAction(String username, String password, String age, String email, String profilePicture);
    public abstract boolean userLogin(String username, String password);
}
