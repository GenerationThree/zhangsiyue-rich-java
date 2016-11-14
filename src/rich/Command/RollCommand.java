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
        return  target.arrive(player);
    }

    @Override
    public Player.Status respond(Player player, Response response, String parameter) {
        return response.execute(player, parameter);
    }

    public static Response YesToBuy = (player,parameter) -> {
        player.buy();
        return Player.Status.END_TURN;
    };

    public static Response NoToBuy = (player,parameter) -> Player.Status.END_TURN;

    public static Response YesToPromote = (player,parameter) -> {
        player.promoteEstate();
        return Player.Status.END_TURN;
    };

    public static Response NoToPromote = (player,parameter) -> Player.Status.END_TURN;

    public static Response BuyToll = (player, parameter) -> {
        int choice = Integer.valueOf(parameter);
        player.buyTool(choice);
        return Player.Status.WAIT_RESPONSE;
    };

}
