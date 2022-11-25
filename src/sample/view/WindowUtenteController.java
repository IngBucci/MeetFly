package sample.view;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.ChatClient.Inserisci_Nick;
import sample.Main;
import sample.mysql.Utenti;
import sample.mysql.UtentiHasVoli;
import sample.mysql.UtentiHasVoliMYSQL;

import java.sql.SQLException;


public class WindowUtenteController {

    private UtentiHasVoli uhv;
    private Utenti utente;
    private UtentiHasVoliMYSQL utentiHasVoliMYSQL = new UtentiHasVoliMYSQL();
    private Stage primaryStage;
    private Main main;

    public void initWindow(Utenti utente, UtentiHasVoli utent, Stage primaryStage, Main main) {
        this.uhv = utent;
        this.utente = utente;
        this.primaryStage = primaryStage;
        nickname.setText(uhv.getNickname());
        indirizzo.setText(uhv.getIndirizzoDestinazione());
        this.main = main;
        sesso.setText(utente.getSesso());
    }

    @FXML
    private Label nickname;
    @FXML
    private Label indirizzo;
    @FXML
    private Label sesso;
    @FXML
    private JFXButton chat;
    @FXML
    private JFXButton mappaBtn;
    @FXML
    void pressedChat(ActionEvent event) {
        Inserisci_Nick nik = new Inserisci_Nick();
        nik.initNik(utente,main);
    }

    @FXML
    void pressedMappa(ActionEvent event) throws SQLException {
        Window window = primaryStage.getScene().getWindow();

        if(utentiHasVoliMYSQL.utenteRegistrato( utente.getId() )== null && utentiHasVoliMYSQL.inidirizzo( utente.getId() )==null){
            Notifiche.showAlert( Alert.AlertType.ERROR, window, "Errore!", "Nessun volo o indirizzo inserito");
        }else{
            Mappe map = new Mappe();
            map.initMappe(utente,uhv );
        }
    }
}
