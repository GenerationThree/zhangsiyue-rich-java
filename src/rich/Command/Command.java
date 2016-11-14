package rich.Command;

import rich.Player;

public interface Command {

    Player.Status execute(Player player);
}
