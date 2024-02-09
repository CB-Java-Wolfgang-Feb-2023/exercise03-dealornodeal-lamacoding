package application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Deal or no Deal?");

        Deal game = new Deal();

        game.startGame();
        game.playRound();
    }
}
