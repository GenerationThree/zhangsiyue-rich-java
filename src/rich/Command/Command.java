package rich.command;

import rich.Commander;
import rich.Player;
import rich.Status;

public interface Command {

    Status execute(Commander player);

    Status respond(Commander player, Response response, String parameter);
}
