package rich.command.response;

import rich.commander.Commander;
import rich.commander.Player;
import rich.Status;
import rich.command.Command;

public class SelectGiftResponse implements Command {
    private String choice;

    public SelectGiftResponse(String choice) {
        this.choice = choice;
    }

    @Override
    public Status execute(Commander player) {
        int choice = Integer.valueOf(this.choice);
        ((Player)player).selectGift(choice);
        return Status.END_TURN;
    }
}
