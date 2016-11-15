package rich;

import org.junit.Test;
import rich.GameControl.Game;
import rich.GameControl.GameControl;
import rich.map.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;

public class GameControlTest {
    @Test
    public void should_fail_when_set_invalid_init_balance() throws Exception {
        Map map = mock(Map.class);
        Dice dice = mock(Dice.class);

        GameControl gameControl = new Game(map, dice);

        try {
            gameControl.setInitBalance(Game.INIT_BALANCE_HIGH_LIMIT - 1);
        }catch (Exception e){
            assertThat(e.getMessage().contains(contains("初始资金超过限定范围!")), is(true));
        }
    }


}
