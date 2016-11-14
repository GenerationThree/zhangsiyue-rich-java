package rich.Place;

import rich.Player;

public class Prison implements Place {
    public static final int WAIT_TURN = 2;
    @Override
    public Player.Status arrive(Player player) {
        player.inPrison(WAIT_TURN);
        return Player.Status.END_TURN;
    }
}
