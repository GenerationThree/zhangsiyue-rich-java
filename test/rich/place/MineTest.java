package rich.place;

import org.junit.Test;
import rich.commander.Player;
import rich.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MineTest {
    @Test
    public void should_return_end_turn_and_gain_player_points_when_arrive_mine() throws Exception {
        int gainPoints = 20;
        Mine mine = new Mine(gainPoints);
        Player player = Player.createPlayerWithPoints(mine, 0);

        assertThat(mine.arrive(player), is(Status.END_TURN));
        assertThat(player.getPoints(), is(gainPoints));
    }
}
