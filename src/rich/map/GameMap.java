package rich.map;

import rich.GameControl.GameControl;
import rich.Player;
import rich.place.Hospital;
import rich.place.Place;
import rich.tool.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

public class GameMap implements Map {
    private List<Place> places;
    private java.util.Map toolSetList;
    private GameControl gameControl;

    public GameMap(Place... places) {
        this.places = new ArrayList<Place>() {{
            addAll(asList(places));
        }};
        toolSetList = new HashMap<>();
    }

    public static GameMap createMapWithTools(int position, Tool tool, Place... places) {
        GameMap gameMap = new GameMap(places);
        gameMap.toolSetList.put(position, tool);
        return gameMap;
    }

    @Override
    public Place move(Place currentPlace, int steps) {
        int currentPosition = places.indexOf(currentPlace);
        Place target = currentPlace;

        for (int i = 1; i <= steps; i++) {
            target = places.get((currentPosition + i) % places.size());
            if (getTool(target) != null)
                return target;
        }
        return target;
    }

    @Override
    public Tool getTool(Place place) {
        return (Tool) toolSetList.getOrDefault(places.indexOf(place), null);
    }

    @Override
    public Place findHospital() {
        return places
                .stream()
                .filter(place -> place instanceof Hospital)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removeTool(Place place) {
        toolSetList.remove(places.indexOf(place));
    }

    @Override
    public Place findPlace(int position) {
        return places.get(position % places.size());
    }

    @Override
    public void putInGame(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    @Override
    public boolean useTool(Place current, int distance, Tool.Type type) {
        int currentPosition = places.indexOf(current);
        if (type == Tool.Type.ROBOT) {
            for (int i = 1; i <= distance; i++) {
                int targetPosition = (currentPosition + i) % places.size();
                if (toolSetList.get(targetPosition) != null) {
                    toolSetList.remove(targetPosition);
                }
            }
            return true;
        }
        int length = places.size();
        int targetPosition = distance + currentPosition;

        targetPosition = targetPosition < 0 ? targetPosition % length + length : targetPosition % length;
        for (Player player : gameControl.getPlayers()){
            if(player.getCurrentPlace().equals(places.get(targetPosition)))
                return false;
        }
        if (toolSetList.getOrDefault(targetPosition, null) == null ) {
            toolSetList.put(targetPosition, new Tool(type));
            return true;
        }
        return false;
    }
}
