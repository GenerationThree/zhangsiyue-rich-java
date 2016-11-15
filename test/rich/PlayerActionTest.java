package rich;

import org.junit.Test;
import rich.place.*;
import rich.tool.Tool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerActionTest {
    private final double INIT_BALANCE = 10000;
    private final double IN_BALANCE = 200;

    @Test
    public void should_move_to_specified_place() throws Exception {
        Player player = new Player();
        Place destination = mock(Place.class);

        player.moveTo(destination);

        assertThat(player.getCurrentPlace(), is(destination));
    }

    @Test
    public void should_decrease_balance_and_increase_estate_after_buy_empty() throws Exception {
        Estate estate = new Estate(IN_BALANCE);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE);

        player.buy();
        assertThat(player.getBalance(), is(INIT_BALANCE - IN_BALANCE));
        assertThat(player.getEstates().size(), is(1));
    }

    @Test
    public void should_not_buy_other_player_estate() throws Exception {
        Player otherPlayer = mock(Player.class);
        Estate estate = Estate.createEstateWithOwner(IN_BALANCE, otherPlayer);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE);

        player.buy();
        assertThat(player.getBalance(), is(INIT_BALANCE));
        assertThat(player.getEstates().size(), is(0));
    }

    @Test
    public void should_not_buy_estate_without_enough_balance() throws Exception {
        Estate estate = new Estate(IN_BALANCE);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, IN_BALANCE - 1);

        player.buy();
        assertThat(player.getBalance(), is(IN_BALANCE - 1));
        assertThat(player.getEstates().size(), is(0));

    }

    @Test
    public void should_decrease_balance_after_promote_estate() throws Exception {
        Estate estate = mock(Estate.class);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE, estate);
        when(estate.promote()).thenReturn(true);
        when(estate.getPrice()).thenReturn(IN_BALANCE);

        player.promoteEstate();

        assertThat(player.getBalance(), is(INIT_BALANCE - IN_BALANCE));
    }

    @Test
    public void should_not_decrease_balance_when_promote_estate_failed() throws Exception {
        Estate estate = mock(Estate.class);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE, estate);
        when(estate.promote()).thenReturn(false);

        player.promoteEstate();

        assertThat(player.getBalance(), is(INIT_BALANCE));
    }

    @Test
    public void should_not_promote_estate_with_out_enough_balance() throws Exception {
        Estate estate = mock(Estate.class);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, IN_BALANCE - 1, estate);
        when(estate.promote()).thenReturn(false);
        when(estate.getPrice()).thenReturn(IN_BALANCE);

        player.promoteEstate();

        assertThat(player.getBalance(), is(IN_BALANCE - 1));
    }

    @Test
    public void should_return_true_and_decrease_balance_after_pay_fee_to_other_player() throws Exception {
        Estate estate = mock(Estate.class);
        Player otherPlayer = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE, estate);
        when(estate.getOwner()).thenReturn(otherPlayer);
        when(estate.getLevel()).thenReturn(Estate.Level.ONE);
        when(estate.getPrice()).thenReturn(IN_BALANCE);
        final double fee = estate.getPrice() * estate.getLevel().getFeeTimes();

        player.payFee();

        assertThat(player.getBalance(), is(INIT_BALANCE - fee));
        assertThat(otherPlayer.getBalance(), is(INIT_BALANCE + fee));
    }

    @Test
    public void should_return_true_and_not_decrease_balance_after_pay_fee_when_have_free_turns() throws Exception {
        Estate estate = mock(Estate.class);
        Player player = Player.createPlayerWithFreeTimes(estate, INIT_BALANCE, 1);
        Player otherPlayer = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE, estate);
        when(estate.getOwner()).thenReturn(otherPlayer);
        when(estate.getLevel()).thenReturn(Estate.Level.ONE);
        when(estate.getPrice()).thenReturn(IN_BALANCE);

        player.payFee();

        assertThat(player.getBalance(), is(INIT_BALANCE));
        assertThat(otherPlayer.getBalance(), is(INIT_BALANCE));
    }

    @Test
    public void should_return_tre_and_not_decrease_balance_after_pay_fee_when_owner_is_in_hospital_and_prison() throws Exception {
        Estate estate = mock(Estate.class);
        Hospital hospital = new Hospital();
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE);
        Player otherPlayer = Player.createPlayerWithBalanceAndEstate(hospital, INIT_BALANCE,estate);
        when(estate.getOwner()).thenReturn(otherPlayer);

        player.payFee();

        assertThat(player.getBalance(), is(INIT_BALANCE));
        assertThat(otherPlayer.getBalance(), is(INIT_BALANCE));

        Prison prison = new Prison();
        otherPlayer = Player.createPlayerWithBalanceAndEstate(prison, INIT_BALANCE,estate);

        assertThat(player.getBalance(), is(INIT_BALANCE));
        assertThat(otherPlayer.getBalance(), is(INIT_BALANCE));
    }

    @Test
    public void should_buy_tool() throws Exception {
        Place toolHouse = mock(ToolHouse.class);
        Player player = Player.createPlayerWithPoints(toolHouse, 100);

        player.buyTool(1);

        assertThat(player.getTools().size(), is(1));
        assertThat(player.getPoints(), is(100 - Tool.Type.BLOCK.getPointPrice()));
    }

    @Test
    public void should_not_buy_tool_without_enough_points() throws Exception {
        Place toolHouse = mock(ToolHouse.class);
        Player player = Player.createPlayerWithPoints(toolHouse, Tool.Type.BLOCK.getPointPrice() - 1);

        player.buyTool(1);

        assertThat(player.getTools().size(), is(0));
        assertThat(player.getPoints(), is(Tool.Type.BLOCK.getPointPrice() - 1));
    }

    @Test
    public void should_not_buy_tool_more_than_ten() throws Exception {
        Place toolHouse = mock(ToolHouse.class);
        Player player = Player.createPlayerWithPoints(toolHouse, Tool.Type.BLOCK.getPointPrice()*11 + ToolHouse.POINT_LIMIT);
        for (int i = 0 ; i < 10; i++)
            player.buyTool(1);
        assertThat(player.getTools().size(), is(10));
        int prePoints = player.getPoints();

        player.buyTool(1);
        assertThat(player.getTools().size(), is(10));
        assertThat(player.getPoints(), is(prePoints));
    }

    @Test
    public void should_add_balance_when_player_sell_estate() throws Exception {
        Estate estate = Estate.createEstateWithOwner(IN_BALANCE, null);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE);
        player.buy();

        double preBalance = player.getBalance();
        Estate.Level level = estate.getLevel();
        assertThat(player.getEstates().size(), is(1));
        assertThat(preBalance, is(INIT_BALANCE - IN_BALANCE));

        player.sellEstate(estate);

        assertThat(player.getEstates().size(), is(0));
        assertThat(player.getBalance(), is(preBalance + estate.getPrice() * (level.ordinal() + 1) * 2));
    }

    @Test
    public void should_not_add_balance_when_sell_not_owned_estate() throws Exception {
        Estate estate = Estate.createEstateWithOwner(IN_BALANCE, null);
        Player player = Player.createPlayerWithBalanceAndEstate(estate, INIT_BALANCE);

        player.sellEstate(estate);

        assertThat(player.getEstates().size(), is(0));
        assertThat(player.getBalance(), is(INIT_BALANCE));
    }
}
