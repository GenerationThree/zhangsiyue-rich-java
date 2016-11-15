package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.GameControl.GameControl;
import rich.Player;
import rich.Status;
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
    private GameControl gameControlControl;

    @Before
    public void setUp() throws Exception {
        current = mock(Place.class);
        target = mock(Place.class);
        Map map = new GameMap(current, target);
        gameControlControl = mock(GameControl.class);
        when(gameControlControl.getPlayers()).thenReturn(new ArrayList<>());
        map.putInGame(gameControlControl);
        useToolCommand = new UseToolCommand(map, 1, Tool.Type.BLOCK);
        current = mock(Place.class);
    }

    @Test
    public void should_wait_command_after_use_tool_command() throws Exception {
        Player player = Player.createPlayerWithPoints(current, INIT_POINTS);
        player.buyTool(1);
        int preToolSum = player.getTools().size();

        player.execute(useToolCommand, "");

        assertThat(player.getStatus(), is(Status.WAIT_COMMAND));
        assertThat(player.getTools().size(), is(preToolSum - 1));
    }
}
