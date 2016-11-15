package rich.place;

import org.junit.Test;
import rich.commander.Player;
import rich.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class GiftHouseTest {
    @Test
    public void should_return_wait_response_when_arrive_gift_house() throws Exception {
        GiftHouse giftHouse = new GiftHouse();
        Player player = mock(Player.class);

        assertThat(giftHouse.arrive(player), is(Status.WAIT_RESPONSE));
    }
}
