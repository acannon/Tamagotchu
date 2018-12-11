package tamaLogic;

class TamaActions {

    void printMenu() {
        System.out.println();
        System.out.println("1: Feed");
        System.out.println("2: Play");
        System.out.println("3: Check Stats");
        System.out.println("4: Quit");
        System.out.print("Enter your selection: ");
    }

    void feed(Tamagotchu tama) {
        // eventually parameter can be based on type of food
        tama.adjustFull(20);
        System.out.println("You fed your Tamagotchu!");
    }

    void playWith(Tamagotchu tama) {
        tama.adjustFun(20);
        System.out.println("You played with your Tamagotchu!");
    }

    void getStats(Tamagotchu tama) {
        System.out.println(tama.toString());
    }



}