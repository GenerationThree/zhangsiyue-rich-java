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

public class RollToOtherEstateTest {
    private Map map;
    private Dice dice;
    private Place starting;
    private Estate otherEstate;
    private RollCommand rollCommand;
    private Player otherPlayer;

    private final double INIT_BALANCE = 10000;
    private final double IN_BALANCE = 200;


    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        starting = mock(Place.class);
        otherEstate = mock(Estate.class);
        otherPlayer = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE, otherEstate);
        when(otherEstate.getPrice()).thenReturn(IN_BALANCE);
        when(otherEstate.getLevel()).thenReturn(Estate.Level.ONE);
        rollCommand = new RollCommand(map, dice);

        when(dice.next()).thenReturn(1);
        when(map.move(eq(starting), eq(1))).thenReturn(otherEstate);
    }

    @Test
    public void should_end_turn_when_roll_to_other_estate() throws Exception {
        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE);

        player.executeCommand(rollCommand);

        assertThat(player.getStatus(), is(Player.Status.END_TURN));

    }
}
