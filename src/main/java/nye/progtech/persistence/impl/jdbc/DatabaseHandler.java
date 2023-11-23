package nye.progtech.persistence.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nye.progtech.model.GameState;
import nye.progtech.persistence.GameRepository;
import nye.progtech.service.builder.GameStateBuilder;


public class DatabaseHandler implements GameRepository {
    private final DatabaseServiceInterface databaseService;

    public DatabaseHandler(DatabaseServiceInterface databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void saveGame(String userName, GameState gameState) {

        String statement = "INSERT INTO SAVED_GAMES (PLAYER,SCORE,FINISHED,GAMESTATE) " + "VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, gameState.getNumberOfSteps());
            preparedStatement.setBoolean(3, gameState.isFinishedGame());
            preparedStatement.setString(4, gameState.toString());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Prepared statement failed: " + statement);
        }
    }

    @Override
    public GameState loadGame(String userName, int gameID) {
        String statement = "SELECT GAMESTATE FROM SAVED_GAMES WHERE PLAYER = ? AND ID = ?";
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, gameID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String gameState = resultSet.getString("GAMESTATE");
            GameState loadedGameState = new GameStateBuilder().builder(gameState);
            loadedGameState.setUserName(userName);
            return loadedGameState;
        } catch (SQLException ex) {
            System.out.println("Prepared statement failed: " + statement);
            return null;
        }
    }

    @Override
    public boolean checkGameExists(String userName, int gameID) {
        String statement = "SELECT GAMESTATE FROM SAVED_GAMES WHERE PLAYER = ? AND ID = ?";
        try (PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(statement)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, gameID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            System.out.println("Check for gameplay existence could not be checked.");
            return false;
        }
    }
}
