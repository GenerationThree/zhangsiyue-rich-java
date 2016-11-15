package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.Status;
import rich.place.Place;
import rich.tool.Tool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class SellToolTest {

    private final int INIT_POINTS = 200;
    private Command sellToolCommand;
    private Place current;


    @Before
    public void setUp() throws Exception {
        sellToolCommand = new SellToolCommand(1);
        current = mock(Place.class);
    }

    @Test
    public void should_wait_command_and_sell_tool_if_sell_tool_command() throws Exception {
        Player player = Player.createPlayerWithPoints(current, INIT_POINTS);
        player.buyTool(1);
        int prePoints = player.getPoints();
        int preToolSum = player.getTools().size();

        player.executeCommand(sellToolCommand);

        assertThat(player.getStatus(), is(Status.WAIT_COMMAND));
        assertThat(player.getPoints(), is(prePoints + Tool.Type.BLOCK.getPointPrice()));
        assertThat(player.getTools().size(), is(preToolSum - 1));
    }

    @Test
    public void should_not_sell_tool_with_invalid_choice() throws Exception {
        Player player = Player.createPlayerWithPoints(current, INIT_POINTS);
        player.buyTool(1);
        int prePoints = player.getPoints();
        int preToolSum = player.getTools().size();
        sellToolCommand = new SellToolCommand(4);

        player.executeCommand(sellToolCommand);

        assertThat(player.getStatus(), is(Status.WAIT_COMMAND));
        assertThat(player.getPoints(), is(prePoints));
        assertThat(player.getTools().size(), is(preToolSum));

    }
}
