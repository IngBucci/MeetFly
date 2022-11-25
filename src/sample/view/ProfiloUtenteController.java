package sample.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;
import sample.mysql.DAOException;
import sample.mysql.Utenti;
import sample.mysql.UtentiMySQL;
import sample.util.DateUtil;

import java.io.IOException;

public class ProfiloUtenteController {

    private Utenti utente;
    private Main main;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane bodyLayout;
    private String sesso;

    public void initProfiloUtente (Utenti ut, Main m){
        main = m;
        utente = ut;
        primaryStage = main.getPrimaryStage();
        bodyLayout = main.getHomeLayout();

        System.out.println( utente.getPassword() );
        nome.setText(utente.getNome());
        cognome.setText(utente.getCognome());
        email.setText(utente.getEmail());
        dataNascita.setText(utente.getData());
        password.setText(utente.getPassword());
        telefono.setText(utente.getTelefono());
        username.setText(utente.getNickname());
        età.setText(utente.getEtà());
        //sesso.setText(utente.getSesso());
        if (utente.getSesso().equals("Uomo")){
            uomoBtn.setSelected(true);
        }else{
            donnaBtn.setSelected(true);
        }
    };


    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField nome;
    @FXML
    private JFXTextField cognome;
    @FXML
    private JFXTextField dataNascita;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField password;
    @FXML
    private JFXTextField telefono;
    @FXML
    private JFXTextField età;

    @FXML
    private JFXCheckBox uomoBtn;
    @FXML
    private JFXCheckBox donnaBtn;
    @FXML
    private JFXButton salvaBtn;

    @FXML
    void pressedModifica(ActionEvent event) throws IOException {
        username.setDisable(false);
        nome.setDisable(false);
        cognome.setDisable(false);
        dataNascita.setDisable(false);
        email.setDisable(false);
        password.setDisable(false);
        telefono.setDisable(false);
        età.setDisable(false);
        uomoBtn.setDisable(false);
        donnaBtn.setDisable(false);
        salvaBtn.setDisable(false);
    }

    @FXML
    void pressedDonna(ActionEvent event) {
           if (donnaBtn.isSelected()) {
                uomoBtn.setSelected(false);
                sesso = donnaBtn.getText();
            }
        }

        @FXML
        void pressedUomo(ActionEvent event) {
            if (uomoBtn.isSelected()) {
                donnaBtn.setSelected(false);
                sesso = uomoBtn.getText();
            }
        }

        @FXML
        void pressedSalva(ActionEvent event) throws IOException, DAOException {

            Window conferma = salvaBtn.getScene().getWindow();
            UtentiMySQL jdbc = new UtentiMySQL();

            utente.setNome(nome.getText());
            utente.setCognome(cognome.getText());
            utente.setEmail(email.getText());
            utente.setData(dataNascita.getText());
            utente.setPassword(password.getText());
            utente.setNickname(username.getText());
            utente.setTelefono(telefono.getText());
            utente.setEtà(età.getText());
            utente.setSesso(sesso);

            if (campiVuoti() != false) {
                if (password.getText().length() < 8 || !controlloPassword(password.getText())) {
                    Notifiche.showAlert(Alert.AlertType.ERROR, conferma, "Errore!!", "La password deve contenere almeno 8 caratteri, una lettera maiuscola e un numero");
                    return;
                }
                jdbc.update(utente);

                username.setDisable(true);
                nome.setDisable(true);
                cognome.setDisable(true);
                dataNascita.setDisable(true);
                email.setDisable(true);
                password.setDisable(true);
                telefono.setDisable(true);
                età.setDisable(true);
                uomoBtn.setDisable(true);
                donnaBtn.setDisable(true);


/*
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/MenuLayout.fxml"));
                rootLayout = loader.load();

                MenuLayoutController MenuContr = loader.getController();
                MenuContr.initMenu(utente, main);

                FXMLLoader loader2 = new FXMLLoader();
                loader2.setLocation(Main.class.getResource("view/ProfiloUtente.fxml"));
                bodyLayout = (AnchorPane) loader2.load();
                rootLayout.setCenter(bodyLayout);

                ProfiloUtenteController profCont = loader2.getController();
                profCont.initProfiloUtente(utente, main);

                Scene scene = new Scene(rootLayout);
                primaryStage.setScene(scene);

 */
            }
        }

        private boolean campiVuoti(){
            Window conferma = salvaBtn.getScene().getWindow();
            if (nome.getText().isEmpty()) {
                Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci il nome." );
                return false;
            }
            if (cognome.getText().isEmpty()) {
                Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci il cognome." );
                return false;
            }
            if (dataNascita.getText().isEmpty()) {
                Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci la data di nascita." );
                return false;
            }
            if (!DateUtil.validDate( dataNascita.getText() )) {
                Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Data inserita non è valida, riprova con il formato dd-mm-yyyy!" );
                return false;
            }
            if (email.getText().isEmpty()) {
                Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci l'email." );
                return false;
            }
            if (password.getText().isEmpty()) {
                Notifiche.showAlert( Alert.AlertType.ERROR, conferma, "Errore!", "Inserisci la password." );
                return false;
            }
            if (username.getText().isEmpty()){
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
            return true;
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

    }
/*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MenuLayout.fxml"));
        rootLayout = loader.load();

        MenuLayoutController MenuContr = loader.getController();
        MenuContr.initMenu(utente, main);

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(Main.class.getResource("view/ModificaUtente.fxml"));
        bodyLayout =(AnchorPane)loader2.load();
        rootLayout.setCenter( bodyLayout );

        ModUtenteController modProfilo = loader2.getController();
        modProfilo.initModifica(utente,main);


        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
    }

 */

