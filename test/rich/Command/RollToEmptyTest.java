package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Status;
import rich.command.command.RollCommand;
import rich.command.response.NoToBuyResponse;
import rich.command.response.YesToBuyResponse;
import rich.map.Map;
import rich.place.Estate;
import rich.place.Place;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToEmptyTest {

    private Map map;
    private Dice dice;
    private Place empty;
    private Place starting;

    private final double INIT_BALANCE = 10000;
    private final double IN_BALANCE = 200;
    private RollCommand rollCommand;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        empty = new Estate(200);
        starting = mock(Place.class);
        when(map.move(eq(starting), eq(1))).thenReturn(empty);

        rollCommand = new RollCommand(map, dice);
    }

    @Test
    public void should_be_wait_response_after_roll_to_empty() throws Exception {
        Player player = Player.createPlayerWithStarting(starting);

        player.execute(rollCommand);

        assertThat(player.getStatus(), is(Status.WAIT_RESPONSE));
    }

    @Test
    public void should_end_turn_when_user_respond_yes_at_empty() throws Exception {
        Estate empty = new Estate(IN_BALANCE);

        when(map.move(eq(starting), eq(1))).thenReturn(empty);

        Player player = Player.createPlayerWithBalanceAndEstate(starting, IN_BALANCE);

        player.execute(rollCommand);

        player.execute(new YesToBuyResponse());
        assertThat(player.getStatus(), is(Status.END_TURN));
    }

    @Test
    public void should_buy_empty_when_user_respond_yes_to_buy() throws Exception {
        Estate empty = new Estate(IN_BALANCE);

        when(map.move(eq(starting), eq(1))).thenReturn(empty);

        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE);

        player.execute(rollCommand);

        player.execute(new YesToBuyResponse());
        assertThat(player.getEstates().size(), is(1));
        assertThat(player.getBalance(), is(INIT_BALANCE - IN_BALANCE));
    }

    @Test
    public void should_end_turn_when_say_no_at_empty_land() throws Exception {
        Estate empty = new Estate(IN_BALANCE);

        when(map.move(eq(starting), eq(1))).thenReturn(empty);

        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE);

        player.execute(rollCommand);

        player.execute(new NoToBuyResponse());
        assertThat(player.getStatus(), is(Status.END_TURN));
        assertThat(player.getEstates().size(), is(0));
        assertThat(player.getBalance(), is(INIT_BALANCE));
    }
}
