package nye.progtech.service.game;

import java.util.Scanner;
import java.util.regex.Pattern;

import nye.progtech.model.GameState;
import nye.progtech.model.MenuStructure;
import nye.progtech.service.command.InputHandler;
import nye.progtech.service.util.GameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GamePlay {

    private static final Logger LOGGER = LoggerFactory.getLogger(GamePlay.class);
    private GameState gameState;
    private final Scanner scanner;
    private final InputHandler inputHandler;

    public GamePlay(Scanner scanner, InputHandler inputHandler) {
        this.scanner = scanner;
        this.inputHandler = inputHandler;
        System.out.println("\n\n\tThe Wumpus is waiting for you\n---------------------------------------\n\n\n" +
                "Please enter your user name:");
        String userName = scanner.nextLine();
        LOGGER.info("Read User Name = '{}'", userName);
        gameState = new GameState(userName);
    }

    public void InitGame()  {
        LOGGER.info("Starting the Game.");
        while (gameState.isStillPlaying()) {
            GameUtil.clearConsole();
            inputHandler.setGameState(this.getGameState());
            MenuStructure.MENU_LIST.forEach(System.out::println); //Ezt lehet utilitybe rakom, hogy lehessen tesztelni.
            String choice = this.scanner.nextLine();
            LOGGER.info("User({}) tried to invoke the following menu point = '{}'", gameState.getUserName(), choice);
            if (Pattern.matches("[0-6]", choice)) {
                LOGGER.info("Input handler was called with '{}'", choice);
                this.setGameState(inputHandler.handleInput(choice));
            } else {
                LOGGER.warn("User chose incorrect menu point {}", choice);
                System.out.println("Invalid command, only 1-6 is accepted!");
            }
        }
        scanner.close();
        LOGGER.info("Game play is finished.");
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }
}
