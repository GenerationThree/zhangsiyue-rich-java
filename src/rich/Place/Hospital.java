package rich.place;

import rich.Player;
import rich.Status;

public class Hospital implements Place {
    @Override
    public Status arrive(Player player) {
        return Status.END_TURN;
    }
}
