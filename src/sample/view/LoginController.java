package sample.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;
import sample.mysql.Utenti;
import sample.mysql.UtentiMySQL;
import sample.mysql.Voli;

import java.io.IOException;
import java.sql.SQLException;



public class LoginController {


    private Voli voli;
    private Main main;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane bodyLayout;
    private AnchorPane registrazioneLayout;

    public void initLog (Main main){
        this.main = main;
        primaryStage = this.main.getPrimaryStage();
        bodyLayout = this.main.getHomeLayout();
    };

    @FXML
    private Button login_btn;
    @FXML
    private TextField email;
    @FXML
    private PasswordField pswd;

    @FXML
    void LoginPressed(ActionEvent event) throws SQLException, IOException {
        Window owner = login_btn.getScene().getWindow();

        if (email.getText().isEmpty()) {
            Notifiche.showAlert(Alert.AlertType.ERROR, owner, "Errore!!!",
                    "Il campo e-mail non può essere vuoto.");
            email.getStyleClass().add("credenziali-mancanti");
            pswd.getStyleClass().add("credenziali-mancanti");
            return;
        }
        if (pswd.getText().isEmpty()) {
            Notifiche.showAlert( Alert.AlertType.ERROR, owner, "Errore!!!",
                    "Il campo password non può essere vuoto");
            pswd.getStyleClass().add("credenziali-mancanti");
            email.getStyleClass().add("credenziali-mancanti");
            return;
        }

        String emailText = email.getText();
        String pswdText = pswd.getText();

        UtentiMySQL jdbc = new UtentiMySQL();
        //boolean flag = jdbc.select(emailText, pswdText);
        Utenti utente = jdbc.selectUtente(emailText,pswdText);


        if (utente != null) {
           // Notifiche.infoBox("Login avvenuto con successo!", null, "Congratulazioni");

            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Main.class.getResource("view/MenuLayout.fxml"));
            rootLayout = loader.load();

            MenuLayoutController menuContr = loader.getController();
            menuContr.initMenu(utente, main);


            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(Main.class.getResource("view/Home.fxml"));
            bodyLayout = (AnchorPane) loader2.load();
            rootLayout.setCenter( bodyLayout );

            HomeController HomeContr = loader2.getController();
            HomeContr.initHome(utente,main,voli);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
        }else {
            Notifiche.infoBox("E-mail o Password non corretti.", null, "Doh");
            email.getStyleClass().add("credenziali-sbagliate");
            pswd.getStyleClass().add("credenziali-sbagliate");
        }
    }


    @FXML
    void RegPressed(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource("view/Registrazione.fxml"));
        registrazioneLayout = loader.load();

        // Home View
        Scene scene = new Scene(registrazioneLayout);
        primaryStage.setScene(scene);

        RegistrazioneController reg = loader.getController();
        reg.initReg(main);
    }

    @FXML
    void recuperoPassword(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource("view/RecuperoPassword.fxml"));
        bodyLayout = (AnchorPane)loader.load();

        // Home View
        Scene scene = new Scene(bodyLayout);
        primaryStage.setScene(scene);

        RecuperoPasswordController recuperoPswd = loader.getController();
        recuperoPswd.initRecuperoPassword(main);
    }
}
