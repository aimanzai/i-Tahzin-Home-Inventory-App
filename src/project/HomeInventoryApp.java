package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Group Members:
 * (1) Mohamad Aiman Bin Zainuddin
 * (2) Muhammad Zamri
 * (3) Muhammad Eimran
 */
public class HomeInventoryApp extends Application {
    private TextField tfUsername = new TextField();
    private TextField tfPassword = new TextField();
    private TextField tfAge = new TextField();
    private TextField tfEmail = new TextField();
    private TextField tfProfilePic = new TextField();
    private TextField searchText = new TextField();
    private Text itemText = new Text();
    private Text locationText = new Text();
    private Text priceText = new Text();
    private DatePicker dateTF = new DatePicker();
    private Text storeText = new Text();
    private Text noteText = new Text();
    private Text photoText = new Text();
    private VBox content;
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        //Declare the name of the file and arraylist for the home inventory
        String filename = "HomeInventoryFile.txt";
        ArrayList<HomeInventory> inventoryList = new ArrayList<>();
        
        //Declare String for each of the textfield to use it as a default value for each of the class that we want to use
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String age = tfAge.getText();
        String email = tfEmail.getText();
        String profilePic = tfProfilePic.getText();
        //Declare class login and register with the string value that we delcare (i'm asume this will take null value?)
        LoginUser login = new LoginUser(username, password, age, email, profilePic);
        RegisterUser register = new RegisterUser(username, password, age, email, profilePic);
        
        VBox loginPage = LoginPage(login, register, filename, inventoryList, primaryStage);
        loginPage.setAlignment(Pos.CENTER);
        
        //Try to make picture as a background in the login
        InputStream streamBG = new FileInputStream("media/login.jpg");
        Image imageBG = new Image(streamBG);
        Region backgroundRegion = new Region();
        backgroundRegion.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        backgroundRegion.setBackground(new Background(new BackgroundImage(
                imageBG,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        )));
        
        //Stack all of it
        StackPane sp = new StackPane(backgroundRegion, loginPage);
        sp.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(sp);
        
        //To make sure the stage is in full screen
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public VBox LoginPage(LoginUser login, RegisterUser register, String filename,  ArrayList<HomeInventory> inventoryList, 
            Stage primaryStage) throws FileNotFoundException {
        VBox mainLayout = new VBox();
        
        //To put a logo on top of the textfield username & password
        InputStream stream = new FileInputStream("media/logo.png");
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(175);
        imageView.setPreserveRatio(true);
        
        //HBox for username
        HBox username = new HBox();
        Label lblUsername = new Label("Username: ");
        lblUsername.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        TextField tfUsername = new TextField();
        username.getChildren().addAll(lblUsername, tfUsername);
        username.setAlignment(Pos.CENTER);
        
        //HBox for password
        HBox password1 = new HBox();
        Label lblPassword = new Label("Password: ");
        lblPassword.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        //TextField tfPassword = new TextField();
        PasswordField tfPassword = new PasswordField();
        password1.getChildren().addAll(lblPassword, tfPassword);
        password1.setAlignment(Pos.CENTER);

        Label statement = new Label("Don't have an account? Register now");
        statement.setFont(Font.font("Times New Roman", 15));
        
        //To make the buttons in vertical which are login & register button
        VBox btn = new VBox(12);
        Button btnLogin = new Button("Login");
        btnLogin.setPrefWidth(220);
        btnLogin.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        btnLogin.setOnAction(e -> {
            if(login.userLogin(tfUsername.getText(), tfPassword.getText())) {
                try {
                    String email = login.getEmail(); // Use the getter method to get the email
                    String age = login.getAge();   
                    displayHomePage(primaryStage, filename, inventoryList, login, tfUsername.getText(), tfPassword.getText()); //it will display homepage if the username and password is correct
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(HomeInventoryApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
               ErrorMessage em = new ErrorMessage();
               em.faileToLogin();//Shows login error message if the username or password is invalid
            }
        });

        Button btnRegister = new Button("Register");
        btnRegister.setPrefWidth(220);
        btnRegister.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        btnRegister.setOnAction(e -> {
            try {
                RegisterPage(login, register, filename, inventoryList, primaryStage);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HomeInventoryApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btn.getChildren().addAll(btnLogin, btnRegister);
        btn.setAlignment(Pos.CENTER);
        //End of the VBox button
        
        //To put all the HBox and VBox inside the VBox frame
        VBox frame = new VBox(10);
        frame.getChildren().addAll(imageView,username, password1, statement, btn);
        frame.setAlignment(Pos.CENTER);
        frame.setStyle("-fx-background-color: white;");
        frame.setMinWidth(500);
        frame.setMaxWidth(500);
        frame.setMinHeight(400);
        frame.setMaxHeight(400);
        frame.setStyle("-fx-background-radius: 10;-fx-background-color: #ffffff;");
        
        //To give shadow effect of the frame
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0.02);
        dropShadow.setOffsetY(0.02);
        frame.setEffect(dropShadow);

        mainLayout.getChildren().add(frame);
        return mainLayout;
    }
    
    private void RegisterPage(LoginUser login, RegisterUser register, String filename,  ArrayList<HomeInventory> inventoryList, Stage primaryStage) throws FileNotFoundException {
        primaryStage.close(); //to close previous stage
        Stage ShowRegister = new Stage(); //create a new stage for register page
        
         //To put a logo on top of the textfields
        InputStream stream = new FileInputStream("media/logo.png");
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(175);
        imageView.setPreserveRatio(true);
        
        //To declare each of the information that user needs to register in the HBox
        HBox username = new HBox();
        Label lblUsername = new Label("Username: ");
        lblUsername.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        tfUsername = new TextField();
        username.getChildren().addAll(lblUsername, tfUsername);
        username.setAlignment(Pos.CENTER);

        HBox password = new HBox();
        Label lblPassword = new Label("Password: ");
        lblPassword.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        tfPassword = new TextField();
        password.getChildren().addAll(lblPassword, tfPassword);
        password.setAlignment(Pos.CENTER);

        HBox age = new HBox();
        Label lblAge = new Label("Age: ");
        lblAge.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        tfAge = new TextField();
        age.getChildren().addAll(lblAge, tfAge);
        age.setAlignment(Pos.CENTER);

        HBox email = new HBox();
        Label lblEmail = new Label("Email: ");
        lblEmail.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        tfEmail = new TextField();
        email.getChildren().addAll(lblEmail, tfEmail);
        email.setAlignment(Pos.CENTER);
        
        HBox pic = new HBox(10);
        Label lblPhoto = new Label("Profile Picture: ");
        lblPhoto.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        tfProfilePic = new TextField();
        Button photoBtn = new Button("...");
        Stage stage = new Stage();
        photoBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image File");
                fileChooser.getExtensionFilters().addAll(
                        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp") //set of picture that it can read
                );

                List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
                if (selectedFiles != null && !selectedFiles.isEmpty()) {
                    File selectedFile = selectedFiles.get(0);
                    String filePath = selectedFile.getAbsolutePath();
                    tfProfilePic.setText(filePath);
                }
            }
        });
        pic.getChildren().addAll(lblPhoto, tfProfilePic, photoBtn);
        pic.setAlignment(Pos.CENTER);
        
        ErrorMessage em = new ErrorMessage(); //declare ErrorMessage class 
        
        Button btnRegister = new Button("Create acoount");
        btnRegister.setPrefWidth(100);
        btnRegister.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        btnRegister.setOnAction(e -> {
            //this if else is to make sure that the user input all the textfield with the correct value  
            if(em.isPresent(tfUsername, "Username") && em.isPresent(tfPassword, "Password") && 
                    em.isInteger(tfAge, "Age") && em.isPresent(tfEmail, "Email") && em.isPresent(tfProfilePic, "Profile Picture")){
                register.userAction(tfUsername.getText(), tfPassword.getText(), tfAge.getText(), tfEmail.getText(), tfProfilePic.getText());
            }
        });
        
        //Declare button for close, if user clicks it, it will shows the login page and close the register page
        Button close = new Button("Close");
        close.setPrefWidth(100);
        close.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        close.setOnAction(e -> {
            primaryStage.show();
            ShowRegister.close();  
        });
        
        //Set the button inside HBox so that it will in the horizontal
        HBox btn = new HBox(10, btnRegister, close);
        btn.setAlignment(Pos.CENTER);

        VBox frame = new VBox(30);
        frame.getChildren().addAll( imageView,username, password, age, email, pic,btn);
        frame.setAlignment(Pos.CENTER);
        frame.setStyle("-fx-background-color: white;");
        frame.setMinWidth(500);
        frame.setMaxWidth(500);
        frame.setMinHeight(550);
        frame.setMaxHeight(550);
        frame.setStyle("-fx-background-radius: 10;-fx-background-color: #ffffff;");
        
        //To give shadow effect of the frame
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0.02);
        dropShadow.setOffsetY(0.02);
        frame.setEffect(dropShadow);
    
        VBox root = new VBox(frame);
        root.setAlignment(Pos.CENTER);
        
        //to put a background image for the register page
        InputStream streamBG = new FileInputStream("media/register.jpg");
        Image imageBG = new Image(streamBG);
        Region backgroundRegion = new Region();
        backgroundRegion.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        backgroundRegion.setBackground(new Background(new BackgroundImage(
                imageBG,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        )));
        
        //stack background image with
        StackPane sp = new StackPane(backgroundRegion, root);
        sp.setAlignment(Pos.CENTER);
    
        Scene scene = new Scene(sp);
        
        ShowRegister.setMaximized(true);
        ShowRegister.setScene(scene);
        ShowRegister.show();
    }
    
