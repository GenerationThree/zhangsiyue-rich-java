package rich.Command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Map;
import rich.Place.Estate;
import rich.Place.Place;
import rich.Place.Prison;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToPrisonTest {
    private Map map;
    private Dice dice;
    private Place prison;
    private Place starting;

    private RollCommand rollCommand;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        prison = new Prison();
        starting = mock(Place.class);
        when(map.move(eq(starting), eq(1))).thenReturn(prison);

        rollCommand = new RollCommand(map, dice);
    }

    @Test
    public void should_end_turn_when_roll_to_prison() throws Exception {
        Player player = Player.createPlayerWithStarting(starting);

        player.executeCommand(rollCommand);

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
        assertThat(player.getWaitTurn(), is(Prison.WAIT_TURN));
    }
}