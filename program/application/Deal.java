package application;

import java.util.*;

public class Deal {
    private Map<Integer, Double> boxes;
    private Map<Integer, Double> eliminatedBoxes;
    private Map<Integer, Integer> rounds;
    private int chosenBox, firstChosenBox;
    private Scanner s = new Scanner(System.in);
    private String userInput = "";
    private final double[] VALUES = {0.01, 1, 5, 10, 25, 50, 75, 100, 200, 300, 400, 500, 750, 1000, 5000, 10000, 25000, 50000, 75000, 100000, 200000, 300000, 400000, 500000, 750000, 1000000};

    private final int[] BOXES_PER_ROUND = {6, 5, 4, 3, 2, 1, 1, 1, 1};
    private int currentRound;
    private boolean gameEnded;

    public Deal() {
        boxes = new HashMap<>();
        eliminatedBoxes = new HashMap<>();
        rounds = new HashMap<>();
        currentRound = 1;
        gameEnded = false;

        for (int i = 0; i < VALUES.length; i++) {
            boxes.put(i + 1, VALUES[i]);
        }

        for (int i = 0; i < BOXES_PER_ROUND.length; i++) {
            rounds.put(i + 1, BOXES_PER_ROUND[i]);
        }
    }

    public void startGame() {
        printIntro();
        do {
            System.out.print("Choose your box from 1-26: ");
            try {
                firstChosenBox = s.nextInt();
            } catch (InputMismatchException e) {
                s.nextLine();
                System.out.println("Please enter a number between 1 and 26!");
            }
        } while (firstChosenBox < 1 || firstChosenBox > 26);

        eliminateBox(firstChosenBox);
    }

    public void playRound() {
        for (int i = 0; i < rounds.get(currentRound); i++) {
            printAvailableBoxes();
            System.out.print("Choose your box to open: ");

            do {
                try {
                    chosenBox = s.nextInt();
                } catch (InputMismatchException e) {
                    s.nextLine();
                    System.out.println("Please enter only numbers!");
                }
                s.nextLine(); //Flush input buffer
                if (!boxes.containsKey(chosenBox)) {
                    System.out.println("Box not available. Available boxes are:");
                    printAvailableBoxes();
                }
            } while (!boxes.containsKey(chosenBox));

            eliminateBox(chosenBox);
            System.out.println("Value of box no. " + chosenBox + ": " +
                    eliminatedBoxes.get(chosenBox).doubleValue() + "$");
        }

        int bankOffer = calculateBankOffer();

        System.out.println("******************************");
        System.out.println("Round " + currentRound + " is over.");
        System.out.println("******************************");

        if (currentRound <= 9) {
            System.out.println("The bank offers you " + bankOffer);
            do {
                System.out.print("Do you want accept this offer? (y/n): ");
                userInput = s.nextLine();
                System.out.println("User input: " + userInput);
            } while (!(userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("n")));

            if (userInput.equalsIgnoreCase("y")) {
                System.out.println("CONGRATULATIONS! You win " + bankOffer + "$");
                System.out.println("Let's see what was in your box no. " + firstChosenBox);
                System.out.println(eliminatedBoxes.get(firstChosenBox).doubleValue());
                gameEnded = true;
            }
        } else {
//            After 9 rounds only 2 suitcases remain in the game (the one the player picked and another one).
//            The user is offered to switch his case he chose at the beginning of the game with the other one
//            left in play.
//
//            The player wins the content of the suitcase, and the program exits.
        }


        currentRound++;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    private void printAvailableBoxes() {
        System.out.println("Available boxes:");
        for (Integer box : boxes.keySet()) {
            System.out.print("[" + box + "]");
        }
        System.out.println();
    }

    private void eliminateBox(int key) {
        eliminatedBoxes.put(key, boxes.get(key));
        boxes.remove(key);

        System.out.println(boxes);
        System.out.println(eliminatedBoxes);
    }

    private void printIntro() {
        System.out.println("$$$ WELCOME TO DEAL OR NO DEAL $$$");
    }

    public int calculateBankOffer() {
        int remainingBoxes = boxes.size();
        int remainingBoxesValue = 0;

        for (double valueOfBox : boxes.values()) {
            remainingBoxesValue += valueOfBox;
        }

        remainingBoxesValue += eliminatedBoxes.get(firstChosenBox).intValue();

        return (remainingBoxesValue / remainingBoxes * currentRound) / 10;
    }
}
