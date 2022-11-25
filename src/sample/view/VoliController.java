package sample.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.controlsfx.control.textfield.TextFields;
import sample.Main;
import sample.mysql.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import sample.mysql.Voli;
import sample.mysql.VoliMySQL;


public class VoliController {

    private Main main;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane bodyLayout;
    private Voli voli;
    private ArrayList idVoli;
    private VoliMySQL jdbc = new VoliMySQL();
    private UtentiHasVoliMYSQL uhvJdbc = new UtentiHasVoliMYSQL();
    private UtentiHasVoli uhv;
    private Utenti utente;

    private String mezzo;

    public void initVolo(Main main, Utenti utente) throws SQLException {
        this.main = main;
        primaryStage = this.main.getPrimaryStage();
        bodyLayout = this.main.getHomeLayout();
        idVoli = jdbc.select();
        TextFields.bindAutoCompletion(codiceVolo, idVoli);
        this.utente = utente;
    }


    @FXML
    private JFXTextField aeroportArr;
    @FXML
    private JFXTextField aeroportoPart;
    @FXML
    private JFXTextField oraPart;
    @FXML
    private JFXTextField oraArr;
    @FXML
    private JFXTextField viaDestinazione;
    @FXML
    private JFXTextField cittaDestinazione;
    @FXML
    private TextField codiceVolo;
    @FXML
    private TableView<UtentiHasVoli> tabellaListaUtenti;
    @FXML
    private TableColumn<UtentiHasVoli, String> personeVoloTabella;
    @FXML
    private TableColumn<UtentiHasVoli, String> indirizzoTabella;
    @FXML
    private TableColumn<UtentiHasVoli, String> cittàTabella;
    @FXML
    private TableColumn<UtentiHasVoli, String> mezzoTabella;
    @FXML
    private JFXButton aggiungiBtn;
    @FXML
    private JFXButton eliminaBtn;
    @FXML
    private JFXCheckBox auto;
    @FXML
    private JFXCheckBox bus;
    @FXML
    private JFXCheckBox moto;
    @FXML
    private JFXCheckBox metro;
    @FXML
    private JFXCheckBox taxi;
    @FXML
    private JFXCheckBox indeciso;
    @FXML
    private JFXCheckBox cerco;


    @FXML
    public void codiceVolo(javafx.event.ActionEvent actionEvent) throws SQLException {
        Window window = aggiungiBtn.getScene().getWindow();
        if (codiceVolo.getText().isEmpty()) {
            Notifiche.showAlert(Alert.AlertType.ERROR, window, "Errore!", "Inserisci un codice volo");
            return;
        } else {
            aggiornaTabella(codiceVolo.getText());
            //tabellaListaUtenti.setDisable( true );
        }
    }

    public void aggiungiUtente(ActionEvent actionEvent) throws DAOException, SQLException {
        Window window = codiceVolo.getScene().getWindow();
        //Si aggiunge l'utente al db utenti has voli:
        String codice = uhvJdbc.utenteRegistrato(utente.getId());
        if (!codiceVolo.getText().isEmpty()) {

            if (codice == null && codice != codiceVolo.getText()) {

                uhvJdbc.insert(utente, codiceVolo.getText());
                Notifiche.infoBox("Ti sei iscritto al volo " + codiceVolo.getText() + "!", null, "Iscrizione");
                //Si aggiorna la tabella:
                aggiornaTabella(codiceVolo.getText());
                return;
            } else {
                if(codice != codiceVolo.getText()){
                Notifiche.showAlert(Alert.AlertType.ERROR, window, "Errore!", "Sei già registrato sul volo " + codice + "!");
                //Si aggiorna la tabella:
                aggiornaTabella(codice);
                return;
                }else{
                    aggiornaTabella( codice );
                }
            }
        } else {
            Notifiche.showAlert(Alert.AlertType.ERROR, window, "Errore!", "Nessun codice volo selezionato!");
        }
    }