    private void displayHomePage(Stage primaryStage, String filename, ArrayList<HomeInventory> inventoryList, LoginUser login, String username, String pass) throws FileNotFoundException {
        primaryStage.close();
        Stage homepage = new Stage();
        
        //Declare each of the textfield value with the string
        String search = searchText.getText();
        String item = itemText.getText();
        String location = locationText.getText();
        String price = priceText.getText();
        String date = dateTF.getValue() != null ? dateTF.getValue().toString() : "";
        String website = storeText.getText();
        String note = noteText.getText();
        String photo = photoText.getText();
        
        //Assign the stirng inside the HomeInventory & Readitem class
        HomeInventory HI = new HomeInventory(username, item, location, price, date, website, note, photo);
        ReadItem read = new ReadItem(username, item, location, price, date, website, note, photo);
        read.readFromFile(filename, inventoryList); //to read each value inside the textfile
        
        //Create a borderpane to make a left side and right side
        BorderPane border = new BorderPane();
        VBox sidenav = sideNav(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, login, username, pass); //call the sideNav method
        content = new VBox(); //declare the content into new VBox
        content =  homePage(filename, inventoryList, username); // assign the homepage method to the content
        content.setStyle("-fx-background-color: #ece9e3;"); //to set the background color of the content
        border.setLeft(sidenav); //assign the side navigation value to the left side
        border.setCenter(content); //while the content on the right side
        
        //create a new scene where it takes border as a parent
        Scene scene = new Scene(border);

        homepage.setMaximized(true);
        homepage.setScene(scene);
        homepage.show();
    }
    
