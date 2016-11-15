package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Player;
import rich.Status;
import rich.map.GameMap;
import rich.map.Map;
import rich.place.Hospital;
import rich.place.Place;
import rich.tool.Tool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollPassByBombTest {
    private Map map;
    private Dice dice;
    private Place starting;
    private Hospital hospital;
    private Place bombPoint;
    private Place endpoint;
    private RollCommand rollCommand;

    @Before
    public void setUp() throws Exception {
        dice = mock(Dice.class);
        when(dice.next()).thenReturn(3);
        bombPoint = mock(Place.class);
        starting = mock(Place.class);
        endpoint = mock(Place.class);
        hospital = new Hospital();
        map = GameMap.createMapWithTools(1, new Tool(Tool.Type.BOMB), starting, bombPoint, hospital, endpoint);

        rollCommand = new RollCommand(map, dice);
    }

    @Test
    public void should_end_turn_and_go_to_hospital_when_pass_by_bomb() throws Exception {
        Player player = new Player(1, 0);

        player.executeCommand(rollCommand);

        assertThat(player.getCurrentPlace(), is(hospital));
        assertThat(player.getStatus(), is(Status.END_TURN));
        assertThat(player.getWaitTurn(), is(3));
    }
}
