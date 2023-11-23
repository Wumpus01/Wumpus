package nye.progtech.service.command.impl;

import java.util.Scanner;
import java.util.regex.Pattern;

import nye.progtech.model.GameState;
import nye.progtech.service.command.Command;
import nye.progtech.service.command.InputHandler;
import nye.progtech.ui.BoardRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartGame implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartGame.class);

    private final BoardRender boardRender;
    private final  Scanner scanner;
    private final InputHandler inputHandler;

    public StartGame(BoardRender boardRender, Scanner scanner, InputHandler inputHandler) {
        this.boardRender = boardRender;
        this.scanner = scanner;
        this.inputHandler = inputHandler;
    }

    @Override
    public boolean canProcess(char input) {
        return input == '5';
    }

    @Override
    public GameState process(GameState gameState) {

        while (!gameState.isGivenUpGame() && !gameState.isFinishedGame()) {
            boardRender.printBoard(gameState.getBoard(), gameState.getHero(), null);
            inputHandler.setGameState(gameState);
            String choice = scanner.nextLine();
            if (Pattern.matches("[asdwq ]", choice)) {
                LOGGER.info("Input handler was invoked with command: {}", choice);
                gameState = inputHandler.handleInput(choice);
            } else {
                System.out.println("Invalid command, only the followings are allowed:\n\t-> a - left turn" +
                        "\n\t-> d - right turn\n\t-> w - move forward\n\t-> ' ' - shoot arrow\n\t-> s - take gold\n\t-> q - give up");
            }
        }
        return gameState;
    }
}
