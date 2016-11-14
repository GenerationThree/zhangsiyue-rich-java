package rich.map;

import rich.place.Place;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class GameMap implements Map{
    private List<Place> places;

    public GameMap(Place...places) {
        this.places = new ArrayList<Place>(){{
            addAll(asList(places));
        }};
    }

    @Override
    public Place move(Place currentPlace, int steps) {
        int destinationIndex = (places.indexOf(currentPlace) + steps) % places.size();

        return places.get(destinationIndex);
    }
}
