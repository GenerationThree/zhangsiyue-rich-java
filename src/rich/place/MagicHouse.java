package rich.place;

import rich.Player;
import rich.Status;

public class MagicHouse implements Place {
    @Override
    public Status arrive(Player player) {
        return Status.WAIT_RESPONSE;
    }
}
