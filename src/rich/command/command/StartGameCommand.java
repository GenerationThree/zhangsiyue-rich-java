package rich.command.command;

import rich.Commander;
import rich.GameControl.GameControl;
import rich.Status;
import rich.command.Command;

public class StartGameCommand implements Command {
    @Override
    public Status execute(Commander gameControl) {
        ((GameControl) gameControl).startGame();
        return null;
    }
}
