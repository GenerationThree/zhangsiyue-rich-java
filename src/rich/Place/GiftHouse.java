package rich.place;

import rich.Printer;
import rich.commander.Player;
import rich.Status;

public class GiftHouse implements Place {
    @Override
    public Status arrive(Player player) {
        Printer.printMessage("欢迎光临礼品屋，请选择一件您喜欢的礼品：");
        return Status.WAIT_SELECT_GIFT_RESPONSE;
    }

}
