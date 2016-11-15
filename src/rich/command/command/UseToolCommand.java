package rich.command.command;

import rich.commander.Commander;
import rich.commander.Player;
import rich.Status;
import rich.command.Command;
import rich.map.Map;
import rich.tool.Tool;

public class UseToolCommand implements Command {
    private Map map;
    private int distance;
    private Tool.Type type;

    public UseToolCommand(Map map, int distance, Tool.Type type) {
        this.map = map;
        this.distance = distance;
        this.type = type;
    }

    @Override
    public Status execute(Commander player) {
        ((Player)player).useTool(map, distance, type);
        return Status.WAIT_COMMAND;
    }

}
