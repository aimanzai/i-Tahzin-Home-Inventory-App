package project;

import java.util.ArrayList;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

public class SearchItem extends HomeInventory{
    private Text itemText = new Text();
    private Text locationText = new Text();
    private Text priceText = new Text();
    private DatePicker dateText = new DatePicker();
    private Text storeText = new Text();
    private Text noteText = new Text();
    private Text photoText = new Text();

    public SearchItem(String username, String item, String location, String price, String date, String website, String note, String photo,
            Text itemText, Text locationText, Text priceText, DatePicker dateText, Text storeText, Text noteText, Text photoText) {
        super(username, item, location, price, date, website, note, photo);
        this.itemText = itemText;
        this.locationText = locationText;
        this.priceText = priceText;
        this.dateText = dateText;
        this.storeText = storeText;
        this.noteText = noteText;
        this.photoText = photoText;
    }
    
    public static HomeInventory searchFromFile(ArrayList<HomeInventory> inventoryList, String search, String username) {
        for (HomeInventory HI : inventoryList) {
            if (search.equals(HI.getItem()) && username.equals(HI.getUsername())) {
                return HI;
            }
        }
        return null;
    }
}
