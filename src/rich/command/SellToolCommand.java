package rich.command;

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
    public Status execute(Commander player, String parameter) {
        Tool.Type[] toolTypes = Tool.Type.values();
        if (choice >= 1 && choice <= toolTypes.length) {
            Tool.Type type = toolTypes[choice - 1];
            ((Player)player).sellTool(type);
        }
        return Status.WAIT_COMMAND;
    }

}
