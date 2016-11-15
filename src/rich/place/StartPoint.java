package rich.place;

import rich.commander.Player;
import rich.Status;

public class StartPoint implements Place {
    @Override
    public Status arrive(Player player) {
        return Status.END_TURN;
    }
}
