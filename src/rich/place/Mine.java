package rich.place;

import rich.Player;

public class Mine implements Place {
    private int points;

    public Mine(int points) {
        this.points = points;
    }

    @Override
    public Player.Status arrive(Player player) {
        player.gainPoints(points);
        return Player.Status.END_TURN;
    }
}
