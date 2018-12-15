package TamaApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TamaMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("starting start");

        Parent root = FXMLLoader.load(getClass().getResource("/tama.fxml"));
        System.out.println(root);

        stage.setTitle("Tamagotchu v0.1.0");
        System.out.println(stage.getTitle());
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
        System.out.println("stage initialized");


    }
}