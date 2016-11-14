package rich.Command;

import rich.Player;

public interface Response {

    Player.Status execute(Player player);
}
