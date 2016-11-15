package rich.place;

import rich.commander.Player;
import rich.Status;

public class GiftHouse implements Place {
    @Override
    public Status arrive(Player player) {
        return Status.WAIT_RESPONSE;
    }
}
