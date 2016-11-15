package rich.map;

import rich.GameControl.GameControl;
import rich.place.Place;
import rich.tool.Tool;

public interface Map {
    Place move(Place currentPlace, int steps);

    Tool getTool(Place place);

    Place findHospital();

    void removeTool(Place place);

    Place findPlace(int position);

    void putInGame(GameControl gameControl);

    boolean useTool(Place current, int distance, Tool.Type type);

}
