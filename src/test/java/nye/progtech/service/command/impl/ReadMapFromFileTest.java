package nye.progtech.service.command.impl;

import nye.progtech.model.GameState;
import nye.progtech.service.util.GameUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ReadMapFromFileTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    ReadMapFromFile underTest = new ReadMapFromFile(new Scanner(System.in));
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
        char input = '2';
        boolean expected = true;
        //When
        boolean actual = underTest.canProcess(input);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testProcessShouldInvokeInputProcessingMethodsWhenCalled() {
        //Given
        String testPath = "c:\\Users\\Gh\\wumpus1.txt";
        Scanner mockScanner = mock(Scanner.class);
        GameState mockGameState = mock(GameState.class);


        when(mockScanner.nextLine()).thenReturn(testPath);
        underTest = new ReadMapFromFile(mockScanner);
        //When
        underTest.process(mockGameState);
        //Then
        verify(mockScanner, times(1)).nextLine(); // Verify that nextLine was called


    }
}