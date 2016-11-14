package rich;

import rich.Place.Place;

public interface Map {
    Place move(Place currentPlace, int steps);
}
