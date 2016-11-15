package rich.place;

import org.junit.Test;
import rich.commander.Player;
import rich.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ToolHouseTest {
    @Test
    public void should_return_wait_response_when_player_arrive_tool_house() throws Exception {
        ToolHouse toolHouse = new ToolHouse();
        Player player = Player.createPlayerWithPoints(toolHouse, 100);

        assertThat(toolHouse.arrive(player), is(Status.WAIT_RESPONSE));
    }

    @Test
    public void should_return_end_turn_when_player_with_not_enough_points_arrive() throws Exception {
        ToolHouse toolHouse = new ToolHouse();
        Player player = Player.createPlayerWithPoints(toolHouse, ToolHouse.POINT_LIMIT - 1);

        assertThat(toolHouse.arrive(player), is(Status.END_TURN));

    }
}
