package rich.command;

import rich.Dice;
import rich.map.Map;
import rich.place.Place;
import rich.place.ToolHouse;
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
        if(player.getPoints() >= ToolHouse.POINT_LIMIT)
            return Player.Status.WAIT_RESPONSE;
        else
            return Player.Status.END_TURN;
    };

    public static Response QuiteToolHouse = (player,parameter) -> Player.Status.END_TURN;

    public static Response SelectGift = (player,parameter) -> {
        int choice = Integer.valueOf(parameter);
        player.selectGift(choice);
        return Player.Status.END_TURN;
    };

}
