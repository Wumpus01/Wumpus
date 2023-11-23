package nye.progtech.persistence.impl.jdbc;

import nye.progtech.model.GameState;
import nye.progtech.service.builder.GameStateBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class DatabaseHandlerTest {

    private  DatabaseHandler underTest;

    private GameState expected;
    private String gameStateFlatMap;

    @BeforeEach
    public void setup() {
        List<String> gameStateInput = new ArrayList<>(
                List.of("0 B 5 false ",
                        "B 4 N 1 false ",
                        "6 ",
                        "WWWWWW",
                        "W___PW",
                        "WUGP_W",
                        "W____W",
                        "W__P_W",
                        "WWWWWW"));
        gameStateFlatMap = String.join("", gameStateInput);
        expected = new GameStateBuilder().builder(gameStateFlatMap);
    }

    @Test
    void testSaveGameShouldSaveTheGameIntoTheDatabaseWhenCalled()  {
        // Given
        DatabaseServiceInterface mockDatabaseService = mock(DatabaseServiceInterface.class);

        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockDatabaseService.getConnection()).thenReturn(mockConnection);

        try {
            when(mockConnection.prepareStatement("INSERT INTO SAVED_GAMES (PLAYER,SCORE,FINISHED,GAMESTATE) " + "VALUES(?,?,?,?)")).thenReturn(mockPreparedStatement);
            underTest = new DatabaseHandler(mockDatabaseService);

            // When
            underTest.saveGame("TestUser", expected);

            // Then
            Mockito.verify(mockPreparedStatement, Mockito.times(1)).execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLoadGameShouldGetBackTheCorrectGameWhenCalled() {
        // Given
        DatabaseServiceInterface mockDatabaseService = mock(DatabaseServiceInterface.class);

        try {
            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            GameStateBuilder mockGameStateBuilder = mock(GameStateBuilder.class);
            ResultSet mockResultSet = mock(ResultSet.class);
            when(mockDatabaseService.getConnection()).thenReturn(mockConnection);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("GAMESTATE")).thenReturn(gameStateFlatMap);
            when(mockConnection.prepareStatement("SELECT GAMESTATE FROM SAVED_GAMES WHERE PLAYER = ? AND ID = ?")).thenReturn(mockPreparedStatement);

            underTest = new DatabaseHandler(mockDatabaseService);

            // When
            GameState actual = underTest.loadGame("TestUser", 1);

            //Then
            Assertions.assertArrayEquals(expected.getHero().getPosition(), actual.getHero().getPosition());
            Assertions.assertEquals(expected.getHero().getDirection(), actual.getHero().getDirection());
            Assertions.assertEquals(expected.getHero().getNumberOfArrows(), actual.getHero().getNumberOfArrows());
            Assertions.assertEquals(expected.getHero().getHasGold(), actual.getHero().getHasGold());

            Assertions.assertArrayEquals(expected.getBoard().getMap(),actual.getBoard().getMap());
            Assertions.assertEquals(expected.getBoard().getSize(),actual.getBoard().getSize());

            Assertions.assertEquals(expected.getNumberOfSteps(),actual.getNumberOfSteps());
            Assertions.assertArrayEquals(expected.getStartPosition(),actual.getStartPosition());
            Assertions.assertEquals(expected.isFinishedGame(),actual.isFinishedGame());
            Assertions.assertEquals(expected.isGivenUpGame(),actual.isGivenUpGame());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLoadGameShouldReturnNullWhenTheStatementExecutionFails() {
        // Given
        DatabaseServiceInterface mockDatabaseService = mock(DatabaseServiceInterface.class);

        try {
            Connection mockConnection = mock(Connection.class);
            when(mockDatabaseService.getConnection()).thenReturn(mockConnection);
            doThrow(new SQLException("Failed acquirement of a statement")).when(mockConnection).prepareStatement(anyString());
            underTest = new DatabaseHandler(mockDatabaseService);

            // When
            GameState actual = underTest.loadGame("TestUser", 1);

            //Then
            Assertions.assertNull(actual);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCheckGameExistsShouldGetBackTrueWhenTheGameExists() {
        // Given
        DatabaseServiceInterface mockDatabaseService = mock(DatabaseServiceInterface.class);

        try {
            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            GameStateBuilder mockGameStateBuilder = mock(GameStateBuilder.class);
            ResultSet mockResultSet = mock(ResultSet.class);
            when(mockDatabaseService.getConnection()).thenReturn(mockConnection);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("GAMESTATE")).thenReturn(gameStateFlatMap);
            when(mockConnection.prepareStatement("SELECT GAMESTATE FROM SAVED_GAMES WHERE PLAYER = ? AND ID = ?")).thenReturn(mockPreparedStatement);
            underTest = new DatabaseHandler(mockDatabaseService);


            // When
            boolean actual = underTest.checkGameExists("TestUser", 1);

            //Then
            Assertions.assertTrue(actual);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testCheckGameExistsShouldGetBackFalseWhenTheGameDoesNotExists() {
        // Given
        DatabaseServiceInterface mockDatabaseService = mock(DatabaseServiceInterface.class);

        try {
            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            GameStateBuilder mockGameStateBuilder = mock(GameStateBuilder.class);
            ResultSet mockResultSet = mock(ResultSet.class);
            when(mockDatabaseService.getConnection()).thenReturn(mockConnection);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);
            when(mockResultSet.getString("GAMESTATE")).thenReturn(gameStateFlatMap);
            when(mockConnection.prepareStatement("SELECT GAMESTATE FROM SAVED_GAMES WHERE PLAYER = ? AND ID = ?")).thenReturn(mockPreparedStatement);
            underTest = new DatabaseHandler(mockDatabaseService);

            // When
            boolean actual = underTest.checkGameExists("TestUser", 1);

            //Then
            Assertions.assertFalse(actual);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}