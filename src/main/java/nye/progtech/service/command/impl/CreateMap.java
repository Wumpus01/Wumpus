package nye.progtech.service.command.impl;

import nye.progtech.model.GameState;
import nye.progtech.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CreateMap implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMap.class);

    @Override
    public boolean canProcess(char input) {
        return input == '1';
    }

    @Override
    public GameState process(GameState gameState) {
        LOGGER.info("Create map command was invoked");
        System.out.println("Not yet implemented");
        return gameState;
    }
}
