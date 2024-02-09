package application;

public class Main {
    public static void main(String[] args) {
        Deal game = new Deal();

        game.startGame();
        while (!game.isGameEnded()) {
            game.playRound();
        }
    }
}
