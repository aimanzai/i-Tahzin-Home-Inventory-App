/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoginUser extends UserInfo{
   
    public LoginUser(String username, String password, String age, String email, String profilePicture){
        super(username, password, age, email, profilePicture);
    }

    @Override
    public void userAction(String username, String password, String age, String email, String profilePicture){}
    
    @Override
    public boolean userLogin(String username, String password) {
        try {
            File file = new File("LoginInfo.txt");

            if (!file.exists()) {
                return false;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(username) && userData[1].equals(password)) {
                    return true;
                }
            }

            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
