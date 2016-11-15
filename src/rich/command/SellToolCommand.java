package rich.command;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;
import rich.tool.Tool;

public class SellToolCommand implements Command {
    private int choice;

    public SellToolCommand(int choice) {
        this.choice = choice;
    }

    @Override
    public Player.Status execute(Player player) {
        Tool.Type[] toolTypes = Tool.Type.values();
        if (choice >= 1 && choice <= toolTypes.length) {
            Tool.Type type = toolTypes[choice - 1];
            player.sellTool(type);
        }
        return Player.Status.WAIT_COMMAND;
    }

    @Override
    public Player.Status respond(Player player, Response response, String parameter) {
        throw new UnsupportedMediaException();
    }
}
