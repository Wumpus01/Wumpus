package nye.progtech.service.util;

import java.util.Arrays;

import nye.progtech.model.Board;


public class MapUtil {

    public Board setPosition(int[] position, char character, char[][] map) {
        int mapSize = map.length;
        char[][] newMap =  new char[mapSize][];

        for (int i = 0; i < mapSize; i++) {
            newMap[i] = Arrays.copyOf(map[i], map[i].length);
        }

        map[position[0]][position[1]] = character; //It can be done because of the deepcopy
        return new Board(mapSize, map);
    }

    public char getChar(int[] position, char[][] map) {
        char foundChar;
        foundChar = map[position[0]][position[1]];
        return foundChar;
    }
}
