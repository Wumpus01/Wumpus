package nye.progtech.service.util;


public class GameUtil {

    public static boolean isGameCompleted(int[] startPosition, int[] currentPosition, boolean hasGold) {
        return startPosition[0] == currentPosition[0] && startPosition[1] == currentPosition[1] && hasGold;
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}

