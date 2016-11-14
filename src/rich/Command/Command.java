package rich.Command;

import rich.Player;

public interface Command {

    Player.Status execute(Player player);

    Player.Status respond(Player player, Response response, String parameter);
}
