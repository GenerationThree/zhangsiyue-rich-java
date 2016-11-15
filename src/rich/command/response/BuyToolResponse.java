package rich.command.response;

import rich.Printer;
import rich.commander.Commander;
import rich.commander.Player;
import rich.Status;
import rich.command.Command;
import rich.place.ToolHouse;

public class BuyToolResponse implements Command {
    private String choice;

    public BuyToolResponse(String choice) {
        this.choice = choice;
    }

    @Override
    public Status execute(Commander player) {
        int choice = Integer.valueOf(this.choice);
        ((Player)player).buyTool(choice);
        if (((Player)player).getPoints() >= ToolHouse.POINT_LIMIT) {
            Printer.printMessage("欢迎光临道具屋， 请选择您所需要的道具：");
            return Status.WAIT_RESPONSE;
        }
        else
            return Status.END_TURN;
    }
}
