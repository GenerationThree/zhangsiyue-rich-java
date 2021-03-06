package rich;
import org.junit.Before;
import org.junit.Test;
import rich.command.Command;
import rich.commander.Player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerCommandRespondTest {
    private Player player;
    private Command command;
    private Command response;

    @Before
    public void setUp() throws Exception {
        player = new Player(1, 0);
        command = mock(Command.class);
        response = mock(Command.class);
    }

    @Test
    public void should_be_wait_command_after_execute_command_not_need_respond() throws Exception {
        when(command.execute(eq(player))).thenReturn(Status.WAIT_COMMAND);

        player.execute(command);

        assertThat(player.getStatus(), is(Status.WAIT_COMMAND));
    }

    @Test
    public void should_wait_response_after_execute_command_need_response() throws Exception {
        when(command.execute(eq(player))).thenReturn(Status.WAIT_RESPONSE);

        player.execute(command);

        assertThat(player.getStatus(), is(Status.WAIT_RESPONSE));
    }

    @Test
    public void should_be_end_turn_after_execute_command() throws Exception {
        when(command.execute(eq(player))).thenReturn(Status.END_TURN);

        player.execute(command);

        assertThat(player.getStatus(), is(Status.END_TURN));
    }

    @Test
    public void should_be_end_turn_after_respond() throws Exception {
        when(command.execute(eq(player))).thenReturn(Status.WAIT_RESPONSE);

        player.execute(command);

        when(response.execute(eq(player))).thenReturn(Status.END_TURN);

        player.execute(response);

        assertThat(player.getStatus(), is(Status.END_TURN));
    }

    @Test
    public void should_be_wait_response_after_respond() throws Exception {
        when(command.execute(eq(player))).thenReturn(Status.WAIT_RESPONSE);

        player.execute(command);

        when(response.execute(eq(player))).thenReturn(Status.WAIT_RESPONSE);


        player.execute(response);

        assertThat(player.getStatus(), is(Status.WAIT_RESPONSE));
    }
}
