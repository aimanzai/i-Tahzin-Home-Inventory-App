package project;

import java.io.File;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class ErrorMessage {
    private Alert msg;
    
    public ErrorMessage(){msg = new Alert(Alert.AlertType.NONE);}
    
    public void registerAccount(){
        msg.setAlertType(Alert.AlertType.INFORMATION);
        msg.setTitle("Register Account!");
        msg.setContentText("Your account has been registered!");
        msg.show();
    }
    
    public void faileToLogin(){
        msg.setAlertType(Alert.AlertType.ERROR);
        msg.setTitle("Error Message!");
        msg.setContentText("Invalid username or password. Please try again!");
        msg.show();
    }
    
    public void editProfileMessage() {
        msg.setAlertType(Alert.AlertType.CONFIRMATION);
        msg.setTitle("Edit Profile");
        msg.setContentText("You need to logout to see the changes in your profile");
        Optional<ButtonType> result = msg.showAndWait();
        
        if(result.get() == ButtonType.OK)
            System.exit(0);
    }
    
    public void showErrorMessage(String message){
        msg.setAlertType(Alert.AlertType.ERROR);
        msg.setTitle("Invalid Entry!");
        msg.setContentText(message);
        msg.show();
    }
    
    public void confirmExit(){
        msg.setAlertType(Alert.AlertType.CONFIRMATION);
        msg.setTitle("Confirmation Dialog");
        msg.setContentText("Are you sure to exit?");
        Optional<ButtonType> result = msg.showAndWait();
        
        if(result.get() == ButtonType.OK)
            System.exit(0);
    }
    
    public void deleteMessage(){
        msg.setAlertType(Alert.AlertType.INFORMATION);
        msg.setTitle("Delete Message!");
        msg.setContentText("Item has been successfully deleted!");
        msg.show();
    }
    
    public void saveItemMessage(TextField itemTF, TextField locationTF, TextField priceTF, TextField storeTF, TextField noteTF, TextField photoTF){
        msg.setAlertType(Alert.AlertType.INFORMATION);
        msg.setTitle("Confirmation Dialog");
        msg.setContentText("Item has been successfully added!");
        msg.show();
        itemTF.clear();
        locationTF.clear();
        priceTF.clear();
        storeTF.clear();
        noteTF.clear();
        photoTF.clear();
    }
    
    public void editItemMessage(){
        msg.setAlertType(Alert.AlertType.INFORMATION);
        msg.setTitle("Confirmation Dialog");
        msg.setContentText("Item has been successfully edited!");
        msg.show();
    }
    
    public void faileToEditItem(){
        msg.setAlertType(Alert.AlertType.ERROR);
        msg.setTitle("Failed to Edit Item!");
        msg.setContentText("Error renaming files. Check permissions or file usage!");
        msg.show();
    }
    
    public void notFoundItem(){
        msg.setAlertType(Alert.AlertType.ERROR);
        msg.setTitle("Not Found Item!");
        msg.setContentText("Please search a correct item!");
        msg.show();
    }
    
    public boolean isPresent(TextField tf, String fieldName){
        if(tf.getText().length() == 0){
            showErrorMessage(fieldName + " is a required field");
            return false;
        }
        return true;
    }
    
    public boolean isInteger(TextField tf, String fieldName){
        try{
            int i = Integer.parseInt(tf.getText());
            return true;
        }catch(NumberFormatException nfe){
            showErrorMessage(fieldName + " must be an integer!");
            return false;
        }
    }
    
    public boolean isDouble(TextField tf, String fieldName){
        try{
            double d = Double.parseDouble(tf.getText());
            return true;
        }catch(NumberFormatException nfe){
            showErrorMessage(fieldName + " must be a double!");
            return false;
        }
    }
}
