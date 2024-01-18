package project;

public class HomeInventory {
    private String username;
    private String item;
    private String location;
    private String price;
    private String date;
    private String website;
    private String note;
    private String photo;
    
    public HomeInventory(String username, String item, String location, String price, String date, String website, String note, String photo){
        this.username = username;
        this.item = item;
        this.location = location;
        this.price = price;
        this.date = date;
        this.website = website;
        this.note = note;
        this.photo = photo;
    }
    
    public String getUsername(){return this.username;}
    public String getItem(){return this.item;}
    public String getLocation(){return this.location;}
    public String getPrice(){return this.price;}
    public String getDate(){return this.date;}
    public String getWeb(){return this.website;}
    public String getNote(){return this.note;}
    public String getPhoto(){return this.photo;}
    
    public void setItem(String item){this.item = item;}
    public void setLocation(String location){this.location = location;}
    public void setPrice(String price){this.price = price;}
    public void setDate(String date){this.date = date;}
    public void setWeb(String website){this.website = website;}
    public void setNote(String note){this.note = note;}
    public void setPhoto(String photo){this.photo = photo;}
    
    
}