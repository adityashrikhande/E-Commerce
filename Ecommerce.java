package com.example.ecommerce;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Ecommerce extends Application {

    private final int width = 1000, height = 800, headerLine = 50;

    Pane bodyPane = new Pane();
    ProductList productList = new ProductList();
    Customer loggedInCustomer;

    Button signInButton = new Button("Sign In");
    Label welcomeLable = new Label("Welcome Customer");
    private GridPane headerBar(){
        GridPane Pane = new GridPane();

        TextField searchBar = new TextField();
        Button searchButton = new Button("Search");

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productList.getAllProducts());
            }
        });

        Pane.setHgap(10);

        Pane.add(searchBar, 0, 0);
        Pane.add(searchButton, 1, 0);
        Pane.add(signInButton, 2, 0);
        Pane.add(welcomeLable, 3, 0);

        return Pane;
    }
    private GridPane loginPage(){
        Label userLabel = new Label("User Name");
        Label passLabel = new Label("Password");
        TextField userName = new TextField();
        userName.setPromptText("Enter User Name");
        PasswordField password = new PasswordField();
        password.setPromptText("Enter Password");
        Button loginbutton = new Button("Login");
        Label messageLabel = new Label("Login - Message");

        loginbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = userName.getText();
                String pass = password.getText();

                try {
                    loggedInCustomer = Login.customerLogin(user, pass);
                    if(loggedInCustomer != null){
                        messageLabel.setText("Login Successful!!");
                        welcomeLable.setText("Welcome " + loggedInCustomer.getName());
                    }else{
                        messageLabel.setText("Login Failed!!");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });

        GridPane loginPane = new GridPane();
        loginPane.setTranslateY(50);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.add(userLabel, 12, 0);
        loginPane.add(userName, 15, 0);
        loginPane.add(passLabel, 12, 1);
        loginPane.add(password, 15, 1);
        loginPane.add(loginbutton, 12, 2);
        loginPane.add(messageLabel, 15, 2);

        return loginPane;
    }

    private void showDialogue(String message){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Order Status");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

    private GridPane footerBar(){
        Button buyNewButton = new Button("Buy Now");
        buyNewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProducts();
                boolean orderStatus = false;
                if(product != null && loggedInCustomer != null){
                    orderStatus = Order.placeOrder(loggedInCustomer, product);
                }
                if(orderStatus) {
                    //
                    showDialogue("Order Successful");
                }else{
                    //
                    showDialogue("Order Unsuccessful");
                }
            }
        });

        GridPane footer = new GridPane();
        footer.setTranslateY(headerLine+height);
        footer.add(buyNewButton, 0, 0);

        return footer;
    }

    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width+2*headerLine, height);

        bodyPane = new Pane();
        bodyPane.setPrefSize(width, height);
        bodyPane.setTranslateY(headerLine);
        bodyPane.setTranslateX(10);

        bodyPane.getChildren().add(loginPage());

        root.getChildren().addAll(headerBar(),
//                loginPage(),
//                productList.getAllProducts(),
                bodyPane,
                footerBar()
        );
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}