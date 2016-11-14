package rich.Place;

import rich.Player;

public class GiftHouse implements Place {
    @Override
    public Player.Status arrive(Player player) {
        return Player.Status.WAIT_RESPONSE;
    }
}
