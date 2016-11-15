package rich.map;

import org.junit.Before;
import org.junit.Test;
import rich.GameControl.GameControl;
import rich.Player;
import rich.place.Place;
import rich.tool.Tool;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapTest {
    private GameControl gameControl;

    @Before
    public void setUp() throws Exception {
        gameControl = mock(GameControl.class);
        when(gameControl.getPlayers()).thenReturn(new ArrayList<>());
    }

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

    @Test
    public void should_set_tool_at_specified_place() throws Exception {
        Place current = mock(Place.class);
        Place other = mock(Place.class);
        Place target = mock(Place.class);
        Map map = new GameMap(current, other, target);
        map.putInGame(gameControl);

        assertThat(map.useTool(current, -1, Tool.Type.BLOCK), is(true));

        assertThat(map.getTool(target).getType(), is(Tool.Type.BLOCK));
    }

    @Test
    public void should_remove_tools_in_distance_when_use_robot() throws Exception {
        Place current = mock(Place.class);
        Place other = mock(Place.class);
        Place target = mock(Place.class);
        Map map = new GameMap(current, target, other);
        map.putInGame(gameControl);

        map.useTool(current, 1, Tool.Type.BLOCK);
        map.useTool(current, 2, Tool.Type.BOMB);

        map.useTool(current, 1, Tool.Type.ROBOT);

        assertThat(map.getTool(target), nullValue());
        assertThat(map.getTool(other), notNullValue());
    }

    @Test
    public void should_not_set_tool_at_place_have_tool() throws Exception {
        Place current = mock(Place.class);
        Place other = mock(Place.class);
        Place target = mock(Place.class);
        Map map = new GameMap(current, target, other);
        map.putInGame(gameControl);

        assertThat(map.useTool(current, 1, Tool.Type.BLOCK), is(true));
        assertThat(map.useTool(current, 1, Tool.Type.BOMB), is(false));

        assertThat(map.getTool(target).getType(), is(Tool.Type.BLOCK));
    }

    @Test
    public void should_not_set_tool_at_place_with_player() throws Exception {
        Place current = mock(Place.class);
        Place other = mock(Place.class);
        Place target = mock(Place.class);
        Player otherPlayer = Player.createPlayerWithStarting(target);
        when(gameControl.getPlayers()).thenReturn(new ArrayList<Player>(){{
            add(otherPlayer);
        }});
        Map map = new GameMap(current, target, other);
        map.putInGame(gameControl);

        assertThat(map.useTool(current, 1, Tool.Type.BLOCK), is(false));
        assertThat(map.getTool(target), nullValue());
    }
}
