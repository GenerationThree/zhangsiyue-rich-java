package rich.command;

import rich.Player;

public interface Response {

    Player.Status execute(Player player, String parameter);
}
