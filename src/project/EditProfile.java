package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EditProfile extends UserInfo{

    public EditProfile(String username, String password, String age, String email, String profilePicture) {
        super(username, password, age, email, profilePicture);
    }

    @Override
    public void userAction(String newUsername, String newPassword, String newAge, String newEmail, String newProfilePicture) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("LoginInfo.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("LoginInfo_temp.txt"))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(getUsername())) {
                    // Update the user data
                    writer.write(newUsername + "," + newPassword + "," + newAge + "," + newEmail + "," + newProfilePicture);
                } else {
                    // If the line does not correspond to the specified username, write it as is
                    writer.write(line);
                }
            writer.newLine(); // Add a newline character after each line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original file after updating
        File originalFile = new File("LoginInfo.txt");
        File tempFile = new File("LoginInfo_temp.txt");

        // Attempt to delete the original file
        if (!originalFile.delete()) {
            System.out.println("Failed to delete the original file.");
        }

        // Rename the temporary file to the original file after successful deletion
        if (!tempFile.renameTo(originalFile)) {
            System.out.println("Failed to rename the temporary file.");
        }

        // Update the current object's information
        setUsername(newUsername);
        setPass(newPassword);
        setAge(newAge);
        setEmail(newEmail);
        setProfilePic(newProfilePicture);
        ErrorMessage em = new ErrorMessage();
         em.editProfileMessage();
    }

    @Override
    public boolean userLogin(String username, String password) {return true;}
}
