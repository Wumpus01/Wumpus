package nye.progtech.service.builder;

import java.util.Arrays;

import nye.progtech.model.Board;
import nye.progtech.model.GameState;
import nye.progtech.model.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GameStateBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStateBuilder.class);

    public GameStateBuilder() {}

    public GameState builder(String gameState) {
        LOGGER.info("Game state builder creating a map using flatmap:\n{}", gameState);
        String[] inputString = gameState.split(" ");

        String heroLine = String.join(" ", Arrays.copyOfRange(inputString, 4, 9));
        Hero hero = new HeroBuilder().builder(heroLine);

        String flatMap = inputString[10];
        int size = Integer.parseInt(inputString[9]);
        Board board = new BoardBuilder().builder(flatMap, size);

        int numberOfSteps = Integer.parseInt(inputString[0]);
        int[] startingPosition = new int[2];
        startingPosition[0] = Integer.parseInt(inputString[2]) - 1;
        startingPosition[1] = inputString[1].charAt(0) - 'A';
        boolean finished = Boolean.getBoolean(inputString[3]);

        return new GameState(board, hero, numberOfSteps, startingPosition, finished);

    }

}
