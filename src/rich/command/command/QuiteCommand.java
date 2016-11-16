package rich.command.command;

import rich.commander.Commander;
import rich.Status;
import rich.command.Command;

public class QuiteCommand implements Command {
    @Override
    public Status execute(Commander player) {
        System.exit(0);
        return Status.LOSE_GAME;
    }

}
