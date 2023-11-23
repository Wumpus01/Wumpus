package nye.progtech.persistence;

import nye.progtech.model.GameState;

public interface GameRepository {
    void saveGame(String userName, GameState gameState);

    GameState loadGame(String userName, int gameID);

    boolean checkGameExists(String userName, int gameID);
}
