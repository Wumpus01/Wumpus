package nye.progtech.service.command.impl;

import java.util.Scanner;

import nye.progtech.model.GameState;
import nye.progtech.persistence.impl.jdbc.DatabaseHandler;
import nye.progtech.persistence.impl.jdbc.DatabaseService;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadFromDatabase implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadFromDatabase.class);
    private final DatabaseServiceInterface databaseServiceInterface;
    private final Scanner scanner;

    public LoadFromDatabase(Scanner scanner, DatabaseServiceInterface databaseServiceInterface) {
        this.scanner = scanner;
        this.databaseServiceInterface = databaseServiceInterface;
    }

    @Override
    public boolean canProcess(char input) {
        return input == '3';
    }

    @Override
    public GameState process(GameState gameState) {

        System.out.println("Please enter the gameid you want to load:");
        int id = scanner.nextInt();
        scanner.nextLine();
        LOGGER.info("Load map command was invoked with the following game ID: {}", id);
        DatabaseHandler databaseHandler = new DatabaseHandler(databaseServiceInterface);
        GameState loadedGame = null;
        if (databaseHandler.checkGameExists(gameState.getUserName(), id)) {
            loadedGame = databaseHandler.loadGame(gameState.getUserName(), id);
            System.out.println("Game successfully loaded!");
        }
        return loadedGame != null ? loadedGame : gameState;
    }
}
