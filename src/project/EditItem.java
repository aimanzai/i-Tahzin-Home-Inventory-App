package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EditItem extends HomeInventory{
    
    public EditItem(String username, String item, String location, String price, String date, String website, String note, String photo) {
        super(username, item, location, price, date, website, note, photo);
    }
    
    /*public void editItem(String filename, ArrayList<HomeInventory> inventoryList, String username, 
        String newItem, String newLocation, String newPrice, String newDate, 
        String newWebsite, String newNote, String newPhoto) {
        
        ErrorMessage msg = new ErrorMessage();
        File tempFile = new File("C:\\Users\\USER\\Documents\\NetBeansProjects\\Project\\temp.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (HomeInventory item : inventoryList) {
                // Replace the existing line with the edited values
                if (item.getUsername().equals(username) && item.getItem().equals(newItem)) {
                    writer.write(username + "#" + newItem + "#" + newLocation + "#" + newPrice + "#" + newDate + "#" + newWebsite + "#" + newNote + "#" + newPhoto);
                } else {
                    // Write the unchanged line
                    writer.write(item.getUsername() + "#" + item.getItem() + "#" + item.getLocation() + "#" + item.getPrice() + "#" + item.getDate() + "#" + item.getWeb() + "#" + item.getNote() + "#" + item.getPhoto());
                }

                // Add a newline character after each line
                writer.newLine();
            }

            // Close the writer before attempting to delete/replace the original file
            writer.close();

            // Replace the original file with the temporary file
            File originalFile = new File(filename);
            if (originalFile.exists()) {
                if (originalFile.delete() && tempFile.renameTo(originalFile)) {
                    System.out.println("Item has been edited!");
                    msg.editItemMessage();
                } else {
                    System.out.println("Error renaming files. Check permissions or file usage.");
                    msg.faileToEditItem();
                }
            } else {System.out.println("Original file does not exist.");}
        } catch (IOException e) {
            System.out.println("Error during editing: " + e.getMessage());
            e.printStackTrace();
        }
    }*/
    
    public void editItem(ArrayList<HomeInventory> inventoryList, String username,
        String itemToEdit, String newLocation, String newPrice, String newDate,
        String newWebsite, String newNote, String newPhoto, String filename) {

        ErrorMessage msg = new ErrorMessage();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (HomeInventory item : inventoryList) {
                if (item.getUsername().equals(username) && item.getItem().equals(itemToEdit)) {
                    // Update the item's properties
                    item.setLocation(newLocation);
                    item.setPrice(newPrice);
                    item.setDate(newDate);
                    item.setWeb(newWebsite);
                    item.setNote(newNote);
                    item.setPhoto(newPhoto);
                }

            // Write the line to the file
                writer.write(item.getUsername() + "#" + item.getItem() + "#" + item.getLocation()
                    + "#" + item.getPrice() + "#" + item.getDate() + "#" + item.getWeb() + "#"
                    + item.getNote() + "#" + item.getPhoto());

            // Add a newline character after each line
                writer.newLine();
            }

            // Flush the writer to ensure that any buffered data is written to the file
            writer.flush();

            System.out.println("Item has been edited!");
            msg.editItemMessage();
        } catch (IOException e) {
            System.out.println("Error during editing: " + e.getMessage());
            e.printStackTrace();
            msg.faileToEditItem();
        }
    }
}
