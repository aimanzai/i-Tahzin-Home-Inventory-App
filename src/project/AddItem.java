package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddItem extends HomeInventory{

    public AddItem(String username, String item, String location, String price, String date, String website, String note, String photo) {
        super(username, item, location, price, date, website, note, photo);
    }
    
    public void addItem(String filename, ArrayList<HomeInventory> inventoryList, String username, String item, String location, String price, String date, String website, String note, String photo){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));

            writer.append(username + "#" + item + "#" + location + "#" + price + "#" + date + "#" + website + "#" + note + "#" + photo + System.lineSeparator());		
            //writer.append("Demonstrating an append mode\n");
            writer.close();
            HomeInventory newItem = new HomeInventory(username, item, location, price, date, website, note, photo);
            inventoryList.add(newItem);
        }
	catch (IOException e){
	    System.out.println(e.getMessage());
	}
    }
}
