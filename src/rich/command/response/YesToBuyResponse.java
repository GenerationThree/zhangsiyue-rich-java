package rich.command.response;

import rich.commander.Commander;
import rich.commander.Player;
import rich.Status;
import rich.command.Command;

public class YesToBuyResponse implements Command {
    @Override
    public Status execute(Commander player) {
        ((Player)player).buy();
        return Status.END_TURN;
    }
}
