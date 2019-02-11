/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatwithserver;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author jiach
 */
public class ChatServer extends Application {
    
    DataInputStream fromClient=null;
    DataOutputStream toClient=null;
    @Override
    public void start(Stage primaryStage) {
        Pane pane=new Pane();
        Scene scene=new Scene(pane,500,440);
        TextArea serverText=new TextArea();
        TextArea clientText=new TextArea();
        Label serverTextLabel=new Label("Server");
        Label clientTextLabel=new Label("Client");
        
        clientText.setMaxSize(500,200);
        clientText.setMinSize(500,200);
        clientText.setLayoutY(20);
        clientTextLabel.setMaxSize(500, 20);
        clientTextLabel.setMinSize(500, 20);
        
        serverText.setMaxSize(500,200);
        serverText.setMinSize(500,200);
        serverText.setLayoutY(240);
        serverTextLabel.setMaxSize(500, 20);
        serverTextLabel.setMinSize(500, 20);
        serverTextLabel.setLayoutY(220);
        
        pane.getChildren().addAll(serverTextLabel,serverText,clientTextLabel,clientText);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        serverText.setOnKeyPressed(e -> {
            String serverT = serverText.getText();
           // String serverT;
            try {
                toClient.writeUTF(serverT);
                toClient.flush();
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            Socket socket = serverSocket.accept();
            fromClient = new DataInputStream(socket.getInputStream());
            toClient = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Server is dwon!");
        }
        
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("data reseved");
                    String clientT = fromClient.readUTF();
                    clientText.setText(clientT);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }).start();
        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
