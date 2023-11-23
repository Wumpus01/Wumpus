package nye.progtech.service.command.impl;

import nye.progtech.model.GameState;
import nye.progtech.persistence.impl.jdbc.DatabaseHandler;
import nye.progtech.persistence.impl.jdbc.DatabaseService;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SaveToDatabase implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveToDatabase.class);

    private final DatabaseServiceInterface databaseServiceInterface;

    public SaveToDatabase(DatabaseServiceInterface databaseServiceInterface) {
        this.databaseServiceInterface = databaseServiceInterface;
    }

    @Override
    public boolean canProcess(char input) {
        return input == '4';
    }

    @Override
    public GameState process(GameState gameState) {
        LOGGER.info("User ({}) initiated to save the current game with the following state\n{}", gameState.getUserName(), gameState);
        new DatabaseHandler(databaseServiceInterface).saveGame(gameState.getUserName(), gameState);
        return gameState;
    }
}
