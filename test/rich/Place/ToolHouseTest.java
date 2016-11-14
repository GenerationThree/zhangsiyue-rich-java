package rich.Place;

import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ToolHouseTest {
    @Test
    public void should_return_wait_response_when_player_arrive_tool_house() throws Exception {
        ToolHouse toolHouse = new ToolHouse();
        Player player = Player.createPlayerWithPoints(toolHouse, 100);

        assertThat(toolHouse.arrive(player), is(Player.Status.WAIT_RESPONSE));
    }
}
