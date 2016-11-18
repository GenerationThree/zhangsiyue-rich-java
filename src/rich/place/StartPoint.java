package rich.place;

import rich.commander.Player;
import rich.Status;

public class StartPoint implements Place {
    @Override
    public Status arrive(Player player) {
        return Status.END_TURN;
    }

    @Override
    public Status getStatus(Player player) {
        if(player.getStatus() == Status.WAIT_COMMAND)
            return Status.WAIT_COMMAND;
        return Status.END_TURN;
    }
}
