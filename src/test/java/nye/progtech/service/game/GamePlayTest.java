package nye.progtech.service.game;

import nye.progtech.model.GameState;
import nye.progtech.service.builder.GameStateBuilder;
import nye.progtech.service.command.InputHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GamePlayTest {

    private GamePlay underTest;

    private Scanner mockScanner;
    private InputHandler mockInputHandler;

    @BeforeEach
    public void setup() {
        mockScanner = mock(Scanner.class);
        mockInputHandler = mock(InputHandler.class);
        when(mockScanner.nextLine()).thenReturn("testUser");
        underTest = new GamePlay(mockScanner,mockInputHandler);
    }

    @Test
    void testInitGameShouldStartTheGameLoopWhenCalled() {
        //Given
        GameState mockGameState = mock(GameState.class);

        // Setup mock
        when(mockScanner.nextLine()).thenReturn("6"); // Simulate user input
        when(mockGameState.isStillPlaying()).thenReturn(false,true); // Simulate game state changes
        when(mockInputHandler.handleInput(anyString())).thenReturn(mockGameState);

        //When
        underTest.InitGame();

        //Then
        verify(mockScanner, times(2)).nextLine(); // Verify that nextLine was called
        verify(mockInputHandler, times(1)).handleInput(any());
    }

    @Test
    void testSetGameStateShouldReturnTheGameStateThatWasSetWhenCalled() {
        //Given
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
        GameState expected = new GameStateBuilder().builder(String.join("", gameStateInput));

        //When
        underTest.setGameState(expected);
        GameState actual = underTest.getGameState();

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

    @Test
    void testGetGameStateShouldReturnGameStateWhenCalled() {
        //Given

        //When
        GameState actual =underTest.getGameState();
        //Then
        assertNull(actual.getHero());
        assertNull(actual.getBoard());
        assertEquals("testUser", actual.getUserName());

    }
}