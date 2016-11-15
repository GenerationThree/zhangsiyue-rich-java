package rich.command.command;

import rich.Commander;
import rich.Status;
import rich.command.Command;

public class QuiteCommand implements Command {
    @Override
    public Status execute(Commander player) {
        return Status.END_GAME;
    }

}
