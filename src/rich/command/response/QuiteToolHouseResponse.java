package rich.command.response;

import rich.Commander;
import rich.Player;
import rich.Status;
import rich.command.Command;

public class QuiteToolHouseResponse implements Command {

    @Override
    public Status execute(Commander player) {
        return Status.END_TURN;
    }
}
