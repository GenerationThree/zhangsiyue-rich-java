package rich.command;

import org.junit.Before;
import org.junit.Test;
import rich.Player;
import rich.Status;
import rich.map.GameMap;
import rich.map.Map;
import rich.place.Estate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SellEstateTest {
    private final double IN_BALANCE = 200;
    private Command sellCommand;
    private Map map;
    private Estate estate;


    @Before
    public void setUp() throws Exception {
        estate = new Estate(IN_BALANCE);
        map = new GameMap(estate);
        sellCommand = new SellCommand(map, 0);

    }

    @Test
    public void should_wait_command_and_sell_estate_when_sell_estate_command() throws Exception {
        Player player = Player.createPlayerWithBalanceAndEstate(estate, IN_BALANCE);
        player.buy();
        Estate.Level level = estate.getLevel();
        double preBalance = player.getBalance();
        int preEstateNum = player.getEstates().size();

        player.execute(sellCommand, "");

        assertThat(player.getBalance(), is(preBalance + estate.getPrice() * 2 *(level.ordinal() + 1)));
        assertThat(player.getStatus(), is(Status.WAIT_COMMAND));
        assertThat(player.getEstates().size(), is(preEstateNum - 1));
    }
}
