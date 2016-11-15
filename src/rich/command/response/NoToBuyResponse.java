package rich.command.response;

import rich.commander.Commander;
import rich.Status;
import rich.command.Command;

public class NoToBuyResponse implements Command {
    @Override
    public Status execute(Commander player) {
        return Status.END_TURN;
    }
}
