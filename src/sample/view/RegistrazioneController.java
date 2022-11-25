package sample.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import sample.mysql.DAOException;
import sample.mysql.Utenti;
import sample.mysql.UtentiMySQL;
import sample.util.DateUtil;
import sample.util.Email;

import javax.mail.MessagingException;
import java.io.IOException;

public class RegistrazioneController {

    private Main main;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane bodyLayout;

    private String sesso;

    public void initReg (Main main){
        this.main = main;
        primaryStage = this.main.getPrimaryStage();
        bodyLayout = this.main.getHomeLayout();
    };

    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField data_nascita;
    @FXML
    private TextField email;
    @FXML
    private PasswordField confPassword;
    @FXML
    private PasswordField confPassword1;
    @FXML
    private TextField nickname;
    @FXML
    private TextField telefono;
    @FXML
    private TextField età;
    @FXML
    private JFXButton btn_indietro;
    @FXML
    private JFXButton btn_confirm;
    @FXML
    private JFXCheckBox uomoBtn;
    @FXML
    private JFXCheckBox donnaBtn;


    @FXML
    void pressedDonna(ActionEvent event) {
        if (donnaBtn.isSelected()){
            uomoBtn.setSelected(false);
            sesso = donnaBtn.getText();
            //System.out.println(donnaBtn.getText());
        }
    }

    @FXML
    void pressedUomo(ActionEvent event) {
        if (uomoBtn.isSelected()){
            donnaBtn.setSelected(false);
            sesso = uomoBtn.getText();
        }
    }


    @FXML
    private void pressedConfirm(ActionEvent event) throws IOException {
        Window conferma = btn_confirm.getScene().getWindow();
        Utenti utente = new Utenti();
        utente.setNome( nome.getText() );
        utente.setCognome( cognome.getText() );
        utente.setData( data_nascita.getText() );
        utente.setEmail( email.getText() );
        utente.setPassword( confPassword.getText() );
        utente.setNickname(nickname.getText());
        utente.setTelefono(telefono.getText());
        utente.setEtà(età.getText());
        utente.setSesso( sesso );

        UtentiMySQL jdbc = new UtentiMySQL();
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(campiVuoti()!= false ){
        if(jdbc.utenteRegistrato( email.getText())){
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Email già registrata!\n\nInserisci una nuova mail." );
            email.setText( "" );
            return;
        }else {
            if (confPassword.getText().length()<8||!controlloPassword(confPassword.getText())){
                Notifiche.showAlert(Alert.AlertType.ERROR,conferma,"Errore!!","La password deve contenere almeno 8 caratteri, una lettera maiuscola e un numero");
                return;
            }else {
                if (confPassword.getText().equals(confPassword1.getText())){
                    try {
                        Email.sendEmail("smtp.gmail.com", "587", "meetflyapp@gmail.com", "30&Lodebnv",
                                utente.getEmail(), "Benvenuto", "\n\nBenvenuto nella MeetFly APP\n\n");
                    } catch (MessagingException ex) {
                        Notifiche.showAlert(Alert.AlertType.ERROR, conferma, "Errore!", "Email inserita non valida!");
                        email.setText( "" );
                        return;
                    }
                    try {
                        jdbc.insert(utente);
                    } catch (DAOException ex) {
                        ex.printStackTrace();
                    }
                    Notifiche.showAlert(Alert.AlertType.CONFIRMATION, conferma, "Congratulazioni!", "Registrazione avvenuta con successo. \nBenvenuto " + nome.getText() + "!\n");
                    pressedIndietro(event);

                }else{
                    Notifiche.showAlert(Alert.AlertType.ERROR,conferma, "Errore!!", "Hai inserito 2 password diverse");
                    return;
                }
            }
        }
        return;
    }
    }


    public boolean controlloPassword(String password){
     int maiuscole = 0;
     int numeri = 0;
     char c;

     for (int i = 0; i<password.length(); i++){
         c = password.charAt(i);
         if (Character.isUpperCase(c)) {
             maiuscole++;
         }
         if (Character.isDigit(c)){
             numeri++;
         }
     }
     if (maiuscole>0 && numeri>0){
         return true;
     }
     return false;
    }

    private boolean campiVuoti(){
        Window conferma = btn_confirm.getScene().getWindow();
        if (nome.getText().isEmpty()) {
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci il nome." );
            return false;
        }
        if (cognome.getText().isEmpty()) {
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci il cognome." );
            return false;
        }
        if (data_nascita.getText().isEmpty()) {
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci la data di nascita." );
            return false;
        }
        if (!DateUtil.validDate( data_nascita.getText() )) {
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Data inserita non è valida, riprova con il formato dd-mm-yyyy!" );
            return false;
        }
        if (email.getText().isEmpty()) {
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci l'email." );
            return false;
        }
        if (confPassword.getText().isEmpty()) {
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci la password." );
            return false;
        }
        if (confPassword1.getText().isEmpty()) {
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci la password di conferma." );
            return false;
        }
        if (nickname.getText().isEmpty()){
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci un Nickname" );
            return false;
        }
        if (telefono.getText().isEmpty()){
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci un Numero di Telefono" );
            return false;
        }
        if (età.getText().isEmpty()){
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci la tua Età" );
            return false;
        }
        if (sesso.isEmpty()){
            Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Seleziona il tuo Sesso" );
            return false;
        }
        return true;
    }

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
}
