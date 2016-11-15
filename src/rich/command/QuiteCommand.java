package rich.command;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Commander;
import rich.Player;
import rich.Status;

public class QuiteCommand implements Command {
    @Override
    public Status execute(Commander player) {
        return Status.END_GAME;
    }

    @Override
    public Status respond(Commander player, Response response, String parameter) {
        throw new UnsupportedMediaException();

    }
}
