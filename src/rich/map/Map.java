package rich.map;

import rich.place.Place;
import rich.tool.Tool;

public interface Map {
    Place move(Place currentPlace, int steps);

    public Tool getTool(Place place);

    public Place findHospital();

    public void removeTool(Place place);
}
