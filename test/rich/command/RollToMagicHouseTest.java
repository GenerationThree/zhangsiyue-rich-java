package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Player;
import rich.Status;
import rich.map.Map;
import rich.place.MagicHouse;
import rich.place.Place;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToMagicHouseTest {
    private Map map;
    private Dice dice;
    private Place magicHouse;
    private Place starting;

    private RollCommand rollCommand;


    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        magicHouse = new MagicHouse();
        starting = mock(Place.class);
        when(map.move(eq(starting), eq(1))).thenReturn(magicHouse);

        rollCommand = new RollCommand(map, dice);
    }

    @Test
    public void should_wait_response_when_roll_to_magic_house() throws Exception {
        Player player = Player.createPlayerWithStarting(starting);

        player.executeCommand(rollCommand);

        assertThat(player.getStatus(), is(Status.WAIT_RESPONSE));
    }

    @Test
    public void should_end_turn_after_response_at_magicHouse() throws Exception {
        Player player = Player.createPlayerWithStarting(starting);

        player.executeCommand(rollCommand);

        player.respond(rollCommand.UseMagic, "");

        assertThat(player.getStatus(), is(Status.END_TURN));

    }
}
