package rich.command;

import rich.commander.Commander;
import rich.Status;

public interface Command {

    Status execute(Commander player);

}
