package rich.command.command;

import rich.commander.Commander;
import rich.commander.GameControl;
import rich.Status;
import rich.command.Command;

public class StartGameCommand implements Command {
    @Override
    public Status execute(Commander gameControl) {
        ((GameControl) gameControl).startGame();
        return Status.IN_PROGRESS;
    }
}
