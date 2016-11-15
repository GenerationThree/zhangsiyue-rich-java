package rich.command.response;

import rich.Commander;
import rich.Player;
import rich.Status;
import rich.command.Command;

public class YesToPromoteResponse implements Command {
    @Override
    public Status execute(Commander player) {
        ((Player)player).promoteEstate();
        return Status.END_TURN;
    }
}
