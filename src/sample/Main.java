package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.ChatServer.Lancia_Server;
import sample.view.LoginController;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane homeLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;

       this.primaryStage.setTitle("MeetFly Application");
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(Main.class.getResource("view/Login.fxml"));

       homeLayout = loader.load();
       Scene scene = new Scene(homeLayout);
       this.primaryStage.setScene(scene);

        LoginController log = loader.getController();
        log.initLog(this);

        this.primaryStage.show();
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public AnchorPane getHomeLayout() {
        return homeLayout;
    }

    public static void main(String[] args)  {
        Lancia_Server.main( args );
        launch(args);}
}
