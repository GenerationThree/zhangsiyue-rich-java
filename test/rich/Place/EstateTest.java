package rich.place;

import org.junit.Test;
import rich.commander.Player;
import rich.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class EstateTest {
    private final double INIT_BALANCE = 10000;
    private final double IN_BALANCE = 200;

    @Test
    public void should_return_true_when_buy_empty() throws Exception {
        Estate estate = new Estate(200);
        Player player = mock(Player.class);

        assertThat(estate.buy(player), is(true));
        assertThat(estate.getOwner(), is(player));
    }

    @Test
    public void should_return_false_when_buy_other_player_estate() throws Exception {
        Player player = mock(Player.class);
        Player otherPlayer = mock(Player.class);
        Estate estate = Estate.createEstateWithOwner(200, otherPlayer);

        assertThat(estate.buy(player), is(false));
        assertThat(estate.getOwner(), is(otherPlayer));
    }

    @Test
    public void should_increase_level_after_promote() throws Exception {
        Player player = mock(Player.class);
        Estate estate = Estate.createEstateWithOwner(200, player);
        Estate.Level pre = estate.getLevel();

        assertThat(estate.promote(), is(true));
        assertThat(estate.getLevel(), is(pre.next()));

    }

    @Test
    public void should_not_increase_level_when_estate_is_top() throws Exception {
        Player player = mock(Player.class);
        Estate estate = Estate.createEstateWithOwner(200, player);
        estate.promote();
        estate.promote();
        estate.promote();

        Estate.Level pre = estate.getLevel();
        assertThat(pre, is(Estate.Level.TOP));
        assertThat(estate.promote(), is(false));
        assertThat(estate.getLevel(), is(Estate.Level.TOP));
    }

    @Test
    public void should_return_wait_response_when_player_arrive_to_empty() throws Exception {
        Estate estate = new Estate(200);
        Player player = mock(Player.class);

        assertThat(estate.arrive(player), is(Status.WAIT_RESPONSE));
    }

    @Test
    public void should_return_wait_response_when_player_arrive_to_owned_estate() throws Exception {
        Player player = mock(Player.class);
        Estate estate = Estate.createEstateWithOwner(200, player);

        assertThat(estate.arrive(player), is(Status.WAIT_RESPONSE));

    }

    @Test
    public void should_return_end_turn_when_player_arrive_to_other_estate() throws Exception {
        Player otherPlayer = Player.createPlayerWithBalanceAndEstate(null, INIT_BALANCE);
        Estate estate = Estate.createEstateWithOwner(IN_BALANCE, otherPlayer);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE);
        final double fee = estate.getPrice() * estate.getLevel().getFeeTimes();

        assertThat(estate.arrive(player), is(Status.END_TURN));
        assertThat(player.getBalance(), is(INIT_BALANCE - fee));
        assertThat(otherPlayer.getBalance(), is(INIT_BALANCE + fee));
    }

    @Test
    public void should_return_end_game_when_player_arrive_to_other_estate_with_out_enough_balance_for_fee() throws Exception {
        Player otherPlayer = Player.createPlayerWithBalanceAndEstate(null, INIT_BALANCE);
        Estate estate = Estate.createEstateWithOwner(IN_BALANCE, otherPlayer);
        final double fee = estate.getPrice() * estate.getLevel().getFeeTimes();
        Player player = Player.createPlayerWithBalanceAndEstate(estate, fee - 1);

        assertThat(estate.arrive(player), is(Status.LOSE_GAME));
        assertThat(otherPlayer.getBalance(), is(INIT_BALANCE));
    }
}