    public VBox sideNav(String filename, ArrayList<HomeInventory> inventoryList, ReadItem read, 
            String search, String item, String location, String price, String date, String website, String note, String photo, HomeInventory HI, 
            LoginUser login, String username, String pass) throws FileNotFoundException {
        
        ErrorMessage msg = new ErrorMessage(); //declare ErrorMessage class
        
        //Create the panel for the sidebar
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color:#2b312c;");
        sidebar.setPrefWidth(200);
        
        //Divide the sidebar onto two parts which are top and bottom
        VBox top = new VBox();
        top.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(top, Priority.ALWAYS); // Allow top to expand
        
        try {
            File file = new File("LoginInfo.txt");

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(username)) {
                    //to put a picture
                    InputStream stream = new FileInputStream(userData[4]);
                    Image image = new Image(stream);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(140);
                    imageView.setPreserveRatio(true);
                    
                    //assign the current user's username on to the usernameT 
                    Text usernameT = new Text(username);
                    usernameT.setFill(Color.WHITE);
                    usernameT.setFont(Font.font("Verdana",15));

                    //set the margin for the picture and username
                    VBox.setMargin(imageView, new Insets(30, 0, 20, 0));
                    VBox.setMargin(usernameT, new Insets(0, 0, 30, 0));
        
                    Button homeButton = new Button("Home");
                    Button inventoryButton = new Button("Inventory");
                    Button settingButton = new Button("Profile");

                    top.getChildren().addAll(imageView,usernameT,homeButton,inventoryButton, settingButton); //add everything in the top
                    //end of the top part       
        
                    //To declare the bottom part
                    VBox bottom = new VBox();
                    bottom.setAlignment(Pos.BOTTOM_CENTER);
                    Button logoutButton = new Button("Logout");
                    bottom.getChildren().add(logoutButton);
                    //End of the bottom part
        
                    //Set size for all the buttons
                    homeButton.setPrefSize(150, 50);
                    inventoryButton.setPrefSize(150, 50);
                    settingButton.setPrefSize(150, 50);
                    logoutButton.setPrefSize(150, 50);

                    //Set style for each of the button
                    homeButton.setStyle("-fx-background-color: #7f8380;");
                    homeButton.setTextFill(Color.WHITE);
                    homeButton.setAlignment(Pos.CENTER_LEFT); // Adjust the alignment as needed

                    inventoryButton.setStyle("-fx-background-color: #2b312c;");
                    inventoryButton.setTextFill(Color.WHITE);
                    inventoryButton.setAlignment(Pos.CENTER_LEFT); // Adjust the alignment as needed

                    settingButton.setStyle("-fx-background-color: #2b312c;");
                    settingButton.setTextFill(Color.WHITE);
                    settingButton.setAlignment(Pos.CENTER_LEFT); // Adjust the alignment as needed

                    logoutButton.setStyle("-fx-background-color: #2b312c;");
                    logoutButton.setTextFill(Color.WHITE);
                    logoutButton.setAlignment(Pos.CENTER_LEFT); 
        
            homeButton.setOnAction(e -> {
                content.getChildren().clear(); //to make sure to clear the current content
                try {
                    content.getChildren().add(homePage(filename, inventoryList, username));//assign the value of the homepage into the content
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(HomeInventoryApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                //set differnet background color for each of the button (to make the user which button that he clicks right now)
                inventoryButton.setStyle("-fx-background-color: #2b312c;");
                homeButton.setStyle("-fx-background-color: #7f8380;");
                settingButton.setStyle("-fx-background-color: #2b312c;");
                logoutButton.setStyle("-fx-background-color: #2b312c;");
            });
            inventoryButton.setOnAction(e -> {
                content.getChildren().clear();//to make sure to clear the current content
                //assign the value of the inventoryPage into the content
                content.getChildren().add(inventoryPage(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, username));
                 //set differnet background color for each of the button (to make the user which button that he clicks right now)
                inventoryButton.setStyle("-fx-background-color: #7f8380;");
                homeButton.setStyle("-fx-background-color: #2b312c;");
                settingButton.setStyle("-fx-background-color: #2b312c;");
                logoutButton.setStyle("-fx-background-color: #2b312c;");
            });
            settingButton.setOnAction(e -> {
                content.getChildren().clear();//to make sure to clear the current content
                content.getChildren().add(profilePage(filename, inventoryList, login, username, pass));//assign the value of the settingPage into the content
                //set differnet background color for each of the button (to make the user which button that he clicks right now)
                inventoryButton.setStyle("-fx-background-color: #2b312c;");
                homeButton.setStyle("-fx-background-color: #2b312c;");
                settingButton.setStyle("-fx-background-color: #7f8380;");
                logoutButton.setStyle("-fx-background-color: #2b312c;");
            });
            logoutButton.setOnAction(e -> {
                //set differnet background color for each of the button (to make the user which button that he clicks right now)
                inventoryButton.setStyle("-fx-background-color: #2b312c;");
                homeButton.setStyle("-fx-background-color: #2b312c;");
                settingButton.setStyle("-fx-background-color: #2b312c;");
                logoutButton.setStyle("-fx-background-color: #7f8380;");
                msg.confirmExit();
            });
        
            //assign the value for top and bottom to the sidebar
            sidebar.getChildren().add( top);
            sidebar.getChildren().add(bottom);
        
            //To create a shadow effect on the sidebar
            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(2);
            dropShadow.setOffsetY(2);
            sidebar.setEffect(dropShadow);
            }
        }

            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sidebar;
    }
    
    public VBox homePage(String filename,  ArrayList<HomeInventory> inventoryList, String username) throws FileNotFoundException{
        VBox c = new VBox();
        
        VBox contentPanel = new VBox(40);
        contentPanel.setPrefHeight(720);
        contentPanel.setMinWidth(1280);
        contentPanel.setMaxWidth(1280);
        
        //declare a HBox to display the welcoming text 
        HBox displayText = new HBox();
        
        VBox textVB = new VBox(15);
        textVB.setAlignment(Pos.CENTER_LEFT);
        textVB.setPrefHeight(700);
        textVB.setPrefWidth(400);
        Text welcomeLabel = new Text("Welcome, " + username + "!");
        welcomeLabel.setTextAlignment(TextAlignment.LEFT);
        welcomeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        VBox.setMargin(welcomeLabel, new Insets(0, 00, 0, 57));
        
        VBox info = new VBox(10);
        String introText = "Welcome to the i-Tahzin, your easy-to-use one-stop shop for managing "
                + "and recording your personal things. You may build extensive inventory lists using our software. "
                + "You may access your inventory from any device and store it in the cloud for safety and accessibility "
                + "using our app. The straightforward design of i-Tahzin makes creating"
                + " a home inventory simple and stress-free, and our team is available to assist you "
                + "every step along the way. We are delighted to welcome you to the i-Tahzin"
                + " and we look forward to assisting you in managing your personal things"
                + " and providing you with peace of mind.";

        Text introTextFlow = new Text(introText);
        introTextFlow.setFont(Font.font("Verdana", 15));
        introTextFlow.setTextAlignment(TextAlignment.LEFT);
        introTextFlow.setWrappingWidth(650); // Set the width to wrap the text
        info.setPrefWidth(300);
        info.getChildren().add(introTextFlow);
        
        textVB.getChildren().addAll(welcomeLabel, info);
        
        //To put a picture beside the welcoming text
        InputStream stream = new FileInputStream("media/home.png");
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
        
        displayText.setAlignment(Pos.CENTER);
        displayText.getChildren().addAll(textVB, imageView);
        //end of the displayText HBox
        
        HBox displaySponsor = new HBox(30);
        displaySponsor.setAlignment(Pos.CENTER);
        Label lbl1 = new Label("Powered By");
        Label lbl2 = new Label("not");
        Label lbl3 = new Label("Sponsored By");
        
        HBox combine = new HBox();
        combine.setAlignment(Pos.CENTER);
        combine.getChildren().addAll(lbl2, lbl3);
        
        lbl1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        lbl2.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        lbl3.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        
        InputStream stream1 = new FileInputStream("media/logo/netbeans.png");
        Image image1 = new Image(stream1);
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitHeight(50);
        imageView1.setPreserveRatio(true);
        
        InputStream stream2 = new FileInputStream("media/logo/iium.png");
        Image image2 = new Image(stream2);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitHeight(50);
        imageView2.setPreserveRatio(true);
        
        InputStream stream3 = new FileInputStream("media/logo/petronas.png");
        Image image3 = new Image(stream3);
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitHeight(50);
        imageView3.setPreserveRatio(true);
        
        InputStream stream4 = new FileInputStream("media/logo/google.png");
        Image image4 = new Image(stream4);
        ImageView imageView4 = new ImageView(image4);
        imageView4.setFitHeight(50);
        imageView4.setPreserveRatio(true);
        
        displaySponsor.getChildren().addAll(lbl1, imageView1, combine,imageView2, imageView3, imageView4);
        
        contentPanel.getChildren().addAll(displayText, displaySponsor);
        c.getChildren().add(contentPanel);
        c.setAlignment(Pos.CENTER); // Set the overall alignment of the content
        
        return c;
    } 
    
    public VBox inventoryPage(String filename,  ArrayList<HomeInventory> inventoryList,  ReadItem read, 
            String search, String item, String location, String price, String date, String website, String note, String photo, HomeInventory HI, String username){
        
        VBox c = new VBox();
        
        VBox contentPanel = new VBox();
        contentPanel.setPrefHeight(720);
        contentPanel.setMinWidth(1280);
        contentPanel.setMaxWidth(1280);
        
        Text invText = new Text("Inventory");
        invText.setFill(Color.BLACK);
        invText.setFont(Font.font("Verdana",40));
        invText.setTextAlignment(TextAlignment.LEFT);
        VBox.setMargin(invText, new Insets(0, 00, 0, 57));
        
        //decalre new search and item panel to display all the items inside the textfile
        HBox searchPanel = searchPanel(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, itemText, locationText, priceText, dateTF, storeText, noteText, photoText, username);
        VBox itemPanel = displayAllItemsPanel(filename, inventoryList, read, search, item, HI, username);
        
        //create a new VBox to add all the text, search panel and item panel
        VBox cp = new VBox();
        cp.getChildren().addAll(invText,searchPanel, itemPanel);
        cp.setAlignment(Pos.CENTER);
        cp.setSpacing(20);

        contentPanel.getChildren().add(cp);
        c.getChildren().add(contentPanel);
        c.setAlignment(Pos.CENTER);
        
        return c;
    }
    
    public VBox profilePage(String filename, ArrayList<HomeInventory> inventoryList, LoginUser login, String username, String pass) {

        VBox c = new VBox();

        VBox contentPanel = new VBox(20);
        contentPanel.setPrefHeight(720);
        contentPanel.setMinWidth(1280);
        contentPanel.setMaxWidth(1280);

        Text settingText = new Text("Profile");
        settingText.setFill(Color.BLACK);
        settingText.setFont(Font.font("Verdana", 40));
        settingText.setTextAlignment(TextAlignment.LEFT);
        VBox.setMargin(settingText, new Insets(0, 00, 0, 57));
    
        try {
            File file = new File("LoginInfo.txt");

            if (!file.exists()) {
                return c;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(username)) {
                    // User found, proceed to display profile
                    VBox username1 = new VBox();
                    Label lblUsername = new Label("Username");
                    TextField tfUsername = new TextField();
                    tfUsername.setText(username);
                    tfUsername.setEditable(false);
                    username1.getChildren().addAll(lblUsername, tfUsername);
                    username1.setMaxWidth(200);
                    username1.setAlignment(Pos.CENTER);

                    /*VBox password = new VBox();
                    Label lblPassword = new Label("Password");
                    TextField tfPassword = new TextField();
                    tfPassword.setText(userData[1]);
                    tfPassword.setEditable(false);
                    password.getChildren().addAll(lblPassword, tfPassword);
                    password.setMaxWidth(200);
                    password.setAlignment(Pos.CENTER);*/

                    VBox age1 = new VBox();
                    Label lblAge = new Label("Age");
                    tfAge.setText(userData[2]); // Assuming age is at index 2 in the data
                    tfAge.setEditable(true);
                    age1.getChildren().addAll(lblAge, tfAge);
                    age1.setMaxWidth(200);
                    age1.setAlignment(Pos.CENTER);

                    VBox email1 = new VBox();
                    Label lblEmail = new Label("Email");
                    tfEmail.setText(userData[3]); // Assuming email is at index 3 in the data
                    tfEmail.setEditable(true);
                    email1.getChildren().addAll(lblEmail, tfEmail);
                    email1.setMaxWidth(200);
                    email1.setAlignment(Pos.CENTER);
                    
                    VBox pic = new VBox();
                    HBox photoP = new HBox(10);
                    Label lblPic = new Label("Profile Picture");
                    tfProfilePic.setText(userData[4]); // Assuming profile is at index 4 in the data
                    tfProfilePic.setEditable(true);
                    pic.setMaxWidth(200);
                    pic.setAlignment(Pos.CENTER);
                    
                    Button photoBtn = new Button("...");
                    Stage stage = new Stage();
                    photoBtn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Select Image File");
                            fileChooser.getExtensionFilters().addAll(
                                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
                            // Add more image file extensions as needed
                            );

                            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
                        
                            if (selectedFiles != null && !selectedFiles.isEmpty()) {
                                File selectedFile = selectedFiles.get(0);
                                String filePath = selectedFile.getAbsolutePath();
                                tfProfilePic.setText(filePath);
                                System.out.println("Selected Image: " + filePath);
                            }
                        }
                    });
    
                    photoP.getChildren().addAll(tfProfilePic,photoBtn);
                    photoP.setAlignment(Pos.CENTER);
                    VBox.setMargin(photoP, new Insets(0, 0, 40, 0));
                    
                    Button saveButton = new Button("Save");
                    saveButton.setPrefWidth(80);
                    saveButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
                    
                    ErrorMessage em = new ErrorMessage();
        
                    saveButton.setOnAction((ActionEvent e) -> {
                        if(em.isInteger(tfAge, "Age") && em.isPresent(tfEmail, "Email") && 
                            em.isPresent(tfProfilePic, "Photo")){
            
                            String newAge = tfAge.getText();
                            String newEmail = tfEmail.getText();
                            String newPhoto = tfProfilePic.getText();
                            
                            EditProfile ef = new EditProfile(username, userData[1], newAge, newEmail, newPhoto);
                            ef.userAction(username, userData[1], newAge, newEmail, newPhoto);
                        }
            
                    });
                    pic.getChildren().addAll(lblPic, photoP);
                    VBox userInfo = new VBox(10);                    
                    userInfo.getChildren().addAll(username1,age1, email1, pic, saveButton);
                    userInfo.setAlignment(Pos.CENTER);
                    userInfo.setStyle("-fx-background-color: white;");
                    userInfo.setMinWidth(500);
                    userInfo.setMaxWidth(500);
                    userInfo.setMinHeight(400);
                    userInfo.setMaxHeight(400);
                    userInfo.setStyle("-fx-background-radius: 10;-fx-background-color: #ffffff;");

                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setOffsetX(0.02);
                    dropShadow.setOffsetY(0.02);
                    userInfo.setEffect(dropShadow);

                    contentPanel.getChildren().addAll(settingText,userInfo);
                    contentPanel.setAlignment(Pos.CENTER);
                    c.getChildren().add( contentPanel);
                    c.setAlignment(Pos.CENTER);
                    break;
                }
            }

            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return c;
    }
    
    public VBox addPage(String filename, ArrayList<HomeInventory> inventoryList, ReadItem read, String search, String item, String location, String price, 
            String date, String website, String note, String photo, HomeInventory HI, String username) throws FileNotFoundException {
        
        VBox c = new VBox();
        VBox contentPanel = new VBox();

        ErrorMessage msg = new ErrorMessage(); //declare the ErrorMessage class
        
        //to make the title for the page
        Text addText = new Text("Add Item");
        addText.setFill(Color.BLACK);
        addText.setFont(Font.font("Verdana", 40));
        addText.setTextAlignment(TextAlignment.CENTER);
        VBox.setMargin(addText, new Insets(20, 0, 0, 0));

        // Panel for the left section
        VBox panelLeft = new VBox(10);
        panelLeft.setAlignment(Pos.CENTER_RIGHT);
        
        //declare horizontol box for item label and textfield
        HBox itemP = new HBox(10);
        Label itemLabel = new Label("Inventory Item: ");
        TextField itemTF = new TextField();
        itemTF.setPrefWidth(300);
        itemTF.setPrefHeight(30);
        itemP.getChildren().addAll(itemLabel, itemTF);
        itemP.setAlignment(Pos.CENTER);
        VBox.setMargin(itemP, new Insets(0, 0, 40, 0));
        
        //declare horizontol box for location label and textfield
        HBox locationP = new HBox(10);
        Label locationLabel = new Label("Location: ");
        TextField locationTF = new TextField();
        locationTF.setPrefWidth(300);
        locationTF.setPrefHeight(30);
        locationP.getChildren().addAll(locationLabel, locationTF);
        locationP.setAlignment(Pos.CENTER);
        VBox.setMargin(locationP, new Insets(0, 0, 40, 0));
        
        //declare horizontol box for price label and textfield
        HBox priceP = new HBox(10);
        Label priceLabel = new Label("Purchase Price: ");
        TextField priceTF = new TextField();
        priceTF.setPrefWidth(300);
        priceTF.setPrefHeight(30);
        priceP.getChildren().addAll(priceLabel, priceTF);
        priceP.setAlignment(Pos.CENTER);
        VBox.setMargin(priceP, new Insets(0, 0, 40, 0));
    
        panelLeft.getChildren().addAll(itemP, locationP, priceP);
        // End of the left panel section

        // Panel for the right section
        VBox panelRight = new VBox(10);
        panelRight.setAlignment(Pos.CENTER_LEFT);
        
        //declare horizontol box for date label and textfield
        HBox dateP = new HBox(10);
        Label dateLabel = new Label("Date Purchased: ");
        DatePicker dp = new DatePicker(); //declare date picker since user can choose the date that he purchased the item
        dp.setPrefWidth(300);
        dp.setPrefHeight(30);
        dateP.getChildren().addAll(dateLabel, dp);
        dateP.setAlignment(Pos.CENTER);
        VBox.setMargin(dateP, new Insets(0, 0, 40, 0));
        
        //declare horizontol box for store label and textfield
        HBox storeP = new HBox(10);
        Label storeLabel = new Label("Stored / Website: ");
        TextField storeTF = new TextField();
        storeTF.setPrefWidth(300);
        storeTF.setPrefHeight(30);
        storeP.getChildren().addAll(storeLabel, storeTF);
        storeP.setAlignment(Pos.CENTER);
        VBox.setMargin(storeP, new Insets(0, 0, 40, 0));
        
        //declare horizontol box for note label and textfield
        HBox noteP = new HBox(10);
        Label noteLabel = new Label("Note: ");
        TextField noteTF = new TextField();
        noteTF.setPrefWidth(300);
        noteTF.setPrefHeight(30);
        noteP.getChildren().addAll(noteLabel, noteTF);
        noteP.setAlignment(Pos.CENTER);
        VBox.setMargin(noteP, new Insets(0, 0, 40, 0));   
        
        //declare vertical box for photo label and textfield
        VBox photoPanel = new VBox();
        HBox photoP = new HBox(10);
        Label photoLabel = new Label("Photo: ");
        TextField photoTF = new TextField();
        photoTF.setPrefWidth(300);
        photoTF.setPrefHeight(30);
        
        //When user click the button it will open new stage and user can click the picture of the item
        Button photoBtn = new Button("...");
        Stage stage = new Stage();
        photoBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image File");
                fileChooser.getExtensionFilters().addAll(
                        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp") //set of picture that it can read
                );

                List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
                if (selectedFiles != null && !selectedFiles.isEmpty()) {
                    File selectedFile = selectedFiles.get(0);
                    String filePath = selectedFile.getAbsolutePath();
                    photoTF.setText(filePath);
                }
            }
        });
    
        photoP.getChildren().addAll(photoLabel, photoTF, photoBtn);
        photoP.setAlignment(Pos.CENTER);
        VBox.setMargin(photoP, new Insets(0, 0, 40, 0));
        photoPanel.getChildren().add(photoP);
    
        panelRight.getChildren().addAll(dateP, storeP, noteP);
        // End of the right panel section
        
        //Declare all the buttons and put it inside the horizontol box
        Button backButton = new Button("Back");
        Button saveButton = new Button("Save");
        Button clearButton = new Button("Clear");
        HBox buttonPanel = new HBox(10);
        buttonPanel.getChildren().addAll(backButton,saveButton, clearButton);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPadding(new Insets(10, 0, 0, 0));
        
        //to set a width for the buttons
        backButton.setPrefWidth(80);
        saveButton.setPrefWidth(80);
        clearButton.setPrefWidth(80);
        
        //to set a background color & the color of the text inside the buttons
        backButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        saveButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        clearButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        
        backButton.setOnAction(e -> {
            content.getChildren().clear(); //it will clear the current content which is the AddPage
            //the content will change it to the inventory page
            content.getChildren().add(inventoryPage(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, username));
        });
        
        ErrorMessage em = new ErrorMessage(); //to declare the ErrorMessage class
        
        saveButton.setOnAction((ActionEvent e) -> {
            //to make sure that whole textfield is not null and have a correct value
            if(em.isPresent(itemTF, "Inventory Item") && em.isPresent(locationTF, "Location") && 
               em.isDouble(priceTF, "Price") && em.isPresent(storeTF, "Store/Website") && 
               em.isPresent(noteTF, "note") && em.isPresent(photoTF, "Photo")){
            
                String newItem = itemTF.getText();
                String newLocation = locationTF.getText();
                String newPrice = priceTF.getText();
                String newDate = dp.getValue().toString(); // Assuming dp is a DatePicker
                String newWebsite = storeTF.getText();
                String newNote = noteTF.getText();
                String newPhoto = photoTF.getText();

                // Create an instance of the AddItem class
                AddItem addItem = new AddItem(username, newItem, newLocation, newPrice, newDate, newWebsite, newNote, newPhoto);

                // Call the addItem method to add the new item to the file
                addItem.addItem(filename, inventoryList, username, newItem, newLocation, newPrice, newDate, newWebsite, newNote, newPhoto);
                //to call the message when user save the items
                msg.saveItemMessage(itemTF, locationTF, priceTF, storeTF, noteTF, photoTF);
            }
        });
        
        //to clear all the textfields
        clearButton.setOnAction((ActionEvent e) -> {
            itemTF.clear();
            locationTF.clear();
            priceTF.clear();
            storeTF.clear();
            noteTF.clear();
            photoTF.clear();
        });
        
        //create a new horizontol box to assign the title of the page inside it (declare it as top)
        HBox top = new HBox();
        top.getChildren().add(addText);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20, 0, 0, 0));
        
        //create the horizontol box where it combine all the children inside panel left and right
        HBox center = new HBox(50);
        center.getChildren().addAll(panelLeft, panelRight);
        center.setAlignment(Pos.CENTER);

        //create horizontol box for the button panel with the name of bottom
        HBox bottom = new HBox();
        bottom.getChildren().add(buttonPanel);
        bottom.setAlignment(Pos.CENTER);
        
        //create a new vertical box to combine all of the horizontol box, which are top, center and bottom
        VBox combine = new VBox(30);
        combine.setStyle("-fx-background-radius: 10;-fx-background-color: #faf4f6;");
        combine.setPrefHeight(1000);
        combine.setPrefWidth(1500);
        combine.getChildren().addAll(top, center, photoPanel,bottom);
        
        //to give a shadow effect to the vertical box
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0.02);
        dropShadow.setOffsetY(0.02);
        combine.setEffect(dropShadow);
        
        contentPanel.getChildren().add(combine);
        contentPanel.setPrefHeight(720);
        contentPanel.setMinWidth(1000);
        contentPanel.setMaxWidth(1000);
        c.getChildren().add(contentPanel);
        c.setAlignment(Pos.CENTER); // Set the overall alignment of the content

        return c;
    }
    
    public VBox searchPage(String filename,  ArrayList<HomeInventory> inventoryList,  ReadItem read, 
            String search, String item, String location, String price, String date, String website, String note, String photo, HomeInventory HI, 
            HomeInventory foundItem, String username){
        
        VBox c = new VBox();
        
        VBox contentPanel = new VBox();
        contentPanel.setPrefHeight(720);
        contentPanel.setMinWidth(1280);
        contentPanel.setMaxWidth(1280);
        
        Text spText = new Text("Search Result for " + foundItem.getItem().toUpperCase());
        spText.setFill(Color.BLACK);
        spText.setFont(Font.font("Verdana",40));
        spText.setTextAlignment(TextAlignment.CENTER);
        VBox.setMargin(spText, new Insets(0, 00, 0, 57));
        
        HBox searchPanel = searchPanel(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, itemText, locationText, priceText, dateTF, storeText, noteText, photoText, username);
        VBox itemPanel = displaySearchItem(filename, inventoryList, foundItem, username, read, search, HI);
        
        Button backButton = new Button("Back");
        backButton.setPrefWidth(120);
        backButton.setPrefHeight(30);
        
        backButton.setOnAction(e -> {
            content.getChildren().clear();
            content.getChildren().add(inventoryPage(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, username));
        });
        
        HBox panel = new HBox();
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.getChildren().add(backButton);
        
        HBox panel1 = new HBox();
        panel1.setAlignment(Pos.CENTER);
        panel1.getChildren().add(spText);
        
        VBox panel2 = new VBox();
        panel2.getChildren().addAll(panel,panel1);
        panel2.setPadding(new Insets(10, 0, 10, 40));
        
        VBox cp = new VBox();
        cp.getChildren().addAll(panel2,searchPanel, itemPanel);
        cp.setAlignment(Pos.CENTER);
        cp.setSpacing(20);

        contentPanel.getChildren().add(cp);
        c.getChildren().add(contentPanel);
        c.setAlignment(Pos.CENTER);
        
        return c;
    }
    
    public HBox searchPanel(String filename, ArrayList<HomeInventory> inventoryList, ReadItem read, String search,
            String item, String location, String price, String date, String website, String note, String photo,
            HomeInventory HI, Text itemText, Text locationText, Text priceText, DatePicker dateTF, Text storeText,
            Text noteText, Text photoText, String username){
        
        HBox searchPanel = new HBox();
        
        searchText = new TextField();
        searchText.setPrefWidth(400);
        searchText.setPrefHeight(20);
        
        Button searchButton = new Button("Search");
        searchButton.setPrefWidth(120);
        searchButton.setPrefHeight(30);
        
        Button addButton = new Button("Add New");
        addButton.setPrefWidth(120);
        addButton.setPrefHeight(30);
        addButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        
        addButton.setOnAction(e -> {
            content.getChildren().clear();
            try {
                content.getChildren().add(addPage(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, username));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        
        Region spacing = new Region();
        spacing.setPrefWidth(40);
        
        searchPanel.getChildren().addAll(searchText, searchButton, spacing,addButton);
        searchPanel.setPadding(new Insets(10, 0, 10, 40));
        searchPanel.setAlignment(Pos.TOP_CENTER);
        
        VBox.setMargin(searchText, new Insets(0, 5, 0, 0));
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 15));
        HBox.setMargin(addButton, new Insets(0, 0, 0, 50));
        
        searchButton.setOnAction((ActionEvent e) -> {    
            String searchItem = searchText.getText();
            read.readFromFile(filename, inventoryList);

            HomeInventory foundItem = SearchItem.searchFromFile(inventoryList, searchItem, username);

            if (foundItem != null) {
                content.getChildren().clear();
                content.getChildren().addAll(searchPage(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, foundItem, username));
            } else {
                ErrorMessage msg = new ErrorMessage();
                msg.notFoundItem();
                searchText.clear();
            }
        });   
        return searchPanel;
    }
    
    public VBox displaySearchItem(String filename, ArrayList<HomeInventory> inventoryList, HomeInventory foundItem, String username, ReadItem read, String search, HomeInventory HI){
        VBox itemContainer = new VBox(); // Container to hold all item panels
        itemContainer.setSpacing(10);
        itemContainer.setPadding(new Insets(0, 0, 0, 50));
        
        itemContainer.setAlignment(Pos.CENTER);
        HBox itemPanel = new HBox(); // Create a new HBox for each item

        Label locationLabel = new Label("Location: ");
        Label priceLabel = new Label("Purchase Price: RM");
        itemText = new Text(foundItem.getItem());
        locationText = new Text(foundItem.getLocation());
        priceText = new Text(foundItem.getPrice());
            
        String item = foundItem.getItem();
        String location =foundItem.getLocation();
        String price = foundItem.getPrice();
        String date = dateTF.getValue() != null ? dateTF.getValue().toString() : "";
        String website = foundItem.getWeb();
        String note = foundItem.getNote();
        String photo = foundItem.getPhoto();
            
        DeleteItem DI = new DeleteItem(username, item, location, price, date, website, note, photo);
        ErrorMessage msg = new ErrorMessage();
            
        itemText.setFill(Color.BLACK);
        itemText.setFont(Font.font("Verdana",15));

        try {
            itemText.setText(foundItem.getItem().toUpperCase());
                
            itemPanel.setPrefHeight(125);
            itemPanel.setMinWidth(800);
            itemPanel.setMaxWidth(800);
            itemPanel.setStyle("-fx-background-radius: 10;-fx-background-color: #faf4f6;");
            itemPanel.setAlignment(Pos.CENTER);
                
            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(0.02);
            dropShadow.setOffsetY(0.02);
            itemPanel.setEffect(dropShadow);

            String photoPath = foundItem.getPhoto();
            InputStream stream = new FileInputStream(photoPath);
            Image image = new Image(stream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(120);
            
            HBox imageContainer = new HBox();
            imageContainer.setPrefWidth(175);
            imageContainer.setAlignment(Pos.CENTER_LEFT);
            imageContainer.getChildren().add(imageView);               
                
            HBox panel = new HBox();
            panel.getChildren().addAll(locationLabel,locationText);
                
            HBox panel2 = new HBox();
            panel2.getChildren().addAll(priceLabel,priceText);
                
            VBox pc = new VBox();
            pc.getChildren().addAll(itemText,panel,panel2);
            pc.setAlignment(Pos.CENTER_LEFT);
            pc.setSpacing(15);
                
            VBox buttonContainer = new VBox();
            buttonContainer.setPrefWidth(200);
            buttonContainer.setAlignment(Pos.CENTER);
            VBox.setVgrow(buttonContainer, Priority.ALWAYS);
            buttonContainer.setSpacing(10);
                
            Button editButton = new Button("Edit Item");
            editButton.setPrefWidth(120);
            editButton.setPrefHeight(30);
            editButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");

            Button deleteButton = new Button("Delete Item");
            deleteButton.setPrefWidth(120);
            deleteButton.setPrefHeight(30);
            deleteButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");

            buttonContainer.getChildren().addAll(editButton, deleteButton);
                
            Region spacing = new Region();
            spacing.setPrefWidth(250);

            itemPanel.getChildren().addAll(imageContainer, pc,spacing,buttonContainer);
            itemContainer.getChildren().add(itemPanel);
            
            editButton.setOnAction((ActionEvent e) -> {
                content.getChildren().clear();
                content.getChildren().add(editPage(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, username));
            });

            deleteButton.setOnAction((ActionEvent e) -> {
                        int index = itemContainer.getChildren().indexOf(itemPanel);

                        // Check if the index is valid and the item belongs to the current user
                        if (index >= 0 && index < inventoryList.size()) {
                        // Write the updated content of the inventoryList back to the text file
                            DI.deleteFromFile(filename, inventoryList, username, search);
                            // Update the GUI
                            itemContainer.getChildren().remove(itemPanel);
                        } else {
                            System.out.println("Invalid index or item does not belong to the current user");
                        }
                    });              
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return itemContainer;
    }

    public VBox displayAllItemsPanel(String filename, ArrayList<HomeInventory> inventoryList, ReadItem read, String search, String item,
            HomeInventory HI, String username) {

        inventoryList.clear();
        read.readFromFile(filename, inventoryList);

        VBox itemContainer = new VBox(); // Container to hold all item panels
        itemContainer.setSpacing(10);
        itemContainer.setPadding(new Insets(0, 0, 0, 50));
    
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).getUsername().equals(username)) {
                
                itemContainer.setAlignment(Pos.CENTER);
                HBox itemPanel = new HBox(); // Create a new HBox for each item

                Label locationLabel = new Label("Location: ");
                Label priceLabel = new Label("Purchase Price: RM");
                Text itemText = new Text(inventoryList.get(i).getItem());
                Text locationText = new Text(inventoryList.get(i).getLocation());
                Text priceText = new Text(inventoryList.get(i).getPrice());
                Text webText = new Text(inventoryList.get(i).getWeb());
                Text noteText = new Text(inventoryList.get(i).getNote());
                Text photoText = new Text(inventoryList.get(i).getPhoto());
                
                //DecimalFormat df = new DecimalFormat("0.00");
                
                String location = locationText.getText();
                String price = priceText.getText();
                String date = dateTF.getValue() != null ? dateTF.getValue().toString() : "";
                String website = webText.getText();
                String note = noteText.getText();
                String photo = photoText.getText();
                DeleteItem DI = new DeleteItem(username, item, location, price, date, website, note, photo);
                ErrorMessage msg = new ErrorMessage();
                
                itemText.setFill(Color.BLACK);
                itemText.setFont(Font.font("Verdana",15));

                try {
                    String newItem = itemText.getText();
                    itemText.setText(newItem.toUpperCase());
                
                    itemPanel.setPrefHeight(125);
                    itemPanel.setMinWidth(800);
                    itemPanel.setMaxWidth(800);
                    itemPanel.setStyle("-fx-background-radius: 10;-fx-background-color: #ffffff;");
                    itemPanel.setAlignment(Pos.CENTER);
                
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setOffsetX(0.02);
                    dropShadow.setOffsetY(0.02);
                    itemPanel.setEffect(dropShadow);

                    String photoPath = inventoryList.get(i).getPhoto();
                    InputStream stream = new FileInputStream(photoPath);
                    Image image = new Image(stream);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(120);
                    //imageView.setPreserveRatio(true);
                    HBox imageContainer = new HBox();
                    imageContainer.setPrefWidth(175);
                    imageContainer.setAlignment(Pos.CENTER_LEFT);
                    imageContainer.getChildren().add(imageView);               
                
                    HBox panel = new HBox();
                    panel.getChildren().addAll(locationLabel,locationText);
                
                    HBox panel2 = new HBox();
                    panel2.getChildren().addAll(priceLabel,priceText);
                
                    VBox pc = new VBox();
                    pc.getChildren().addAll(itemText,panel,panel2);
                    pc.setAlignment(Pos.CENTER_LEFT);
                    pc.setSpacing(15);
                
                    VBox buttonContainer = new VBox();
                    buttonContainer.setPrefWidth(200);
                    buttonContainer.setAlignment(Pos.CENTER);
                    VBox.setVgrow(buttonContainer, Priority.ALWAYS);
                    buttonContainer.setSpacing(10);
                
                    Button editButton = new Button("Edit Item");
                    editButton.setPrefWidth(120);
                    editButton.setPrefHeight(30);
                    editButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
                    
                    editButton.setOnAction((ActionEvent e) -> {
                        content.getChildren().clear();
                        content.getChildren().add(editPage(filename, inventoryList, read, search, newItem, location, price, date, website, note, photo, HI, username));
                    });

                    Button deleteButton = new Button("Delete Item");
                    deleteButton.setPrefWidth(120);
                    deleteButton.setPrefHeight(30);
                    deleteButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");

                    buttonContainer.getChildren().addAll(editButton, deleteButton);
                
                    Region spacing = new Region();
                    spacing.setPrefWidth(250);
                
                    //itemPanel.setSpacing(80);
                    itemPanel.getChildren().addAll(imageContainer, pc,spacing,buttonContainer);
                    itemContainer.getChildren().add(itemPanel);
                    
                    //String currentItemName = item;
                    deleteButton.setOnAction((ActionEvent e) -> {
                        int index = itemContainer.getChildren().indexOf(itemPanel);

                        // Check if the index is valid and the item belongs to the current user
                        if (index >= 0 && index < inventoryList.size()) {
                        // Write the updated content of the inventoryList back to the text file
                            DI.deleteFromFile(filename, inventoryList, username, newItem);
                            // Update the GUI
                            itemContainer.getChildren().remove(itemPanel);
                        } else {
                            System.out.println("Invalid index or item does not belong to the current user");
                        }
                    });         
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }   
        }
        return itemContainer;
    }
    
    public VBox editPage(String filename, ArrayList<HomeInventory> inventoryList, ReadItem read,
        String search, String item, String location, String price, String date, String website, String note, 
        String photo, HomeInventory HI, String username){
        
        VBox c = new VBox();
        VBox contentPanel = new VBox();
  
        Text addText = new Text("Edit Item");
        addText.setFill(Color.BLACK);
        addText.setFont(Font.font("Verdana", 40));
        addText.setTextAlignment(TextAlignment.CENTER);
        VBox.setMargin(addText, new Insets(20, 0, 0, 0));

        // Panel for the left section
        VBox panelLeft = new VBox(10);
        panelLeft.setAlignment(Pos.CENTER_RIGHT);

        HBox itemP = new HBox(10);
        Label itemLabel = new Label("Inventory Item: ");
        TextField itemTF = new TextField();
        itemTF.setEditable(false);
        itemTF.setText(item);
        itemTF.setPrefWidth(300);
        itemTF.setPrefHeight(30);
        itemP.getChildren().addAll(itemLabel, itemTF);
        itemP.setAlignment(Pos.CENTER);
        VBox.setMargin(itemP, new Insets(0, 0, 40, 0));

        HBox locationP = new HBox(10);
        Label locationLabel = new Label("Location: ");
        TextField locationTF = new TextField();
        locationTF.setText(location);
        locationTF.setPrefWidth(300);
        locationTF.setPrefHeight(30);
        locationP.getChildren().addAll(locationLabel, locationTF);
        locationP.setAlignment(Pos.CENTER);
        VBox.setMargin(locationP, new Insets(0, 0, 40, 0));

        HBox priceP = new HBox(10);
        Label priceLabel = new Label("Purchase Price: ");
        TextField priceTF = new TextField();
        priceTF.setText(price);
        priceTF.setPrefWidth(300);
        priceTF.setPrefHeight(30);
        priceP.getChildren().addAll(priceLabel, priceTF);
        priceP.setAlignment(Pos.CENTER);
        VBox.setMargin(priceP, new Insets(0, 0, 40, 0));
    
        panelLeft.getChildren().addAll(itemP, locationP, priceP);
        // End of the left panel section

        // Panel for the right section
        VBox panelRight = new VBox(10);
        panelRight.setAlignment(Pos.CENTER_LEFT);

        HBox dateP = new HBox(10);
        Label dateLabel = new Label("Date Purchased: ");
        DatePicker dp = new DatePicker();
        //dp.setValue(LocalDate.parse(date));  
        dateP.getChildren().addAll(dateLabel, dp);
        dateP.setAlignment(Pos.CENTER);
        VBox.setMargin(dateP, new Insets(0, 0, 40, 0));

        HBox storeP = new HBox(10);
        Label storeLabel = new Label("Stored / Website: ");
        TextField storeTF = new TextField();
        storeTF.setText(website);
        storeTF.setPrefWidth(300);
        storeTF.setPrefHeight(30);
        storeP.getChildren().addAll(storeLabel, storeTF);
        storeP.setAlignment(Pos.CENTER);
        VBox.setMargin(storeP, new Insets(0, 0, 40, 0));

        HBox noteP = new HBox(10);
        Label noteLabel = new Label("Note: ");
        TextField noteTF = new TextField();
        noteTF.setText(note);
        noteTF.setPrefWidth(300);
        noteTF.setPrefHeight(30);
        noteP.getChildren().addAll(noteLabel, noteTF);
        noteP.setAlignment(Pos.CENTER);
        VBox.setMargin(noteP, new Insets(0, 0, 40, 0));   
    
        VBox photoPanel = new VBox();
        HBox photoP = new HBox(10);
        Label photoLabel = new Label("Photo: ");
        TextField photoTF = new TextField();
        photoTF.setText(photo);
        photoTF.setPrefWidth(300);
        photoTF.setPrefHeight(30);
        Button photoBtn = new Button("...");
        Stage stage = new Stage();
        photoBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image File");
                fileChooser.getExtensionFilters().addAll(
                        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
                        // Add more image file extensions as needed
                );

                List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
                if (selectedFiles != null && !selectedFiles.isEmpty()) {
                    File selectedFile = selectedFiles.get(0);
                    String filePath = selectedFile.getAbsolutePath();
                    photoTF.setText(filePath);
                    System.out.println("Selected Image: " + filePath);
                    // Add your logic to handle the selected image file here
                }
            }
        });
    
        photoP.getChildren().addAll(photoLabel, photoTF, photoBtn);
        photoP.setAlignment(Pos.CENTER);
        VBox.setMargin(photoP, new Insets(0, 0, 40, 0));
        photoPanel.getChildren().add(photoP);
    
        panelRight.getChildren().addAll(dateP, storeP, noteP);
        // End of the right panel section
        
        Button backButton = new Button("Back");
        Button saveButton = new Button("Save");
        Button clearButton = new Button("Clear");
        HBox buttonPanel = new HBox(10);
        buttonPanel.getChildren().addAll(backButton,saveButton, clearButton);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPadding(new Insets(10, 0, 0, 0));
        
        backButton.setPrefWidth(80);
        saveButton.setPrefWidth(80);
        clearButton.setPrefWidth(80);
        
        backButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        saveButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        clearButton.setStyle("-fx-background-color: #000000;-fx-text-fill: white;");
        
        backButton.setOnAction(e -> {
            content.getChildren().clear();
            content.getChildren().add(inventoryPage(filename, inventoryList, read, search, item, location, price, date, website, note, photo, HI, username));
        });
        
        ErrorMessage em = new ErrorMessage();
        
        saveButton.setOnAction((ActionEvent e) -> {
            if(em.isPresent(itemTF, "Inventory Item") && em.isPresent(locationTF, "Location") && 
               em.isDouble(priceTF, "Price") && em.isPresent(storeTF, "Store/Website") && 
               em.isPresent(noteTF, "note") && em.isPresent(photoTF, "Photo")){
            
                String newItem = itemTF.getText();
                String newLocation = locationTF.getText();
                String newPrice = priceTF.getText();
                String newDate = dp.getValue().toString();
                String newWebsite = storeTF.getText();
                String newNote = noteTF.getText();
                String newPhoto = photoTF.getText();

                // Create an instance of the EditItem class
                EditItem edit = new EditItem(username, newItem, newLocation, newPrice, newDate, newWebsite, newNote, newPhoto);

                // Call the editItem method to save changes to the file
                edit.editItem(inventoryList, username, newItem, newLocation, newPrice, newDate, newWebsite, newNote, newPhoto, filename);
            }
            
        });
        
        clearButton.setOnAction((ActionEvent e) -> {
            itemTF.clear();
            locationTF.clear();
            priceTF.clear();
            storeTF.clear();
            noteTF.clear();
            photoTF.clear();
        });

        /*Region spacing = new Region();
        spacing.setPrefWidth(480);*/

        HBox top = new HBox();
        top.getChildren().add(addText);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20, 0, 0, 0));

        HBox center = new HBox(50);
        center.getChildren().addAll(panelLeft, panelRight);
        center.setAlignment(Pos.CENTER);
        
        HBox bottom = new HBox();
        bottom.getChildren().add(buttonPanel);
        bottom.setAlignment(Pos.CENTER);

        VBox combine = new VBox(30);
        combine.setStyle("-fx-background-radius: 10;-fx-background-color: #faf4f6;");
        combine.setPrefHeight(1000);
        combine.setPrefWidth(1500);
        combine.getChildren().addAll(top, center, photoPanel,bottom);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(0.02);
        dropShadow.setOffsetY(0.02);
        combine.setEffect(dropShadow);

        contentPanel.getChildren().add(combine);
        contentPanel.setPrefHeight(720);
        contentPanel.setMinWidth(1000);
        contentPanel.setMaxWidth(1000);
        c.getChildren().add(contentPanel);
        c.setAlignment(Pos.CENTER); // Set the overall alignment of the content

        return c;
    }
}
