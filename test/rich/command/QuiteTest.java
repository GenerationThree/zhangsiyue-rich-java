package rich.command;

import org.junit.Test;
import rich.Player;
import rich.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QuiteTest {
    @Test
    public void should_end_game_after_quite_command() throws Exception {
        Command quiteCommand = new QuiteCommand();
        Player player = new Player(1, 0);

        player.execute(quiteCommand, "");

        assertThat(player.getStatus(), is(Status.END_GAME));
    }
}
