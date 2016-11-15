package rich.place;

import rich.Player;
import rich.Status;
import rich.tool.Tool;

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
    public Status arrive(Player player) {
        if(player.getPoints() < POINT_LIMIT)
            return Status.END_TURN;
        return Status.WAIT_RESPONSE;
    }
}
