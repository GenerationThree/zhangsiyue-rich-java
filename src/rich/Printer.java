package rich;

import rich.commander.GameControl;
import rich.commander.Player;
import rich.map.Map;
import rich.place.*;

import java.util.List;

public class Printer {

    public static void printMap(Map map, Player currentPlayer){
        String mapImage = "";
        for (Place place : map.getPlaces()){
            if(place instanceof Estate)
                mapImage += ((Estate) place).getLevel().ordinal();
            if(place instanceof GiftHouse){
                mapImage += "G";
            }
            if(place instanceof Hospital){
                mapImage += "H";
            }
            if(place instanceof MagicHouse){
                mapImage += "M";
            }
            if(place instanceof Mine){
                mapImage += "$";
            }
            if(place instanceof Prison){
                mapImage += "P";
            }
            if(place instanceof ToolHouse){
                mapImage += "T";
            }
            if(place instanceof StartPoint){
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
}
