package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Status;
import rich.map.Map;
import rich.place.Estate;
import rich.place.Place;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToOwnedEstateTest {

    private Map map;
    private Dice dice;
    private Place starting;

    private final double INIT_BALANCE = 10000;
    private final double IN_BALANCE = 200;
    private Estate estate;
    private RollCommand rollCommand;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        starting = mock(Place.class);
        estate = mock(Estate.class);
        rollCommand = new RollCommand(map, dice);

        when(dice.next()).thenReturn(1);
        when(map.move(eq(starting), eq(1))).thenReturn(estate);
    }

    @Test
    public void should_wait_response_when_roll_owned_estate() throws Exception {
        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE, estate);
        when(estate.arrive(player)).thenReturn(Status.WAIT_RESPONSE);

        player.executeCommand(rollCommand, "");

        assertThat(player.getStatus(), is(Status.WAIT_RESPONSE));
    }

    @Test
    public void should_end_turn_if_player_say_yes_at_owned_estate() throws Exception {
        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE, estate);
        when(estate.getOwner()).thenReturn(player);

        player.executeCommand(rollCommand, "");
        player.respond(rollCommand.YesToPromote, "");

        assertThat(player.getStatus(), is(Status.END_TURN));
    }

    @Test
    public void should_decrease_balance_if_say_yes_to_promote_and_promote_success() throws Exception {
        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE, estate);
        when(estate.getOwner()).thenReturn(player);
        when(estate.promote()).thenReturn(true);
        when(estate.getPrice()).thenReturn(IN_BALANCE);

        player.executeCommand(rollCommand, "");
        player.respond(rollCommand.YesToPromote, "");

        assertThat(player.getBalance(), is(INIT_BALANCE - IN_BALANCE));
    }

    @Test
    public void should_decrease_balance_if_say_yes_to_promote_and_promote_failed() throws Exception {
        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE, estate);
        when(estate.getOwner()).thenReturn(player);
        when(estate.promote()).thenReturn(false);
        when(estate.getPrice()).thenReturn(IN_BALANCE);

        player.executeCommand(rollCommand, "");
        player.respond(rollCommand.YesToPromote, "");

        assertThat(player.getBalance(), is(INIT_BALANCE));
    }

    @Test
    public void should_not_promote_when_say_yes_to_promote_without_enough_balance() throws Exception {
        Player player = Player.createPlayerWithBalanceAndEstate(starting, IN_BALANCE - 1, estate);
        when(estate.getOwner()).thenReturn(player);
        when(estate.getPrice()).thenReturn(IN_BALANCE);

        player.executeCommand(rollCommand, "");
        player.respond(rollCommand.YesToPromote, "");

        assertThat(player.getBalance(), is(IN_BALANCE - 1));
    }

    @Test
    public void should_end_turn_when_say_no_to_promote() throws Exception {
        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE, estate);

        player.executeCommand(rollCommand, "");
        player.respond(rollCommand.NoToPromote, "");

        assertThat(player.getBalance(), is(INIT_BALANCE));

    }
}
