package rich;

import rich.commander.Player;
import rich.commander.GameControl;
import rich.command.Command;
import rich.command.command.*;
import rich.command.response.*;
import rich.map.Map;
import rich.place.Estate;
import rich.place.GiftHouse;
import rich.place.Place;
import rich.place.ToolHouse;
import rich.tool.Tool;

public class CommandConvertor {
    public static Command convert(String input, GameControl gameControl) {
        String[] inputContent = input.split(" ");

        if (inputContent.length == 1){
            if(gameControl.getStatus() == Status.WAIT_INIT_BALANCE){
                return new InitBalanceCommand(Double.valueOf(input));
            }
            if(gameControl.getStatus() == Status.WAIT_INIT_PLAYER){
                return new InitPlayerCommand(input);
            }
        }

        Player currentPlayer = gameControl.getCurrentPlayer();
        Map map = gameControl.getMap();
        Dice dice = gameControl.getDice();

        if (currentPlayer.getStatus() == Status.WAIT_COMMAND) {
            if (inputContent[0].equalsIgnoreCase("roll") && inputContent.length == 1) {
                return new RollCommand(map, dice);
            }
            if (inputContent[0].equalsIgnoreCase("sell") && inputContent.length == 2) {
                return new SellCommand(gameControl.getMap(), Integer.valueOf(inputContent[1]));
            }
            if (inputContent[0].equalsIgnoreCase("sellTool") && inputContent.length == 2) {
                return new SellToolCommand(Integer.valueOf(inputContent[1]));
            }
            if (inputContent[0].equalsIgnoreCase("bomb") && inputContent.length == 2) {
                int distance = Integer.valueOf(inputContent[1]);
                if (distance >= -10 && distance <= 10)
                    return new UseToolCommand(map, distance, Tool.Type.BOMB);
            }
            if (inputContent[0].equalsIgnoreCase("block") && inputContent.length == 2) {
                int distance = Integer.valueOf(inputContent[1]);
                if (distance >= -10 && distance <= 10)
                    return new UseToolCommand(map, distance, Tool.Type.BLOCK);
            }
            if (inputContent[0].equalsIgnoreCase("robot") && inputContent.length == 1) {
                return new UseToolCommand(map, 10, Tool.Type.BOMB);
            }
            if (inputContent[0].equalsIgnoreCase("query") && inputContent.length == 1) {
                return new QueryCommand();
            }

        }
        if(currentPlayer.getStatus() == Status.WAIT_RESPONSE){
            Place currentPlace = currentPlayer.getCurrentPlace();
            if(inputContent.length == 1) {
                if (currentPlace instanceof Estate) {
                    if (((Estate) currentPlace).getOwner() == null) {
                        if (inputContent[0].equalsIgnoreCase("y")) {
                            return new YesToBuyResponse();
                        }
                        if (inputContent[0].equalsIgnoreCase("n")) {
                            return new NoToBuyResponse();
                        }
                    }
                    if (((Estate) currentPlace).getOwner() == currentPlayer) {
                        if (inputContent[0].equalsIgnoreCase("y")) {
                            return new YesToPromoteResponse();
                        }
                        if (inputContent[0].equalsIgnoreCase("n")) {
                            new NoToPromoteResponse();
                        }
                    }
                }
                if (currentPlace instanceof GiftHouse){
                    return new SelectGiftResponse(inputContent[0]);
                }
                if(currentPlace instanceof ToolHouse){
                    if (inputContent[0].equalsIgnoreCase("f"))
                        return new QuiteToolHouseResponse();
                    else {
                        return new BuyToolResponse(inputContent[0]);
                    }
                }
            }
        }

        return null;
    }
}
