package rich.Command;

import rich.Dice;
import rich.Map;
import rich.Place.Place;
import rich.Player;

public class RollCommand implements Command{
    private Map map;
    private Dice dice;

    public RollCommand(Map map, Dice dice) {
        this.map = map;
        this.dice = dice;
    }

    @Override
    public Player.Status execute(Player player) {
        Place target = map.move(player.getCurrentPlace(), dice.next());
        player.moveTo(target);
        return Player.Status.WAIT_RESPONSE;
    }

    @Override
    public Player.Status respond(Player player, Response response) {
        return response.execute(player);
    }

    public static Response YesToBuy = player -> {
        player.buy();
        return Player.Status.END_TURN;
    };

}
