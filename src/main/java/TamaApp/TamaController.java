package TamaApp;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TamaController implements Initializable {

    @FXML
    Label name;
    @FXML
    Label healthBar;

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

    @Override
    public void initialize(URL location, ResourceBundle resources){
        // set name label to name of TamaFriend
        name.setText(tamaFriend.getName());     // will interfere with prompting user for name; fix later
        System.out.println(name);

        // create ttlHealth label that will update as ttlHealth changes
        healthBar.setText("Overall health is: " + tamaFriend.getTtlHealth());
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(healthBar.textProperty(),tamaFriend.ttlHealthProperty(), converter);
        System.out.println("health bar created");

        // display tama sprite, currently static
        tamaImage = new ImageView();
        tamaImage.setImage(new Image("images/tama.png"));

        // add button functionality!!!

       // switch on user selection, call related TamaActions method
        Scanner in = new Scanner(System.in);
        int selection;
        boolean valid = false;
        boolean done = false;
        boolean alive = true;

        System.out.println("~~~~~Welcome to Tamagotchu!~~~~~");
        System.out.println("Please enter a name for your Tama: ");
        tamaFriend.setName(in.nextLine());
        System.out.printf("Your Tama's name is %s!\n", tamaFriend.getName());

        ScheduledExecutorService statDecay = Executors.newSingleThreadScheduledExecutor();
        statDecay.scheduleAtFixedRate(tamaFriend, 10, 10, TimeUnit.SECONDS);

        do {
            // print menu of options to the console for use selection
            System.out.println("What would you like to do?");
            printMenu();

            try {
                selection = Integer.parseInt(in.nextLine());

                switch (selection) {
                    case 1:
                        tamaFriend.feed();
                        break;
                    case 2:
                        tamaFriend.playWith();
                        break;
                    case 3:
                        System.out.println(tamaFriend.getStats());
                        break;
                    case 4:
                        done = true;
                        break;
                    default:
                        throw new NumberFormatException();
                }

                valid = true;
                tamaFriend.increaseAge();
                alive = tamaFriend.isAlive();

                if (!alive) {
                    System.out.printf("\nOh no! Your tama died of %s... RIP %s! \n",
                                        tamaFriend.getCauseOfDeath(), tamaFriend.getName());
                }
            }
            catch(NumberFormatException e) {
                System.out.println("Your input must be 1, 2, 3, or 4. Please try again.");
            }
        } while((!valid || !done) && alive);

        System.out.println("Thanks for playing!");
        // statDecay.shutdown();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1: Feed");
        System.out.println("2: Play");
        System.out.println("3: Check Stats");
        System.out.println("4: Quit");
        System.out.print("Enter your selection: ");
    }
}

