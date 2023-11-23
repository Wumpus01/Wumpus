package nye.progtech.service.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GameUtilTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testClearConsoleShouldClearTheConsoleWhenInvoked() {
        GameUtil.clearConsole();
        assertEquals("\033[H\033[2J", outContent.toString());
    }

    //isGameCompleted
    @Test
    void testIsGameCompletedShouldReturnFalseWhenTheGameIsNotCompleted() {
        //Given
        int[] startPosition = {2, 2}, currentPosition = {2, 4};
        boolean hasGold = true, expected =false;
        //When
        boolean actual = GameUtil.isGameCompleted(startPosition, currentPosition, hasGold);
        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testIsGameCompletedShouldReturnTureWhenTheGameIsCompleted() {
        //Given
        int[] startPosition = {2, 2}, currentPosition = {2, 2};
        boolean hasGold = true, expected =true;
        //When
        boolean actual = GameUtil.isGameCompleted(startPosition, currentPosition, hasGold);
        //Then
        Assertions.assertEquals(expected, actual);
    }

}