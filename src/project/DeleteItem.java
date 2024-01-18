package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DeleteItem extends HomeInventory{
    public DeleteItem(String username, String item, String location, String price, String date, String website, String note, String photo) {
        super(username, item, location, price, date, website, note, photo);
    }
    public void deleteFromFile(String filename, ArrayList<HomeInventory> inventoryList, String username, String item) {
        ErrorMessage msg = new ErrorMessage();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (HomeInventory HI : inventoryList) {
                // Only write items that are not the one to be deleted
                if (!(username.equals(HI.getUsername()) && item.equals(HI.getItem()))) {
                    String itemString = HI.getUsername() + "#" + HI.getItem() + "#" + HI.getLocation() + "#" + HI.getPrice() + "#" + HI.getDate() + "#" + HI.getWeb() + "#" + HI.getNote() + "#" + HI.getPhoto();
                    writer.write(itemString + System.lineSeparator());
                    inventoryList.remove(itemString);
                }
            }
            msg.deleteMessage();
        } catch (IOException ex) {
            System.out.println("Error writing to file: " + ex.getMessage());
        }
    }
    
}
