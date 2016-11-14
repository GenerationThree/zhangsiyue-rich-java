package rich.Command;

import rich.Dice;
import rich.Map;
import rich.Place.Estate;
import rich.Place.Place;
import rich.Player;

public class RollCommand implements Command {
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
        if (target instanceof Estate){
            Estate estate = ((Estate) target);
            if (estate.getOwner() == null || estate.getOwner() == player)
                return Player.Status.WAIT_RESPONSE;
            else {
                player.payFee();
                return Player.Status.END_TURN;
            }
        }
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

    public static Response NoToBuy = player -> Player.Status.END_TURN;

    public static Response YesToPromote = player -> {
        player.promoteEstate();
        return Player.Status.END_TURN;
    };

    public static Response NoToPromote = player -> Player.Status.END_TURN;

}
