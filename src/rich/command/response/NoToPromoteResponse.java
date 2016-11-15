package rich.command.response;

import rich.commander.Commander;
import rich.Status;
import rich.command.Command;

public class NoToPromoteResponse implements Command {
    @Override
    public Status execute(Commander player) {
        return Status.END_TURN;
    }
}
