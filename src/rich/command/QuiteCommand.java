package rich.command;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;

public class QuiteCommand implements Command {
    @Override
    public Player.Status execute(Player player) {
        return Player.Status.END_GAME;
    }

    @Override
    public Player.Status respond(Player player, Response response, String parameter) {
        throw new UnsupportedMediaException();

    }
}
