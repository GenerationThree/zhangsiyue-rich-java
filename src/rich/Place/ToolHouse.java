package rich.place;

import rich.Printer;
import rich.commander.Player;
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
        if(player.getPoints() < POINT_LIMIT) {
            Printer.printMessage("点数不足, 自动退出道具屋 :(");
            return Status.END_TURN;
        }
        Printer.printMessage("欢迎光临道具屋， 请选择您所需要的道具：");
        return Status.WAIT_BUY_TOOL_RESPONSE;
    }

}
