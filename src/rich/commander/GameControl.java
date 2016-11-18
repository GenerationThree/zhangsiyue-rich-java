package rich.commander;

import rich.*;
import rich.command.Command;
import rich.map.GameMap;
import rich.map.Map;
import rich.place.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class GameControl implements Commander {

    private List<Player> players;
    private Map map;
    private Dice dice;
    private Player currentPlayer;
    private double initBalance;
    private Status status;
    private Player winner;

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

    public GameControl(){
        this.players = new ArrayList<>();
        this.map = initMap();
        this.dice = new GameDice();
        currentPlayer = null;
        initBalance = 10000;
        winner = null;
        status = Status.WAIT_INIT_BALANCE;
    }

    public static GameControl createGameWithPlayers(Map map, Dice dice, Player...players){
        GameControl gameControl = new GameControl(map, dice);
        gameControl.players.addAll(asList(players));
        return gameControl;
    }

    private Map initMap(){
        List<Place> places = new ArrayList<>();

        places.add(new StartPoint());

        for(int i = 0 ; i < 13; i++ ){
            places.add(new Estate(200));
        }

        places.add(new Hospital());

        for(int i = 0 ; i < 13; i++ ){
            places.add(new Estate(200));
        }

        places.add(new ToolHouse());

        for(int i = 0 ; i < 6; i++ ){
            places.add(new Estate(500));
        }

        places.add(new GiftHouse());

        for(int i = 0 ; i < 13; i++ ){
            places.add(new Estate(300));
        }

        places.add(new Prison());

        for(int i = 0 ; i < 13; i++ ){
            places.add(new Estate(300));
        }

        places.add(new MagicHouse());

        places.add(new Mine(20));
        places.add(new Mine(80));
        places.add(new Mine(100));
        places.add(new Mine(40));
        places.add(new Mine(80));
        places.add(new Mine(60));

        Map map = new GameMap(places);
        map.putInGame(this);
        return map;
    }

    @Override
    public void execute(Command command) {
        status = command.execute(this);
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
        if(id < 1 || id > PLAYER_SUM_LIMIT) {
            players = new ArrayList<>();
            throw new Exception("玩家id超过限定范围! (1-" + PLAYER_SUM_LIMIT + ")");
        }
        Player player = players.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        if(player != null) {
            players = new ArrayList<>();
            throw new Exception("玩家" + id + "已经存在!");
        }
        players.add(new Player(id, initBalance, map.findStartPoint()));
    }

    public void startGame() {
        players.sort((p1, p2) -> p1.getId() > p2. getId() ? 1 : -1);
        status = Status.IN_PROGRESS;
    }

    public void startTurn() {
        if (currentPlayer == null)
            currentPlayer = players.get(0);
        else {
            int currentIndex = players.indexOf(currentPlayer);
            currentPlayer = players.get((currentIndex + 1) % players.size());
        }
        currentPlayer.startTurn();
    }

    public void endTurn() {
        List<Player> survivePlayers = players.stream().filter(player -> player.getStatus() != Status.LOSE_GAME).collect(Collectors.toList());
        if(survivePlayers.size() == 1) {
            status = Status.END_GAME;
            winner = survivePlayers.get(0);
        }
        return;
    }

    public Map getMap() {
        return map;
    }

    public Dice getDice() {
        return dice;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Status getStatus() {
        return status;
    }

    public Player getWinner() {
        return winner;
    }
}
