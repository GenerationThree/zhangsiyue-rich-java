package rich.place;

import rich.commander.Player;
import rich.Status;

public class Mine implements Place {
    private int points;

    public Mine(int points) {
        this.points = points;
    }

    @Override
    public Status arrive(Player player) {
        player.gainPoints(points);
        return Status.END_TURN;
    }
}
