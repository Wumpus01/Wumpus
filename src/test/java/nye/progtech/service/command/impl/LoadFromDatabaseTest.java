package nye.progtech.service.command.impl;

import nye.progtech.model.GameState;
import nye.progtech.persistence.impl.jdbc.DatabaseServiceInterface;
import nye.progtech.service.builder.GameStateBuilder;
import nye.progtech.service.util.MapUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoadFromDatabaseTest {

    private LoadFromDatabase underTest;
    private GameState expected;
    private String gameStateFlatMap;
    private DatabaseServiceInterface mockDatabaseService;

    private Scanner mockScanner;

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
        expected.setUserName("testUser");
        mockScanner = mock(Scanner.class);
        mockDatabaseService = mock(DatabaseServiceInterface.class);
    }

    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        char input ='3';
        boolean expected = true;
        underTest = new LoadFromDatabase(mockScanner, mockDatabaseService );
        //When
        boolean actual = underTest.canProcess(input);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testProcessShouldCorrectlyProcessTheGameWhenCalled() {
        //Given
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockScanner.nextLine()).thenReturn("1");
        try {
            when(mockDatabaseService.getConnection()).thenReturn(mockConnection);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("GAMESTATE")).thenReturn(gameStateFlatMap);
            when(mockConnection.prepareStatement("SELECT GAMESTATE FROM SAVED_GAMES WHERE PLAYER = ? AND ID = ?")).thenReturn(mockPreparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        underTest = new LoadFromDatabase(mockScanner, mockDatabaseService);
        //When
        GameState actual = underTest.process(expected);

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

    }
}