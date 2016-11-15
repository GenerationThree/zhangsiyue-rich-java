package rich;

import org.junit.Before;
import org.junit.Test;
import rich.GameControl.Game;
import rich.GameControl.GameControl;
import rich.map.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;

public class GameControlTest {
    private Map map;
    private Dice dice;

    @Before
    public void setUp() throws Exception {
        map = mock(Map.class);
        dice = mock(Dice.class);
    }

    @Test
    public void should_fail_when_set_invalid_init_balance() throws Exception {
        GameControl gameControl = new Game(map, dice);

        try {
            gameControl.setInitBalance(Game.INIT_BALANCE_HIGH_LIMIT - 1);
        }catch (Exception e){
            assertThat(e.getMessage().contains(contains("初始资金超过限定范围!")), is(true));
        }
    }

    @Test
    public void should_add_player() throws Exception {
        GameControl gameControl = new Game(map, dice);

        gameControl.addPlayer(2);

        assertThat(gameControl.getPlayers().get(0).getId(), is(2));
    }
}
