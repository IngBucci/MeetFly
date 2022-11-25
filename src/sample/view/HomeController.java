package sample.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.ChatClient.Inserisci_Nick;
import sample.Main;
import sample.mysql.Utenti;
import sample.mysql.UtentiHasVoli;
import sample.mysql.Voli;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;


public class HomeController {


    private Main m;
    private Voli voli;
    private Utenti utente;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane bodyLayout;

    public void initHome (Utenti ut, Main main, Voli voli){
        utente = ut;
        m = main;
        primaryStage = this.m.getPrimaryStage();
        bodyLayout = this.m.getHomeLayout();
        this.voli = voli;
    };

    @FXML
    private Button mappeBtn;
    @FXML
    private Button voliBtn;
    @FXML
    private Button chatBtn;

    @FXML
    void pressedAiuto(ActionEvent event) {

    }

    @FXML
    void pressedChat(ActionEvent event) {
        Inserisci_Nick nik = new Inserisci_Nick();
        nik.initNik(utente,m);
    }

    @FXML
    void pressedMappe(ActionEvent event) {
        Mappe mappa = new Mappe();
        mappa.mappe();
    }

    @FXML
    void pressedVoli(ActionEvent event) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MenuLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        MenuLayoutController MenuContr = loader.getController();
        MenuContr.initMenu(utente, m);


        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(Main.class.getResource("view/Voli.fxml"));
        bodyLayout = (AnchorPane) loader2.load();
        rootLayout.setCenter( bodyLayout );

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        VoliController logVolo = loader2.getController();
        logVolo.initVolo(m,utente);
    }
}
