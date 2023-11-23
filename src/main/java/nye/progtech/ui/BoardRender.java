package nye.progtech.ui;

import nye.progtech.model.Arrow;
import nye.progtech.model.Board;
import nye.progtech.model.Hero;

public interface BoardRender {
    void  clearConsole();

    void  printBoard(Board board, Hero hero, Arrow arrow);


}
