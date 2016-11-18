package rich.place;

import rich.Printer;
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
        Printer.printMessage("采矿获得点数" + points +"点 :) ");
        return Status.END_TURN;
    }

    @Override
    public Status getStatus(Player player) {
        if(player.getStatus() == Status.WAIT_COMMAND)
            return Status.WAIT_COMMAND;
        return Status.END_TURN;
    }
}
