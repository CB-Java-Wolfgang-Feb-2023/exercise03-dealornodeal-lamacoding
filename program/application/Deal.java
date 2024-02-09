package application;

import java.util.*;

public class Deal {
    private Map<Integer, Double> boxes;
    private Map<Integer, Double> eliminatedBoxes;
    private Map<Integer, Integer> rounds;
    private int chosenBox, firstChosenBox;
    private Scanner s = new Scanner(System.in);
    private final double[] VALUES = {0.01, 1, 5, 10, 25, 50, 75, 100, 200, 300, 400, 500, 750, 1000, 5000, 10000, 25000, 50000, 75000, 100000, 200000, 300000, 400000, 500000, 750000, 1000000};

    private final int[] BOXES_PER_ROUND = {6, 5, 4, 3, 2, 1, 1, 1, 1};
    int currentRound;

    public Deal() {
        boxes = new HashMap<>();
        eliminatedBoxes = new HashMap<>();
        rounds = new HashMap<>();
        currentRound = 1;

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
            System.out.println(rounds.get(currentRound));
            printAvailableBoxes();
            System.out.print("Choose your box to open: ");

            do {
                try {
                    chosenBox = s.nextInt();
                } catch (InputMismatchException e) {
                    s.nextLine();
                    System.out.println("Please enter only numbers!");
                }

                if (!boxes.containsKey(chosenBox)) {
                    System.out.println("Box not available. Available boxes are:");
                    printAvailableBoxes();
                }
            } while (!boxes.containsKey(chosenBox));

            eliminateBox(chosenBox);
        }
        System.out.println("Bank offer: " + calculateBankOffer());
        currentRound++;
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

        for(double valueOfBox : boxes.values()) {
            remainingBoxesValue += valueOfBox;
        }

        remainingBoxesValue += eliminatedBoxes.get(firstChosenBox).intValue();

        System.out.println("Remaining boxes: " + remainingBoxes);
        System.out.println("Remaining value: " + remainingBoxesValue);

        return (remainingBoxesValue / remainingBoxes * currentRound) / 10;
    }
}
