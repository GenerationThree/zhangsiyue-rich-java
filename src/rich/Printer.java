package rich;

import rich.commander.Player;
import rich.map.Map;
import rich.place.*;
import rich.tool.Tool;

import java.util.List;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class Printer {

    public static void printMap(Map map, Player currentPlayer) {

        List<Place> places = map.getPlaces();
        for (int i = 0; i < 29; i++)
            printPoint(map, places.get(i), currentPlayer);
        System.out.println();
        for (int i = 0; i < 6; i++) {
            printPoint(map, places.get(69 - i), currentPlayer);
            for (int j = 0; j < 27; j++)
                System.out.print(" ");
            printPoint(map, places.get(29 + i), currentPlayer);
            System.out.println();
        }
        for (int i = 0; i < 29; i++)
            printPoint(map, places.get(63 - i), currentPlayer);
        System.out.println();
    }

    private static void printPoint(Map map, Place place, Player currentPlayer){
        if(currentPlayer.getCurrentPlace() == place){
            System.out.print(currentPlayer.getAnsi().a(currentPlayer.getName()).reset());
        } else {
            if (place instanceof Estate) {
                Estate estate = (Estate) place;
                Player owner = estate.getOwner();
                if (owner != null) {
                    System.out.print(owner.getAnsi().a(estate.getLevel().ordinal()).reset());
                } else
                    System.out.print(estate.getLevel().ordinal());
            }
            Tool tool = map.getTool(place);
            if(tool != null){
                switch (tool.getType()){
                    case BOMB:
                        System.out.print("*");
                        break;
                    case BLOCK:
                        System.out.print("@");
                        break;
                    default:
                        break;
                }
            }else {
                if (place instanceof GiftHouse) {
                    System.out.print("G");
                }
                if (place instanceof Hospital) {
                    System.out.print("H");
                }
                if (place instanceof MagicHouse) {
                    System.out.print("M");
                }
                if (place instanceof Mine) {
                    System.out.print("$");
                }
                if (place instanceof Prison) {
                    System.out.print("P");
                }
                if (place instanceof ToolHouse) {
                    System.out.print("T");
                }
                if (place instanceof StartPoint) {
                    System.out.print("S");
                }
            }
        }
    }

    public static void printCurrentPlayerInfo(Player currentPlayer) {
        if (currentPlayer.getStatus() != Status.LOSE_GAME) {
            if (currentPlayer.getStatus() != Status.WAIT_RESPONSE) {
                System.out.print(currentPlayer.getName());
                if (currentPlayer.getFreeTurns() > -1) {
                    System.out.print(("(福神附身 :) )"));
                }
                System.out.print(ansi().eraseScreen().fg(BLACK).a("->"));
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
