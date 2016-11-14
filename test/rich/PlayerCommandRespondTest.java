package rich;
import org.junit.Before;
import org.junit.Test;
import rich.command.Command;
import rich.command.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerCommandRespondTest {
    private Player player;
    private Command command;
    private Response response;

    @Before
    public void setUp() throws Exception {
        player = new Player();
        command = mock(Command.class);
        response = mock(Response.class);
    }

    @Test
    public void should_be_wait_command_after_execute_command_not_need_respond() throws Exception {
        when(command.execute(eq(player))).thenReturn(Player.Status.WAIT_COMMAND);

        player.executeCommand(command);

        assertThat(player.getStatus(), is(Player.Status.WAIT_COMMAND));
    }

    @Test
    public void should_wait_response_after_execute_command_need_response() throws Exception {
        when(command.execute(eq(player))).thenReturn(Player.Status.WAIT_RESPONSE);

        player.executeCommand(command);

        assertThat(player.getStatus(), is(Player.Status.WAIT_RESPONSE));
    }

    @Test
    public void should_be_end_turn_after_execute_command() throws Exception {
        when(command.execute(eq(player))).thenReturn(Player.Status.END_TURN);

        player.executeCommand(command);

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_be_end_turn_after_respond() throws Exception {
        when(command.execute(eq(player))).thenReturn(Player.Status.WAIT_RESPONSE);

        player.executeCommand(command);

        when(command.respond(eq(player), eq(response), any())).thenReturn(Player.Status.END_TURN);

        player.respond(response, "");

        assertThat(player.getStatus(), is(Player.Status.END_TURN));
    }

    @Test
    public void should_be_wait_response_after_respond() throws Exception {
        when(command.execute(eq(player))).thenReturn(Player.Status.WAIT_RESPONSE);

        player.executeCommand(command);

        when(command.respond(eq(player), eq(response), any())).thenReturn(Player.Status.WAIT_RESPONSE);

        player.respond(response, "");

        assertThat(player.getStatus(), is(Player.Status.WAIT_RESPONSE));
    }
}