    private void aggiornaTabella(String codVolo) throws SQLException {
        Window window = codiceVolo.getScene().getWindow();
        String codice = uhvJdbc.utenteRegistrato(utente.getId());
            List<UtentiHasVoli> lista = uhvJdbc.listaUtenti( codVolo, cittaDestinazione.getText(), viaDestinazione.getText(), utente );

            personeVoloTabella.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getNickname() ) {
            } );
            indirizzoTabella.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getIndirizzoDestinazione() ) );
            cittàTabella.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getCitta() ) );
            mezzoTabella.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getMezzo() ) );

            ObservableList<UtentiHasVoli> listaUtenti = FXCollections.observableArrayList( lista );
            tabellaListaUtenti.setItems( listaUtenti );

            tabellaListaUtenti.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        try {
                            showUtentiDetails( newValue );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } );


            Voli volo = jdbc.selectVolo( codVolo );
            String aereoP = jdbc.selectAeroporto( volo.getAeroportoPartenza() );
            String aereoA = jdbc.selectAeroporto( volo.getAeroportoArrivo() );
            if (aereoA == null) {
                aereoA = volo.getAeroportoArrivo();
            }
            if (aereoP == null) {
                aereoP = volo.getAeroportoPartenza();
            }

            codiceVolo.setText( volo.getId() );
            aeroportoPart.setText( aereoP );
            aeroportArr.setText( aereoA );
            oraPart.setText( volo.getOrarioPartenza() );
            oraArr.setText( volo.getOrarioArrivo() );

    }

    @FXML
    public void aggiungiDestinazione(ActionEvent actionEvent) throws DAOException, SQLException {
        Window window = codiceVolo.getScene().getWindow();
        String codice = uhvJdbc.utenteRegistrato(utente.getId());

        if (!(codice == null)){
            if(!viaDestinazione.getText().isEmpty() && !cittaDestinazione.getText().isEmpty()) {
                uhvJdbc.update( utente.getId(), viaDestinazione.getText(), cittaDestinazione.getText(), mezzo );
                Notifiche.infoBox( "Destinazione aggiunta correttamente.", null, "Congratulazioni" );
                aggiornaTabella( codiceVolo.getText() );
            }else {
                Notifiche.showAlert(Alert.AlertType.ERROR, window, "Errore!", "Non hai inserito nessuna via o indirizzo");
                return;
            }
        }else{
            Notifiche.showAlert(Alert.AlertType.ERROR, window, "Errore!", "Non sei iscritto a nessun volo");
            return;
        }
    }

    @FXML
    public void eliminaUtente(ActionEvent actionEvent) throws DAOException, SQLException {
        Window window = aggiungiBtn.getScene().getWindow();
        if (!codiceVolo.getText().isEmpty()) {
            uhvJdbc.delete(utente.getId());
            Notifiche.infoBox("Ti sei disiscritto dal volo!", null, "Cancellazione");
            aggiornaTabella(codiceVolo.getText());
        } else {
            Notifiche.showAlert(Alert.AlertType.ERROR, window, "Errore!", "Nessun codice volo selezionato!");
        }
    }


    @FXML
    void pressAuto(ActionEvent event) {
        if (auto.isSelected()) {
            moto.setSelected(false);
            bus.setSelected(false);
            taxi.setSelected(false);
            metro.setSelected(false);
            indeciso.setSelected(false);
            cerco.setSelected(false);

            mezzo = auto.getText();
        }
    }

    @FXML
    void pressBus(ActionEvent event) {
        if (bus.isSelected()) {
            moto.setSelected(false);
            auto.setSelected(false);
            taxi.setSelected(false);
            metro.setSelected(false);
            indeciso.setSelected(false);
            cerco.setSelected(false);

            mezzo = bus.getText();
        }
    }

    @FXML
    void pressCerco(ActionEvent event) {
        if (cerco.isSelected()) {
            moto.setSelected(false);
            bus.setSelected(false);
            taxi.setSelected(false);
            metro.setSelected(false);
            indeciso.setSelected(false);
            auto.setSelected(false);

            mezzo = cerco.getText();
        }
    }

    @FXML
    void pressIndeciso(ActionEvent event) {
        if (indeciso.isSelected()) {
            moto.setSelected(false);
            bus.setSelected(false);
            taxi.setSelected(false);
            metro.setSelected(false);
            auto.setSelected(false);
            cerco.setSelected(false);

            mezzo = indeciso.getText();
        }
    }

    @FXML
    void pressMetro(ActionEvent event) {
        if (metro.isSelected()) {
            moto.setSelected(false);
            bus.setSelected(false);
            taxi.setSelected(false);
            auto.setSelected(false);
            indeciso.setSelected(false);
            cerco.setSelected(false);

            mezzo = metro.getText();
        }
    }

    @FXML
    void pressMoto(ActionEvent event) {
        if (moto.isSelected()) {
            auto.setSelected(false);
            bus.setSelected(false);
            taxi.setSelected(false);
            metro.setSelected(false);
            indeciso.setSelected(false);
            cerco.setSelected(false);

            mezzo = moto.getText();
        }
    }

    @FXML
    void pressTaxi(ActionEvent event) {
        if (taxi.isSelected()) {
            moto.setSelected(false);
            bus.setSelected(false);
            auto.setSelected(false);
            metro.setSelected(false);
            indeciso.setSelected(false);
            cerco.setSelected(false);

            mezzo = taxi.getText();
        }
    }

/*
    public static void loadWindow(URL loc, String title, Stage parentStage){
        try{
            Parent parent = FXMLLoader.load(loc);
            Stage stage = null;
            if (parentStage != null){
                stage = parentStage;
            }else{
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */

    private void showUtentiDetails(UtentiHasVoli utenti) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/windowUtente.fxml"));
        bodyLayout = loader.load();

        Stage dialog = new Stage();
        dialog.setTitle("Profilo Utente");
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(primaryStage);

        Scene scene = new Scene(bodyLayout);
        dialog.setScene(scene);

        WindowUtenteController window = loader.getController();
        window.initWindow(utente,utenti,primaryStage,main);

        dialog.showAndWait();
    }


}

