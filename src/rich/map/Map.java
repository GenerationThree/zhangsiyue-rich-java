package rich.map;

import rich.place.Place;

public interface Map {
    Place move(Place currentPlace, int steps);
}
