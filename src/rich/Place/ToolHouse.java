package rich.Place;

import rich.Player;
import rich.Tool.Tool;

import java.util.Arrays;
import java.util.Comparator;

public class ToolHouse implements Place {
    public static int POINT_LIMIT;

    public ToolHouse(){
         POINT_LIMIT = Arrays
                .stream(Tool.Type.values())
                .min(Comparator.comparing(type -> type.getPointPrice()))
                .get()
                .getPointPrice();
    }

    @Override
    public Player.Status arrive(Player player) {
        if(player.getPoints() < POINT_LIMIT)
            return Player.Status.END_TURN;
        return Player.Status.WAIT_RESPONSE;
    }
}
