package rich.GameControl;

import rich.Dice;
import rich.Player;
import rich.Status;
import rich.map.Map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class GameControl {

    private List<Player> players;
    private Map map;
    private Dice dice;
    private Player currentPlayer;
    private double initBalance;

    public static final double INIT_BALANCE_LOW_LIMIT = 1000;
    public static final double INIT_BALANCE_HIGH_LIMIT = 50000;
    public static final double PLAYER_SUM_LIMIT = 4;


    public GameControl(Map map, Dice dice) {
        this.players = new ArrayList<>();
        this.map = map;
        this.dice = dice;
        currentPlayer = null;
        initBalance = 10000;
    }

    public static GameControl createGameWithPlayers(Map map, Dice dice, Player...players){
        GameControl gameControl = new GameControl(map, dice);
        gameControl.players.addAll(asList(players));
        return gameControl;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setInitBalance(double initBalance) throws Exception {
        if(initBalance < INIT_BALANCE_LOW_LIMIT || initBalance > INIT_BALANCE_HIGH_LIMIT)
            throw new Exception("初始资金超过限定范围! (" + INIT_BALANCE_LOW_LIMIT+ "-" + INIT_BALANCE_HIGH_LIMIT + ")");
        this.initBalance = initBalance;
    }

    public void addPlayer(int id) throws Exception {
        if(id < 1 || id > PLAYER_SUM_LIMIT)
            throw new Exception("玩家id超过限定范围! (1-" + PLAYER_SUM_LIMIT + ")");
        Player player = players.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(player != null)
            throw new Exception("玩家" + id + "已经存在!");
        players.add(new Player(id, initBalance));
    }

    public void startGame() {
        players.sort((p1, p2) -> p1.getId() > p2. getId() ? 1 : -1);
    }

    public void startTurn() {
        if (currentPlayer == null)
            currentPlayer = players.get(0);
        int currentIndex = players.indexOf(players);
        currentPlayer = players.get( (currentIndex + 1) % players.size() );
        currentPlayer.startTurn();
    }

    public Player findWinner() {
        List<Player> survivePlayers = players.stream().filter(player -> player.getStatus() != Status.END_GAME).collect(Collectors.toList());
        if(survivePlayers.size() == 1)
            return survivePlayers.get(0);
        return null;
    }
}
