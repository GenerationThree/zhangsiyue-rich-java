package rich;

import rich.command.Command;

public interface Commander {
    void execute(Command command, String parameter);
}
