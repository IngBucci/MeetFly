package sample.view;

import com.gluonhq.charm.glisten.control.Avatar;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import sample.mysql.Utenti;
import sample.mysql.Voli;

import java.io.IOException;


public class MenuLayoutController {

    private Voli voli;
    private Utenti utente;
    private Main m;
    private Stage stage;
    private BorderPane rootLayout;
    private AnchorPane bodyLayout;
    private VBox toolbar;


    public void initMenu (Utenti ut,Main main){
        utente = ut;
        m = main;
        stage = m.getPrimaryStage();
        bodyLayout = m.getHomeLayout();

        //nomeavatar.setText(utente.getNome() + " " + utente.getCognome());
    };

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;

    @FXML
    void pressedHamburger(MouseEvent event) throws IOException {

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(Main.class.getResource("view/Toolbar.fxml"));
        toolbar = loader2.load();

        drawer.setSidePane(toolbar);

        if (drawer.isClosed()){
            drawer.open();
            drawer.setMinWidth(200);

            ToolbarController toolbarCont = loader2.getController();
            toolbarCont.initDrawer(loader2,utente,m);

        }else{
            drawer.close();
            drawer.setMinWidth(0);
        }
    }
/*
    @FXML
    void pofiloUtente(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MenuLayout.fxml"));
        rootLayout = loader.load();

        MenuLayoutController MenuContr = loader.getController();
        MenuContr.initMenu(utente, m);


        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(Main.class.getResource("view/ProfiloUtente.fxml"));
        bodyLayout = loader2.load();
        rootLayout.setCenter( bodyLayout );

        ProfiloUtenteController profCont = loader2.getController();
        profCont.initProfiloUtente(utente,m);

        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
    }


    @FXML
    void home(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource("view/MenuLayout.fxml"));
        rootLayout = loader.load();

        MenuLayoutController MenuContr = loader.getController();
        MenuContr.initMenu(utente, m);


        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(Main.class.getResource("view/Home.fxml"));
        bodyLayout = (AnchorPane) loader2.load();
        rootLayout.setCenter( bodyLayout );

        HomeController HomeContr = loader2.getController();
        HomeContr.intiHome(utente,m,voli);


        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);

    }


    @FXML
    void logout(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/Login.fxml"));
        rootLayout = loader.load();

        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);

        LoginController log = loader.getController();
        log.initLog(m);

    }

    @FXML
    void feedback(ActionEvent event) {

    }

    @FXML
    void notifiche(ActionEvent event) {

    }


    @FXML
    void setting(ActionEvent event) {

    }

*/
}


