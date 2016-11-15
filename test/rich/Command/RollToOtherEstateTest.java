package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Status;
import rich.command.command.RollCommand;
import rich.map.Map;
import rich.place.Estate;
import rich.place.Place;
import rich.commander.Player;

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
        otherPlayer = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE, otherEstate);
        otherEstate = Estate.createEstateWithOwner(IN_BALANCE, otherPlayer);
        rollCommand = new RollCommand(map, dice);

        when(dice.next()).thenReturn(1);
        when(map.move(eq(starting), eq(1))).thenReturn(otherEstate);
    }

    @Test
    public void should_end_turn_when_roll_to_other_estate() throws Exception {
        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE);

        player.execute(rollCommand);

        assertThat(player.getStatus(), is(Status.END_TURN));
    }

    @Test
    public void should_pay_fee_when_roll_to_other_estate() throws Exception {
        final double fee = otherEstate.getPrice() * otherEstate.getLevel().getFeeTimes();
        Player player = Player.createPlayerWithBalanceAndEstate(starting, INIT_BALANCE);

        player.execute(rollCommand);

        assertThat(player.getBalance(), is(INIT_BALANCE - fee));
        assertThat(otherPlayer.getBalance(), is(INIT_BALANCE + fee));
    }

    @Test
    public void should_end_game_when_pay_fee_failed_when_roll_to_other_land() throws Exception {
        final double fee = otherEstate.getPrice() * otherEstate.getLevel().getFeeTimes();
        Player player = Player.createPlayerWithBalanceAndEstate(starting, fee - 1);

        player.execute(rollCommand);

        assertThat(player.getStatus(), is(Status.LOSE_GAME));
    }
}
