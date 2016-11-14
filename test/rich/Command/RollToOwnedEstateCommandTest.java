package rich.Command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Map;
import rich.Place.Estate;
import rich.Place.Place;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToOwnedEstateCommandTest {

    private Map map;
    private Dice dice;
    private Place starting;

    private final double INIT_BALANCE = 10000;
    private final double IN_BALANCE = 200;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        starting = mock(Place.class);
    }

    @Test
    public void should_wait_response_when_roll_owned_estate() throws Exception {
        Estate estate = mock(Estate.class);
        Player player = Player.createPlayerWithBalanceAndEstate(starting, IN_BALANCE, estate);
        when(estate.getOwner()).thenReturn(player);
        when(map.move(eq(starting), eq(1))).thenReturn(estate);
        RollCommand rollCommand = new RollCommand(map, dice);

        player.executeCommand(rollCommand);

        assertThat(player.getStatus(), is(Player.Status.WAIT_RESPONSE));
    }
}
