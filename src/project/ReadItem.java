package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadItem extends HomeInventory{  
    public ReadItem(String username, String item, String location, String price, String date, String website, String note, String photo) {
        super(username, item, location, price, date, website, note, photo);
    }
    
    public void readFromFile(String filename,  ArrayList<HomeInventory> inventoryList){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            
            while((line = reader.readLine()) != null){
                String[] info = line.split("#");
                
                String username = info[0];
                String item = info[1];
                String location = info[2];
                String price = info[3];
                String date = info[4];
                String website = info[5];
                String note = info[6];
                String photo = info[7];

                HomeInventory HI = new HomeInventory(username, item, location, price, date, website, note, photo);
                inventoryList.add(HI);
            }
	}
	catch(Exception e){
            System.out.println("Message: " + e);
        }
    }
}
