package rich.command;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Commander;
import rich.Player;
import rich.Status;
import rich.tool.Tool;

public class SellToolCommand implements Command {
    private int choice;

    public SellToolCommand(int choice) {
        this.choice = choice;
    }

    @Override
    public Status execute(Commander player) {
        Tool.Type[] toolTypes = Tool.Type.values();
        if (choice >= 1 && choice <= toolTypes.length) {
            Tool.Type type = toolTypes[choice - 1];
            ((Player)player).sellTool(type);
        }
        return Status.WAIT_COMMAND;
    }

    @Override
    public Status respond(Commander player, Response response, String parameter) {
        throw new UnsupportedMediaException();
    }
}
