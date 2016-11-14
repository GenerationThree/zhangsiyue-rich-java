package rich;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerActionTest {
    @Test
    public void should_move_to_specified_place() throws Exception {
        Player player = new Player();
        Place destination = mock(Place.class);

        player.moveTo(destination);

        assertThat(player.getCurrentPlace(), is(destination));
    }
}
