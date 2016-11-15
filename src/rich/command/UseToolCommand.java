package rich.command;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;
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
    public Player.Status execute(Player player) {
        player.useTool(map, distance, type);
        return Player.Status.WAIT_COMMAND;
    }

    @Override
    public Player.Status respond(Player player, Response response, String parameter) {
        throw new UnsupportedMediaException();

    }
}
