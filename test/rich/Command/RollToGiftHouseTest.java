package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Dice;
import rich.Status;
import rich.map.Map;
import rich.place.GiftHouse;
import rich.place.Place;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RollToGiftHouseTest {
    private Map map;
    private Dice dice;
    private Place giftHouse;
    private Place starting;

    private RollCommand rollCommand;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
        when(dice.next()).thenReturn(1);
        giftHouse = new GiftHouse();
        starting = mock(Place.class);
        when(map.move(any(), eq(1))).thenReturn(giftHouse);

        rollCommand = new RollCommand(map, dice);
    }

    @Test
    public void should_wait_response_when_roll_to_gift_house() throws Exception {
        Player player = new Player(1, 0);

        player.executeCommand(rollCommand);

        assertThat(player.getStatus(), is(Status.WAIT_RESPONSE));
    }

    @Test
    public void should_end_turn_and_get_right_gift_when_respond_select_gift() throws Exception {
        Player player = new Player(1, 0);

        player.executeCommand(rollCommand);
        player.respond(rollCommand.SelectGift, "1");

        assertThat(player.getBalance(), is(Double.valueOf("2000")));

        player.executeCommand(rollCommand);
        player.respond(rollCommand.SelectGift, "2");

        assertThat(player.getPoints(), is(200));

        player.executeCommand(rollCommand);
        player.respond(rollCommand.SelectGift, "3");

        assertThat(player.getFreeTurns(), is(5));
    }
}
