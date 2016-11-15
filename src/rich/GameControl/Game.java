package rich.GameControl;

import rich.Dice;
import rich.Player;
import rich.map.Map;

import java.util.ArrayList;
import java.util.List;

public class Game implements GameControl {
    private List<Player> players;
    private Map map;
    private Dice dice;
    private Player currentPlayer;
    private double initBalance;

    public static final double INIT_BALANCE_LOW_LIMIT = 1000;
    public static final double INIT_BALANCE_HIGH_LIMIT = 50000;
    public static final double PLAYER_SUM_LIMIT = 4;


    public Game(Map map, Dice dice) {
        this.players = new ArrayList<>();
        this.map = map;
        this.dice = dice;
        currentPlayer = null;
        initBalance = 10000;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void setInitBalance(double initBalance) throws Exception {
        if(initBalance < INIT_BALANCE_LOW_LIMIT || initBalance > INIT_BALANCE_HIGH_LIMIT)
            throw new Exception("初始资金超过限定范围! (" + INIT_BALANCE_LOW_LIMIT+ "-" + INIT_BALANCE_HIGH_LIMIT + ")");
        this.initBalance = initBalance;
    }

    @Override
    public void addPlayer(int id) throws Exception {
        if(id < 1 || id > PLAYER_SUM_LIMIT)
            throw new Exception("玩家id超过限定范围! (1-" + PLAYER_SUM_LIMIT + ")");
        Player player = players.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(player != null)
            throw new Exception("玩家" + id + "已经存在!");
        players.add(new Player(id, initBalance));
    }


}
