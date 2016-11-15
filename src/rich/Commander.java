package rich;

import rich.command.Command;

public interface Commander {
    void executeCommand(Command command, String parameter);
}
