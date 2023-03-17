package com.company;


import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.awt.*;



public class Main extends Application{

    private boolean isServer = true; //set true for you chatbox, false for friend chatbox

    private TextArea textbox = new TextArea();
    private NetworkConnection connection = isServer ? createServer() : createClient();

    private VBox createContent() {

        //send button for texts
        Button send = new Button();
        //title of button send
        send.setText("Send");
        //sets button size
        send.setPrefSize(80, 10);
        //sets button x axis
        send.setTranslateX(300);
        //sets button y axis
        send.setTranslateY(-10);

        //sets textbox height
        textbox.setPrefHeight(550);
        //creates new textfield input
        TextField input = new TextField();
        //makes textbox uneditable inside chat
        textbox.setEditable(false);

        //sets action event for button send
        send.setOnAction(evt -> {
            //sets title for friend and for sender
            String message = isServer ? "YOU: " : "FRIEND: ";
            //message is set equal to input and adds when new input is sent
            message += input.getText();
            //prints out message to console
            System.out.println(message);
            //clears input when finished
            input.clear();

            //appends textbox to message sent
            textbox.appendText(message + "\n");
            //prints message to console
            System.out.println(message);

            //try-catch
            try {
                connection.send(message);
            } catch (Exception e) {
                //if message not sent
                textbox.appendText("Error. Message not sent." + "\n");

            }
        });
        //creates Vbox named root
        VBox root = new VBox(20, textbox, input, send);
        //sets size
        root.setPrefSize(400,  500);
        //returns root
        return root;

    } //end parent

    //starts connection
    public void init() throws Exception {
        connection.startConnection();
    }

    //override
    @Override
    public void start(Stage primaryStage) {
        //sets scene
        primaryStage.setScene(new Scene(createContent()));
        //sets title
        primaryStage.setTitle("Chat");
        //shows scene
        primaryStage.show();
    } //end stage

    public void stop() throws Exception {
        //closes connection
        connection.closeConnection();
    }

    //creates server
    private Server createServer() {
        return new Server(55555, data -> {
            Platform.runLater(() -> {
                //appends textbox
                textbox.appendText(data.toString() + "\n");
            });
        });
    }

    //creates client
    private Client createClient() {
        //ip address
        return new Client("127.0.0.1", 55555, data -> {
            Platform.runLater(() -> {
                //appends textbox
                textbox.appendText(data.toString() + "\n");
            });
        });

    }
    public static void main(String[] args) {
        launch(args);
    }

} //end class
