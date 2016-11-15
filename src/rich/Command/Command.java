package rich.command;

import rich.Commander;
import rich.Status;

public interface Command {

    Status execute(Commander player);

}
