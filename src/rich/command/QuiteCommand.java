package rich.command;

import rich.Commander;
import rich.Status;

public class QuiteCommand implements Command {
    @Override
    public Status execute(Commander player, String parameter) {
        return Status.END_GAME;
    }

}
