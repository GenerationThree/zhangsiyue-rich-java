package rich;

import rich.Command.Command;
import rich.Place.Estate;
import rich.Place.Hospital;
import rich.Place.Place;
import rich.Command.Response;
import rich.Place.Prison;
import rich.Tool.Tool;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Player {
    private Status status;
    private Place currentPlace;
    private double balance;
    private List<Place> estates;
    Command lastExecuted;
    private int freeTurns;
    private int waitTurn;
    private int points;
    private List<Tool> tools;

    public Player() {
        status = Status.END_TURN;
        balance = 0;
        estates = new ArrayList<>();
        freeTurns = 0;
        waitTurn = 0;
        points = 0;
        tools = new ArrayList<>();
    }

    public static Player createPlayerWithStarting(Place starting) {
        Player player = new Player();
        player.currentPlace = starting;
        return player;
    }

    public static Player createPlayerWithBalanceAndEstate(Place starting, double balance, Place... estates) {
        Player player = createPlayerWithStarting(starting);
        player.balance = balance;
        player.estates.addAll(asList(estates));
        return player;
    }

    public static Player createPlayerWithFreeTimes(Place starting, double balance, int freeTurns, Place... estates) {
        Player player = createPlayerWithBalanceAndEstate(starting, balance, estates);
        player.freeTurns = freeTurns;
        return player;
    }

    public static Player createPlayerWithPoints(Place starting, int points) {
        Player player = new Player();
        player.currentPlace = starting;
        player.points = points;
        return player;
    }

    public void executeCommand(Command command) {
        status = command.execute(this);
        lastExecuted = command;
    }

    public void respond(Response response, String parameter) {
        status = lastExecuted.respond(this, response, parameter);
    }

    public void moveTo(Place place) {
        currentPlace = place;
    }

    public void buy() {
        Estate estate = (Estate) currentPlace;
        if (balance >= estate.getPrice() && estate.buy(this)) {
            balance -= ((Estate) currentPlace).getPrice();
            estates.add(currentPlace);
        }
    }

    public void promoteEstate() {
        Estate estate = ((Estate) currentPlace);
        if (balance >= estate.getPrice() && estate.promote())
            balance -= estate.getPrice();
    }

    public boolean payFee() {
        Estate estate = (Estate) currentPlace;
        if (freeTurns > 0 || estate.getOwner().getCurrentPlace() instanceof Hospital || estate.getOwner().getCurrentPlace() instanceof Prison) {
            return true;
        }
        double fee = estate.getPrice() * estate.getLevel().getFeeTimes();
        if (balance >= fee) {
            balance -= fee;
            ((Estate) currentPlace).getOwner().gainFee(fee);
            return true;
        }
        return false;
    }

    public void gainFee(Double fee) {
        balance += fee;
    }

    public void inPrison(int waitTurn) {
        this.waitTurn = waitTurn;
    }

    public void buyTool(int choice) {
        if (points >= Tool.Type.values()[choice - 1].getPointPrice()) {
            tools.add(new Tool(Tool.Type.values()[choice - 1]));
            points -= Tool.Type.values()[choice - 1].getPointPrice();
        }
    }

    public Status getStatus() {
        return status;
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    public double getBalance() {
        return balance;
    }

    public List<Place> getEstates() {
        return estates;
    }

    public int getWaitTurn() {
        return waitTurn;
    }

    public int getPoints() {
        return points;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public enum Status {
        WAIT_RESPONSE, END_TURN, WAIT_COMMAND, END_GAME
    }
}
