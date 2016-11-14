package rich.place;

import rich.Player;

public class Hospital implements Place {
    @Override
    public Player.Status arrive(Player player) {
        return Player.Status.END_TURN;
    }
}
