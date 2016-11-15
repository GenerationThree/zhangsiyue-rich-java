package rich;

import org.junit.Before;
import org.junit.Test;
import rich.commander.Player;
import rich.commander.GameControl;
import rich.map.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        GameControl gameControlControl = new GameControl(map, dice);

        try {
            gameControlControl.setInitBalance(GameControl.INIT_BALANCE_HIGH_LIMIT - 1);
        }catch (Exception e){
            assertThat(e.getMessage().contains(contains("初始资金超过限定范围!")), is(true));
        }
    }

    @Test
    public void should_add_player() throws Exception {
        GameControl gameControlControl = new GameControl(map, dice);

        gameControlControl.addPlayer(2);

        assertThat(gameControlControl.getPlayers().get(0).getId(), is(2));
    }

    @Test
    public void should_find_winner() throws Exception {
        Player firstPlayer = mock(Player.class);
        Player secondPlayer = mock(Player.class);
        Player winnerPlayer = mock(Player.class);

        when(firstPlayer.getStatus()).thenReturn(Status.END_TURN);
        when(secondPlayer.getStatus()).thenReturn(Status.END_TURN);
        when(winnerPlayer.getStatus()).thenReturn(Status.END_TURN);

        GameControl gameControl = GameControl.createGameWithPlayers(map, dice, firstPlayer, secondPlayer, winnerPlayer);

        assertThat(gameControl.findWinner(), is(nullValue()));

        when(firstPlayer.getStatus()).thenReturn(Status.LOSE_GAME);
        when(secondPlayer.getStatus()).thenReturn(Status.LOSE_GAME);

        assertThat(gameControl.findWinner(), is(winnerPlayer));
    }
}
