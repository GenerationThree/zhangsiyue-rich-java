package rich.map;

import rich.commander.GameControl;
import rich.place.Place;
import rich.tool.Tool;

import java.util.List;

public interface Map {
    Place move(Place currentPlace, int steps);

    Tool getTool(Place place);

    Place findHospital();

    void removeTool(Place place);

    Place findPlace(int position);

    void putInGame(GameControl gameControlControl);

    boolean useTool(Place current, int distance, Tool.Type type);

    List<Place> getPlaces();

    Place findStartPoint();

}
