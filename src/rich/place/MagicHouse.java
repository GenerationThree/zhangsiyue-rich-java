package rich.place;

import rich.Printer;
import rich.commander.Player;
import rich.Status;

public class MagicHouse implements Place {
    @Override
    public Status arrive(Player player) {
        Printer.printMessage("欢迎光魔法屋：");
        return Status.WAIT_RESPONSE;
    }

    @Override
    public Status getStatus(Player player) {
        if(player.getStatus() == Status.WAIT_COMMAND)
            return Status.WAIT_COMMAND;
        return Status.WAIT_USE_MAGIC_RESPONSE;
    }
}
