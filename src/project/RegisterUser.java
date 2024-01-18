/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class RegisterUser extends UserInfo {
    
    public RegisterUser(String username, String password, String age, String email, String profilePicture){
        super(username, password, age, email, profilePicture);
    }
    
    @Override
    public void userAction(String username, String password, String age, String email, String profilePic) {
        try {
            File file = new File("LoginInfo.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(username + "," + password + "," + age + "," + email + "," + profilePic);
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();
            
            ErrorMessage em = new ErrorMessage();
            em.registerAccount();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }  

    @Override
    public boolean userLogin(String username, String password) {return true;}
}
