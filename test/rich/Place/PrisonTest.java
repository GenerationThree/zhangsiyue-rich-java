package rich.Place;

import org.junit.Test;
import rich.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PrisonTest {
    @Test
    public void should_return_end_turn_when_player_arrive_prison() throws Exception {
        Prison prison = new Prison();
        Player player = new Player();

        assertThat(prison.arrive(player), is(Player.Status.END_TURN));
        assertThat(player.getWaitTurn(), is(Prison.WAIT_TURN));
    }
}
