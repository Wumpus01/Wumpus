package nye.progtech.ui;

import java.util.HashMap;
import java.util.Map;

import nye.progtech.model.Arrow;
import nye.progtech.model.Board;
import nye.progtech.model.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClassicRender implements BoardRender {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassicRender.class);
    public static final Map<Character, Character> mapRepresentation = new HashMap<>();
    public static final Map<Character, Character> heroRepresentation = new HashMap<>();
    public static final Map<Character, Character> arrowRepresentation = new HashMap<>();

    static {
        mapRepresentation.put('W', 'W');
        mapRepresentation.put('G', 'G');
        mapRepresentation.put('U', 'U');
        mapRepresentation.put('P', 'P');
        mapRepresentation.put('_', ' ');
    }

    static {
        heroRepresentation.put('N', '^');
        heroRepresentation.put('E', '>');
        heroRepresentation.put('W', '<');
        heroRepresentation.put('S', 'v');
    }

    static {
        arrowRepresentation.put('N', '|');
        arrowRepresentation.put('E', '-');
        arrowRepresentation.put('W', '-');
        arrowRepresentation.put('S', '|');
    }

    @Override
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void printBoard(Board board, Hero hero, Arrow arrow) {
        this.clearConsole();

        int[] arrowPosition;
        char arrowDirection;
        if (arrow == null) {
            arrowPosition = new int[]{100, 100};
            arrowDirection = 'E';
        } else {
            arrowPosition = arrow.getPosition();
            arrowDirection = arrow.getDirection();
        }
        char[][] map = board.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i == hero.getPosition()[0] && j == hero.getPosition()[1]) {
                    System.out.print(heroRepresentation.get(hero.getDirection()));
                } else if (i == arrowPosition[0] && j == arrowPosition[1]) {
                    System.out.print(arrowRepresentation.get(arrowDirection));
                } else {
                    System.out.print(mapRepresentation.get(map[i][j]));
                }
            }
            System.out.println();
        }
    }


}
