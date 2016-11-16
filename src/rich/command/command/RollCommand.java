package rich.command.command;

import rich.Printer;
import rich.commander.Commander;
import rich.Dice;
import rich.Status;
import rich.command.Command;
import rich.map.Map;
import rich.place.Hospital;
import rich.place.Place;
import rich.commander.Player;
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
                player.moveTo(target);
            }
        } else
            player.moveTo(target);
        return target.arrive(player);
    }

}
