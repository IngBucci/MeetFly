package sample.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.RandomStringUtils;
import sample.Main;
import sample.mysql.DAOException;
import sample.mysql.UtentiMySQL;
import sample.util.Email;

import javax.mail.MessagingException;

public class RecuperoPasswordController {

    private Main main;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane bodyLayout;

    public void initRecuperoPassword (Main main){
        this.main = main;
        primaryStage = this.main.getPrimaryStage();
        bodyLayout = this.main.getHomeLayout();
    };

    UtentiMySQL jdbc = new UtentiMySQL();

    @FXML
    private JFXTextField emailText;

    @FXML
    private JFXButton conferma;
    @FXML
    private JFXButton indietroBtn;




    @FXML
    private void pressedIndietro(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource("view/Login.fxml"));
        bodyLayout = loader.load();

        // Home View
        Scene scene = new Scene(bodyLayout);
        primaryStage.setScene(scene);

        LoginController log;
        log = loader.getController();
        log.initLog(main);
    }


    @FXML
    public void aggiornaPassword(javafx.event.ActionEvent actionEvent) throws DAOException, IOException, MessagingException {
        Window owner = conferma.getScene().getWindow();

        int length = 8;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        System.out.println("Password generata: \n"+ generatedString+"\n\n");

        if(jdbc.utenteRegistrato( emailText.getText())){

            jdbc.aggiornaPassword( emailText.getText(), generatedString );

            System.out.println( "Deve inviare la mail con la nuova password" );

            Email.sendEmail("smtp.gmail.com", "587", "meetflyapp@gmail.com", "30&Lodebnv",
                    emailText.getText(), "Benvenuto", "\n\nPassword aggiornata:\n"+generatedString);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Login.fxml"));
            bodyLayout = loader.load();

            // Home View
            Scene scene = new Scene(bodyLayout);
            primaryStage.setScene(scene);

            LoginController log = loader.getController();
            log.initLog(main);
            Notifiche.showAlert( Alert.AlertType.CONFIRMATION, owner, "Congratulazioni!",
                    "Password modificata con successo!!!");
        }
        else {
            Notifiche.showAlert( Alert.AlertType.ERROR, owner, "Errore!!!",
                    "Email non registrata!\n\nREGISTRATI!");
            emailText.getStyleClass().add("credenziali-sbagliate");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Login.fxml"));

            bodyLayout = loader.load();

            // Home View
            Scene scene = new Scene(bodyLayout);
            primaryStage.setScene(scene);

            LoginController log = loader.getController();
            log.initLog(main);
        }
    }
}
