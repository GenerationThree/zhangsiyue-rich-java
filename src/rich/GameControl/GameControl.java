package rich.GameControl;

import rich.Player;

import java.util.List;

public interface GameControl {

    List<Player> getPlayers();

    void setInitBalance(double initBalance) throws Exception;

    void addPlayer(int id) throws Exception;

    void startGame();

    void startTurn();

    Player findWinner();
}
