package rich.command;

import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import rich.Commander;
import rich.Player;
import rich.Status;
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
    public Status execute(Commander player) {
        Place target = map.findPlace(position);
        ((Player)player).sellEstate(target);
        return Status.WAIT_COMMAND;
    }

    @Override
    public Status respond(Commander player, Response response, String parameter) {
        throw new UnsupportedMediaException();
    }
}
