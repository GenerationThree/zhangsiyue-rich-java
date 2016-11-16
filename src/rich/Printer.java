package rich;

import rich.commander.Player;
import rich.map.Map;
import rich.place.*;
import rich.tool.Tool;

public class Printer {

    public static void printMap(Map map, Player currentPlayer) {
        String mapImage = "";
        for (Place place : map.getPlaces()) {
            if (place instanceof Estate)
                mapImage += ((Estate) place).getLevel().ordinal();
            if (place instanceof GiftHouse) {
                mapImage += "G";
            }
            if (place instanceof Hospital) {
                mapImage += "H";
            }
            if (place instanceof MagicHouse) {
                mapImage += "M";
            }
            if (place instanceof Mine) {
                mapImage += "$";
            }
            if (place instanceof Prison) {
                mapImage += "P";
            }
            if (place instanceof ToolHouse) {
                mapImage += "T";
            }
            if (place instanceof StartPoint) {
                mapImage += "S";
            }
        }
        int index = map.getPlaces().indexOf(currentPlayer.getCurrentPlace());
        char[] charArray = mapImage.toCharArray();
        charArray[index] = currentPlayer.getName().toCharArray()[0];
        mapImage = String.valueOf(charArray);

        for (int i = 0; i < 29; i++)
            System.out.print(mapImage.charAt(i));
        System.out.println();
        for (int i = 0; i < 6; i++) {
            System.out.print(mapImage.charAt(69 - i));
            for (int j = 0; j < 27; j++)
                System.out.print(" ");
            System.out.print(mapImage.charAt(29 + i));
            System.out.println();
        }
        for (int i = 0; i < 29; i++)
            System.out.print(mapImage.charAt(63 - i));
        System.out.println();
    }

    public static void printCurrentPlayerInfo(Player currentPlayer) {
        if (currentPlayer.getStatus() != Status.LOSE_GAME) {
            if (currentPlayer.getStatus() != Status.WAIT_RESPONSE) {
                System.out.print(currentPlayer.getName());

                if (currentPlayer.getFreeTurns() > -1) {
                    System.out.print(("(福神附身 :) )"));
                }
                System.out.print("->");
                if (currentPlayer.getStatus() == Status.END_TURN)
                    System.out.println("End Turn");
                int waitTurn = currentPlayer.getWaitTurn();
                if (waitTurn > 0) {
                    if (currentPlayer.getCurrentPlace() instanceof Hospital) {
                        System.out.println("你还需要住院" + waitTurn + "天 TOT");
                    }
                    if (currentPlayer.getCurrentPlace() instanceof Prison) {
                        System.out.println("你还需要服刑" + waitTurn + "天 TOT");
                    }
                }
            }
        }
    }

    public static void printStatus(Player currentPlayer) {
        if (currentPlayer.getStatus() == Status.WAIT_RESPONSE) {
            Place place = currentPlayer.getCurrentPlace();
            if (place instanceof Estate) {
                Estate estate = ((Estate) place);
                if (estate.getOwner() == currentPlayer) {
                    System.out.print("是否升级该处地产，" + estate.getPrice() + "元（Y/N）:");
                }
                if (estate.getOwner() == null) {
                    System.out.print("是否购买该处空地，" + estate.getPrice() + "元（Y/N）:");
                }
            }
        }
        if (currentPlayer.getStatus() == Status.LOSE_GAME)
            System.out.println("破产啦 TOT");
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printAsset(Player currentPlayer) {
        System.out.println("资金: " + currentPlayer.getBalance() + "元");
        System.out.println("点数: " + currentPlayer.getPoints() + "点");
        long zeroLevelSum = currentPlayer
                .getEstates()
                .stream()
                .filter(place -> ((Estate) place).getLevel() == Estate.Level.ZERO)
                .count();
        long oneLevelSum = currentPlayer
                .getEstates()
                .stream()
                .filter(place -> ((Estate) place).getLevel() == Estate.Level.ONE)
                .count();
        long twoLevelSum = currentPlayer
                .getEstates()
                .stream()
                .filter(place -> ((Estate) place).getLevel() == Estate.Level.TWO)
                .count();
        long topLevelSum = currentPlayer
                .getEstates()
                .stream()
                .filter(place -> ((Estate) place).getLevel() == Estate.Level.TOP)
                .count();
        System.out.println("地产: 空地" + zeroLevelSum +
                "处; 茅屋" + oneLevelSum +
                "处; 洋房" + twoLevelSum +
                "处; 摩天楼" + topLevelSum + "处");
        long blockSum = currentPlayer
                .getTools()
                .stream()
                .filter(tool -> tool.getType() == Tool.Type.BLOCK)
                .count();
        long bombSum = currentPlayer
                .getTools()
                .stream()
                .filter(tool -> tool.getType() == Tool.Type.BOMB)
                .count();
        long robotSum = currentPlayer
                .getTools()
                .stream()
                .filter(tool -> tool.getType() == Tool.Type.ROBOT)
                .count();
        System.out.println("道具: 路障" + blockSum +
                "个; 炸弹" + bombSum +
                "个; 机器娃娃" + robotSum + "个");
    }

}
