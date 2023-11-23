package nye.progtech.service.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nye.progtech.model.Arrow;
import nye.progtech.model.GameState;
import nye.progtech.persistence.impl.jdbc.DatabaseService;
import nye.progtech.service.command.impl.CreateMap;
import nye.progtech.service.command.impl.Default;
import nye.progtech.service.command.impl.LoadFromDatabase;
import nye.progtech.service.command.impl.QuitGame;
import nye.progtech.service.command.impl.ReadMapFromFile;
import nye.progtech.service.command.impl.SaveToDatabase;
import nye.progtech.service.command.impl.StartGame;
import nye.progtech.service.command.impl.ingame.GiveUpGame;
import nye.progtech.service.command.impl.ingame.MoveForward;
import nye.progtech.service.command.impl.ingame.ShootArrow;
import nye.progtech.service.command.impl.ingame.TakeGold;
import nye.progtech.service.command.impl.ingame.TurnLeft;
import nye.progtech.service.command.impl.ingame.TurnRight;
import nye.progtech.service.util.HeroUtil;
import nye.progtech.service.util.MapUtil;
import nye.progtech.ui.ClassicRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputHandler.class);
    private static final List<Command> COMMANDS = new ArrayList<>(List.of(new CreateMap(),
            new ReadMapFromFile(new Scanner(System.in)),
            new LoadFromDatabase(new Scanner(System.in), DatabaseService.getInstance()),
            new SaveToDatabase(DatabaseService.getInstance()),
            new StartGame(new ClassicRender(), new Scanner(System.in), new InputHandler()),
            new QuitGame(),
            new GiveUpGame(),
            new MoveForward(new MapUtil(), new HeroUtil()),
            new ShootArrow(new MapUtil(), new HeroUtil(), new Arrow(new int[] {0, 0}, 'N', 1)),
            new TakeGold(new MapUtil()),
            new TurnLeft(new HeroUtil()),
            new TurnRight(new HeroUtil()),
            new Default()));

    private GameState gameState;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState handleInput(String input) {
        if (input.length() == 1) {
            for (Command command : COMMANDS) {
                if (command.canProcess(input.charAt(0))) {
                    LOGGER.info("The input handler was invoked to handle command {}", input.charAt(0));
                    gameState = command.process(this.getGameState());
                    break;
                }
            }
        }
        return gameState;
    }
}
