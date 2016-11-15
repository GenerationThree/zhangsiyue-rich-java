package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Player;
import rich.Status;
import rich.command.command.RollCommand;
import rich.map.Map;
import rich.place.Mine;
import rich.place.Place;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToMineTest {
    private Map map;
    private Dice dice;
    private Place mine;
    private Place starting;

    private RollCommand rollCommand;

    private final int GAIN_POINTS = 20;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        mine = new Mine(GAIN_POINTS);
        starting = mock(Place.class);
        when(map.move(any(), eq(1))).thenReturn(mine);

        rollCommand = new RollCommand(map, dice);
    }

    @Test
    public void should_end_turn_and_gain_points_after_roll_to_mine() throws Exception {
        Player player = Player.createPlayerWithPoints(starting, 0);

        player.execute(rollCommand);

        assertThat(player.getStatus(), is(Status.END_TURN));
        assertThat(player.getPoints(), is(GAIN_POINTS));
    }
}
