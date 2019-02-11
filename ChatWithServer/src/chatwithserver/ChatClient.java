/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatwithserver;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author jiach
 */
public class ChatClient extends Application {
    DataInputStream fromServer=null;
    DataOutputStream toServer=null;
    @Override
    public void start(Stage primaryStage) {
        Pane pane=new Pane();
        Scene scene=new Scene(pane,500,440);
        TextArea serverText=new TextArea();
        TextArea clientText=new TextArea();
        Label serverTextLabel=new Label("Server");
        Label clientTextLabel=new Label("Client");
        
        serverText.setMaxSize(500,200);
        serverText.setMinSize(500,200);
        serverText.setLayoutY(20);
        serverTextLabel.setMaxSize(500, 20);
        serverTextLabel.setMinSize(500, 20);
        
        clientText.setMaxSize(500,200);
        clientText.setMinSize(500,200);
        clientText.setLayoutY(240);
        clientTextLabel.setMaxSize(500, 20);
        clientTextLabel.setMinSize(500, 20);
        clientTextLabel.setLayoutY(220);
        
        pane.getChildren().addAll(serverTextLabel,serverText,clientTextLabel,clientText);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        clientText.setOnKeyPressed(e -> {
            String clientT = clientText.getText();
           // String serverT;
            try {
                toServer.writeUTF(clientT);
                toServer.flush();
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        try {
            Socket socket = new Socket("localhost", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Fail to connet to server!");
        }
       
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("data reseved");
                    String serverT = fromServer.readUTF();
                    serverText.setText(serverT);
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
