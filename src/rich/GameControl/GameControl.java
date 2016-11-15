package rich.GameControl;

import rich.Player;

import java.util.List;

public interface GameControl {

    List<Player> getPlayers();

    void setInitBalance(double initBalance) throws Exception;
}
