package sample.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Main;
import sample.mysql.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ToolbarController {

    private FXMLLoader loader;
    private Utenti utente;
    private Main main;
    private Voli voli;
    private Stage primaryStage;
    private BorderPane menuLayout;
    private BorderPane rootLayout;
    private Stage stage;
    private AnchorPane bodyLayout;


    public void initDrawer(FXMLLoader loader, Utenti utente, Main main){
        this.loader = loader;
        this.main = main;
        this.utente = utente;
        stage = main.getPrimaryStage();
        bodyLayout = main.getHomeLayout();

        user.setText(utente.getNickname()+ "\n" + utente.getNome() +" "+ utente.getCognome());
    }


    @FXML
    private JFXButton user;

    @FXML
    void home(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource("view/MenuLayout.fxml"));
        rootLayout = loader.load();

        MenuLayoutController MenuContr = loader.getController();
        MenuContr.initMenu(utente, main);


        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(Main.class.getResource("view/Home.fxml"));
        bodyLayout = (AnchorPane) loader2.load();
        rootLayout.setCenter( bodyLayout );

        HomeController HomeContr = loader2.getController();
        HomeContr.initHome(utente,main,voli);


        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);

    }

    @FXML
    void logout(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/Login.fxml"));
        bodyLayout = loader.load();

        Scene scene = new Scene(bodyLayout);
        stage.setScene(scene);

        LoginController log = loader.getController();
        log.initLog(main);

    }

    @FXML
    void setting(ActionEvent event) {

    }

    @FXML
    public void profilo(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MenuLayout.fxml"));
        rootLayout = loader.load();

        MenuLayoutController MenuContr = loader.getController();
        MenuContr.initMenu(utente, main);


        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(Main.class.getResource("view/ProfiloUtente.fxml"));
        bodyLayout = loader2.load();
        rootLayout.setCenter( bodyLayout );

        ProfiloUtenteController profCont = loader2.getController();
        profCont.initProfiloUtente(utente,main);

        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
    }
}
