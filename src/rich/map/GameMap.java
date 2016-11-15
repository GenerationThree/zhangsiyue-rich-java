package rich.map;

import rich.place.Hospital;
import rich.place.Place;
import rich.tool.Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class GameMap implements Map{
    private List<Place> places;
    private java.util.Map tools;

    public GameMap(Place...places) {
        this.places = new ArrayList<Place>(){{
            addAll(asList(places));
        }};
        tools = new HashMap<>();
    }

    public static GameMap createMapWithTools(int position, Tool tool, Place...places){
        GameMap gameMap = new GameMap(places);
        gameMap.tools.put(position, tool);
        return gameMap;
    }

    @Override
    public Place move(Place currentPlace, int steps) {
        int currentPosition = places.indexOf(currentPlace);
        Place target = currentPlace;

        for (int i = 1; i <= steps; i++){
            target = places.get((currentPosition + i) % places.size());
            if(getTool(target) != null)
                return target;
        }
        return target;
    }

    public Tool getTool(Place place){
        return (Tool)tools.getOrDefault( places.indexOf(place), null);
    }

    public Place findHospital(){
        return places
                .stream()
                .filter(place -> place instanceof Hospital)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removeTool(Place place) {
        tools.remove(places.indexOf(place));
    }

    @Override
    public Place findPlace(int position) {
        return places.get(position % places.size());
    }
}
