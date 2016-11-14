package rich;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerCommandRespondTest {
    private Player player;
    private Command command;

    @Before
    public void setUp() throws Exception {
        player = new Player();
        command = mock(Command.class);
    }

    @Test
    public void should_be_wait_command_after_exect_command_not_need_respond() throws Exception {
        when(command.execute(eq(player))).thenReturn(Player.Status.WAIT_COMMAND);

        player.executeCommand(command);

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
    }
}
