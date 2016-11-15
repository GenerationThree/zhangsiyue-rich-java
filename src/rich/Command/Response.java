package rich.command;

import rich.Player;
import rich.Status;

public interface Response {

    Status execute(Player player, String parameter);
}
