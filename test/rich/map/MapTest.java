package rich.map;

import org.junit.Test;
import rich.place.Place;
import rich.tool.Tool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class MapTest {
    @Test
    public void should_return_target_place_when_move() throws Exception {
        Place startPoint = mock(Place.class);
        Place passByPoint = mock(Place.class);
        Place targetPoint = mock(Place.class);
        Map map = new GameMap(startPoint, passByPoint, targetPoint);

        assertThat(map.move(startPoint, 4), is(passByPoint));
    }

    @Test
    public void should_stop_when_pass_by_tool() throws Exception {
        Place startPoint = mock(Place.class);
        Place blockPoint = mock(Place.class);
        Place endPoint = mock(Place.class);

        Map map = GameMap.createMapWithTools(1, new Tool(Tool.Type.BLOCK), startPoint, blockPoint, endPoint);

        assertThat(map.move(startPoint, 2), is(blockPoint));
    }
}
