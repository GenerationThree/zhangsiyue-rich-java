package rich.command;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Player;
import rich.map.Map;
import rich.place.Place;

public class SellCommand implements Command{
    private Map map;
    private int position;

    public SellCommand(Map map, int position) {
        this.map = map;
        this.position = position;
    }


    @Override
    public Player.Status execute(Player player) {
        Place target = map.findPlace(position);
        player.sellEstate(target);
        return Player.Status.WAIT_COMMAND;
    }

    @Override
    public Player.Status respond(Player player, Response response, String parameter) {
        throw new UnsupportedMediaException();
    }
}
