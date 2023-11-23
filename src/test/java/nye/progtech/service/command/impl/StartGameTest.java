package nye.progtech.service.command.impl;


import nye.progtech.model.GameState;
import nye.progtech.service.command.InputHandler;
import nye.progtech.service.util.GameUtil;
import nye.progtech.ui.BoardRender;
import nye.progtech.ui.ClassicRender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class StartGameTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private AutoCloseable closeable;
    InputHandler inputHandler = new InputHandler();

    StartGame underTest = new StartGame(new ClassicRender(), new Scanner(System.in), inputHandler);
    @Mock
    private GameUtil gameUtil;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testCanProcessShouldReturnTrueWhenGetsTheCorrectInput() {
        //Given
        char input = '5';
        boolean expected = true;
        //When
        boolean actual = underTest.canProcess(input);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testProcessShouldHandleUserInputWhenCalledWithValidInput() {

        //Given
        BoardRender mockBoardRender = mock(BoardRender.class);
        Scanner mockScanner = mock(Scanner.class);
        InputHandler mockInputHandler = mock(InputHandler.class);
        GameState mockGameState = mock(GameState.class);

        // Setup mock
        when(mockScanner.nextLine()).thenReturn("w", "q"); // Simulate user input
        when(mockGameState.isGivenUpGame()).thenReturn(false,false, true); // Simulate game state changes
        when(mockInputHandler.handleInput(anyString())).thenReturn( mockGameState, mockGameState);

        //Create with mock
        underTest  = new StartGame(mockBoardRender, mockScanner, mockInputHandler);

        //When
        GameState result = underTest.process(mockGameState);

        //Then
        verify(mockScanner, times(2)).nextLine(); // Verify that nextLine was called
        verify(mockInputHandler, times(2)).handleInput(any());
    }

    @Test
    public void testProcessShouldHandleUserInputWhenCalledWithInValidInput() {

        //Given
        List<String> expectedError = List.of("Invalid command, only the followings are allowed:",
        "-> a - left turn",
                "-> d - right turn",
                "-> w - move forward",
                "-> ' ' - shoot arrow",
                "-> s - take gold",
                "-> q - give up",
                "");
        String expected = expectedError.stream().collect(Collectors.joining("")) + "\r";
        BoardRender mockBoardRender = mock(BoardRender.class);
        Scanner mockScanner = mock(Scanner.class);
        InputHandler mockInputHandler = mock(InputHandler.class);
        GameState mockGameState = mock(GameState.class);

        // Setup mock
        when(mockScanner.nextLine()).thenReturn("t"); // Simulate user input
        when(mockGameState.isGivenUpGame()).thenReturn(false, true); // Simulate game state changes


        //Create with mock
        underTest  = new StartGame(mockBoardRender, mockScanner, mockInputHandler);

        //When
        GameState result = underTest.process(mockGameState);

        //Then
        verify(mockScanner, times(1)).nextLine(); // Verify that nextLine was called
        assertEquals(expected, outContent.toString().replace("\t","").replace("\n",""));
    }

}