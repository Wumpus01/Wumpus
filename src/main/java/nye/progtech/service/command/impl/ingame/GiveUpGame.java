package nye.progtech.service.command.impl.ingame;

import nye.progtech.model.GameState;
import nye.progtech.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GiveUpGame implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GiveUpGame.class);


    @Override
    public boolean canProcess(char input) {
        return input == 'q';
    }

    @Override
    public GameState process(GameState gameState) {
        LOGGER.info("Player {} has given up the game", gameState.getUserName());
        gameState.setGivenUpGame(true);
        return gameState;
    }
}
