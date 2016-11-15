package rich.place;

import org.junit.Test;
import rich.commander.Player;
import rich.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PrisonTest {
    @Test
    public void should_return_end_turn_when_player_arrive_prison() throws Exception {
        Prison prison = new Prison();
        Player player = new Player(1, 0);

        assertThat(prison.arrive(player), is(Status.END_TURN));
        assertThat(player.getWaitTurn(), is(Prison.WAIT_TURN));
    }
}
