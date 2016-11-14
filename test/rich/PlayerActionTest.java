package rich;

import org.junit.Test;
import rich.Place.Estate;
import rich.Place.Place;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerActionTest {
    private final double INIT_BALANCE = 10000;
    private final double IN_BALANCE = 200;

    @Test
    public void should_move_to_specified_place() throws Exception {
        Player player = new Player();
        Place destination = mock(Place.class);

        player.moveTo(destination);

        assertThat(player.getCurrentPlace(), is(destination));
    }

    @Test
    public void should_decrease_balance_and_increase_estate_after_buy_empty() throws Exception {
        Estate estate = new Estate(IN_BALANCE);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE);

        player.buy();
        assertThat(player.getBalance(), is(INIT_BALANCE - IN_BALANCE));
        assertThat(player.getEstates().size(), is(1));
    }

    @Test
    public void should_not_buy_other_player_estate() throws Exception {
        Player otherPlayer = mock(Player.class);
        Estate estate = Estate.createEstateWithOwner(IN_BALANCE, otherPlayer);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE);

        player.buy();
        assertThat(player.getBalance(), is(INIT_BALANCE));
        assertThat(player.getEstates().size(), is(0));
    }

    @Test
    public void should_not_buy_estate_without_enough_balance() throws Exception {
        Estate estate = new Estate(IN_BALANCE);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, IN_BALANCE - 1);

        player.buy();
        assertThat(player.getBalance(), is(IN_BALANCE - 1));
        assertThat(player.getEstates().size(), is(0));

    }
}
