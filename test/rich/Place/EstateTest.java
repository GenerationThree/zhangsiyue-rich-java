package rich.Place;

import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class EstateTest {
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
}