package rich.place;

import rich.commander.Player;
import rich.Status;

public class Prison implements Place {
    public static final int WAIT_TURN = 2;
    @Override
    public Status arrive(Player player) {
        player.inPrison(WAIT_TURN);
        return Status.END_TURN;
    }

    @Override
    public Status getStatus(Player player) {
        if(player.getStatus() == Status.WAIT_COMMAND)
            return Status.WAIT_COMMAND;
        return Status.END_TURN;
    }
}
