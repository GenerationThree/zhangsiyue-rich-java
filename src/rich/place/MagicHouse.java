package rich.place;

import rich.Player;

public class MagicHouse implements Place {
    @Override
    public Player.Status arrive(Player player) {
        return Player.Status.WAIT_RESPONSE;
    }
}
