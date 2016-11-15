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
    public Status execute(Commander commander, String parameter) {
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

    public static Command YesToBuy = (player, parameter) -> {
        ((Player)player).buy();
        return Status.END_TURN;
    };

    public static Command NoToBuy = (player, parameter) -> Status.END_TURN;

    public static Command YesToPromote = (player, parameter) -> {
        ((Player)player).promoteEstate();
        return Status.END_TURN;
    };

    public static Command NoToPromote = (player, parameter) -> Status.END_TURN;

    public static Command BuyToll = (player, parameter) -> {
        int choice = Integer.valueOf(parameter);
        ((Player)player).buyTool(choice);
        if (((Player)player).getPoints() >= ToolHouse.POINT_LIMIT)
            return Status.WAIT_RESPONSE;
        else
            return Status.END_TURN;
    };

    public static Command QuiteToolHouse = (player, parameter) -> Status.END_TURN;

    public static Command SelectGift = (player, parameter) -> {
        int choice = Integer.valueOf(parameter);
        ((Player)player).selectGift(choice);
        return Status.END_TURN;
    };

    public static Command UseMagic = ((player, parameter) -> Status.END_TURN);

}
