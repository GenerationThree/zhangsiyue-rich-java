package rich.command;

import rich.Commander;
import rich.Dice;
import rich.Status;
import rich.map.Map;
import rich.place.Hospital;
import rich.place.Place;
import rich.place.ToolHouse;
import rich.Player;
import rich.tool.Tool;

public class RollCommand implements Command {
    private Map map;
    private Dice dice;

    public RollCommand(Map map, Dice dice) {
        this.map = map;
        this.dice = dice;
    }

    @Override
    public Status execute(Commander commander) {
        Player player = (Player)commander;
        Place target = map.move(player.getCurrentPlace(), dice.next());
        Tool tool = map.getTool(target);
        if (tool != null) {
            map.removeTool(target);
            if (tool.getType() == Tool.Type.BLOCK) {
                player.moveTo(target);
            }
            if (tool.getType() == Tool.Type.BOMB) {
                Hospital hospital = (Hospital) map.findHospital();
                player.goToHospital(hospital);
                target = hospital;
            }
        } else
            player.moveTo(target);
        return target.arrive(player);
    }

    @Override
    public Status respond(Commander commander, Response response, String parameter) {
        return response.execute(((Player)commander), parameter);
    }

    public static Response YesToBuy = (player, parameter) -> {
        player.buy();
        return Status.END_TURN;
    };

    public static Response NoToBuy = (player, parameter) -> Status.END_TURN;

    public static Response YesToPromote = (player, parameter) -> {
        player.promoteEstate();
        return Status.END_TURN;
    };

    public static Response NoToPromote = (player, parameter) -> Status.END_TURN;

    public static Response BuyToll = (player, parameter) -> {
        int choice = Integer.valueOf(parameter);
        player.buyTool(choice);
        if (player.getPoints() >= ToolHouse.POINT_LIMIT)
            return Status.WAIT_RESPONSE;
        else
            return Status.END_TURN;
    };

    public static Response QuiteToolHouse = (player, parameter) -> Status.END_TURN;

    public static Response SelectGift = (player, parameter) -> {
        int choice = Integer.valueOf(parameter);
        player.selectGift(choice);
        return Status.END_TURN;
    };

    public static Response UseMagic = ((player, parameter) -> Status.END_TURN);

}
