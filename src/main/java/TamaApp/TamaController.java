package TamaApp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TamaController implements Initializable {

    // constants
    int SHORT_MESSAGE = 3;
    int LONG_MESSAGE = 10;

    @FXML
    Label name;
    @FXML
    Label healthBar;
    @FXML
    Label message;

    @FXML
    ImageView tamaImage;

    @FXML
    Button feedButton;
    @FXML
    Button playButton;
    @FXML
    Button statsButton;
    @FXML
    Button exitButton;

    // create new Tama
    public static Tamagotchu tamaFriend = new Tamagotchu();

    private ScheduledExecutorService statDecay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set name label to name of TamaFriend
        name.setText(tamaFriend.getName());     // will interfere with prompting user for name; fix later

        // create ttlHealth label that will update as ttlHealth changes
        healthBar.setText("Overall health is: " + tamaFriend.getTtlHealth());
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(healthBar.textProperty(), tamaFriend.ttlHealthProperty(), converter);

        // display tama sprite, currently static
        //tamaImage = new ImageView();
        //tamaImage.setImage(new Image("images/tama.png"));

        statDecay = Executors.newSingleThreadScheduledExecutor();
        statDecay.scheduleAtFixedRate(tamaFriend, 10, 10, TimeUnit.SECONDS);
    }

    public void handleFeed() {
        tamaFriend.feed();
        displayMessage("You fed your Tamagotchu!", SHORT_MESSAGE);
    }

    public void handlePlay() {
        tamaFriend.playWith();
        displayMessage("You played with your Tamagotchu!", SHORT_MESSAGE);
    }

    public void handleStats() {
        //stage.setScene();

        displayMessage(tamaFriend.getStats(), LONG_MESSAGE);
    }

    public void displayMessage(String messageText, int duration) {
        message.setText(messageText);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(duration), evt -> message.setVisible(false)));
        timeline.play();
        message.setVisible(true);
    }

    public void handleExit() {
        System.out.println("Thanks for playing!");
        statDecay.shutdown();
        Platform.exit();
    }
}

//        System.out.println("~~~~~Welcome to Tamagotchu!~~~~~");
//        System.out.println("Please enter a name for your Tama: ");
//        tamaFriend.setName(in.nextLine());
//        System.out.printf("Your Tama's name is %s!\n", tamaFriend.getName());
//
//
//
//                tamaFriend.increaseAge();
//                alive = tamaFriend.isAlive();
//
//                if (!alive) {
//                    System.out.printf("\nOh no! Your tama died of %s... RIP %s! \n",
//                                        tamaFriend.getCauseOfDeath(), tamaFriend.getName());
//                }
//
//}

