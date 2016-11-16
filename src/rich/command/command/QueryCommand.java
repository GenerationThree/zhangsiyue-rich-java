package rich.command.command;

import rich.Status;
import rich.command.Command;
import rich.commander.Commander;
import rich.commander.Player;

public class QueryCommand implements Command {
    @Override
    public Status execute(Commander player) {
        ((Player)player).query();
        return Status.WAIT_COMMAND;
    }
}
