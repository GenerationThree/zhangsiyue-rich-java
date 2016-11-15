package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.GameControl.GameControl;
import rich.Player;
import rich.map.GameMap;
import rich.map.Map;
import rich.place.Place;
import rich.tool.Tool;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UseToolTest {
    private final int INIT_POINTS = 200;
    private Command useToolCommand;
    private Place current;
    private Place target;
    private GameControl gameControl;

    @Before
    public void setUp() throws Exception {
        current = mock(Place.class);
        target = mock(Place.class);
        Map map = new GameMap(current, target);
        gameControl = mock(GameControl.class);
        when(gameControl.getPlayers()).thenReturn(new ArrayList<>());
        map.putInGame(gameControl);
        useToolCommand = new UseToolCommand(map, 1, Tool.Type.BLOCK);
        current = mock(Place.class);
    }

    @Test
    public void should_wait_command_after_use_tool_command() throws Exception {
        Player player = Player.createPlayerWithPoints(current, INIT_POINTS);
        player.buyTool(1);
        int preToolSum = player.getTools().size();

        player.executeCommand(useToolCommand);

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
        assertThat(player.getTools().size(), is(preToolSum - 1));
    }
}
