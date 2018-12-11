package tamaLogic;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TamaDriver {

    public static void main(String[] args) {
        // create new Tama
        Tamagotchu tamaFriend = new Tamagotchu();
        TamaActions action = new TamaActions();

        // switch on user selection, call related TamaActions method
        Scanner in = new Scanner(System.in);
        int selection = 0;
        boolean valid = false;
        boolean done = false;
        boolean alive = true;

        System.out.println("~~~~~Welcome to Tamagotchu!~~~~~");
        System.out.println("Please enter a name for your Tama: ");
        tamaFriend.setName(in.nextLine());
        System.out.printf("Your Tama's name is %s!\n", tamaFriend.getName());

        // make stats decay
        ScheduledExecutorService statDecay = Executors.newSingleThreadScheduledExecutor();
        statDecay.scheduleAtFixedRate(tamaFriend, 10, 10, TimeUnit.SECONDS);

        do {
            // print menu of options to the console for use selection
            System.out.println("What would you like to do?");
            action.printMenu();

            try {
                selection = Integer.parseInt(in.nextLine());

                switch (selection) {
                    case 1:
                        action.feed(tamaFriend);
                        break;
                    case 2:
                        action.playWith(tamaFriend);
                        break;
                    case 3:
                        action.getStats(tamaFriend);
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

                if(!alive) {
                    System.out.printf("\nOh no! Your tama died of %s... RIP %s! \n",
                                        tamaFriend.getCauseOfDeath(), tamaFriend.getName());
                }
            }
            catch(NumberFormatException e) {
                System.out.println("Your input must be 1, 2, 3, or 4. Please try again.");
            }
        } while((!valid || !done) && alive);

        System.out.println("Thanks for playing!");
        statDecay.shutdown();
    }
}