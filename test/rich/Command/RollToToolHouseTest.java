package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Status;
import rich.command.command.RollCommand;
import rich.command.response.BuyToolResponse;
import rich.command.response.QuiteToolHouseResponse;
import rich.map.Map;
import rich.place.Place;
import rich.place.ToolHouse;
import rich.commander.Player;
import rich.tool.Tool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToToolHouseTest {
    private Map map;
    private Dice dice;
    private Place toolHouse;
    private Place starting;

    private RollCommand rollCommand;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        toolHouse = new ToolHouse();
        starting = mock(Place.class);
        when(map.move(any(), eq(1))).thenReturn(toolHouse);

        rollCommand = new RollCommand(map, dice);
    }

    @Test
    public void should_wait_response_when_arrive_tool_house() throws Exception {
        Player player = Player.createPlayerWithPoints(toolHouse, 100);

        player.execute(rollCommand);

        assertThat(player.getStatus(), is(Status.WAIT_RESPONSE));
    }

    @Test
    public void should_end_turn_when_arrive_tool_house_without_enough_points() throws Exception {
        Player player = Player.createPlayerWithPoints(toolHouse, ToolHouse.POINT_LIMIT - 1);

        player.execute(rollCommand);

        assertThat(player.getStatus(), is(Status.END_TURN));
    }

    @Test
    public void should_wait_response_when_respond_buy_tool_at_tool_house() throws Exception {
        Player player = Player.createPlayerWithPoints(toolHouse, 100);

        player.execute(rollCommand);

        player.execute(new BuyToolResponse("1"));

        assertThat(player.getStatus(), is(Status.WAIT_RESPONSE));
        assertThat(player.getPoints(), is(100 - Tool.Type.BLOCK.getPointPrice()));
    }

    @Test
    public void should_end_turn_when_after_respond_buy_tool_at_tool_house_without_enough_points() throws Exception {
        Player player = Player.createPlayerWithPoints(toolHouse, ToolHouse.POINT_LIMIT + Tool.Type.BLOCK.getPointPrice() -1);

        player.execute(rollCommand);

        player.execute(new BuyToolResponse("1"));

        assertThat(player.getStatus(), is(Status.END_TURN));
    }

    @Test
    public void should_end_turn_when_quite_tool_house() throws Exception {
        Player player = Player.createPlayerWithPoints(toolHouse, 100);

        player.execute(rollCommand);

        player.execute(new QuiteToolHouseResponse());

        assertThat(player.getStatus(), is(Status.END_TURN));

    }
}
