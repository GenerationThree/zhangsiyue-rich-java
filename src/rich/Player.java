package rich;

import rich.Command.Command;
import rich.Place.Estate;
import rich.Place.Place;
import rich.Command.Response;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Player {
    private Status status;
    private Place currentPlace;
    private double balance;
    private List<Place> estates;

    public Player() {
        status = Status.END_TURN;
        balance = 0;
        estates = new ArrayList<>();
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

    public void executeCommand(Command command) {
        status = command.execute(this);
    }

    public void respond(Response response) {
        status = response.execute(this);
    }

    public void moveTo(Place place) {
        currentPlace = place;
    }

    public void buy() {
        Estate estate = (Estate) currentPlace;
        if(balance >= estate.getPrice() && estate.buy(this)) {
            balance -= ((Estate) currentPlace).getPrice();
            estates.add(currentPlace);
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

    public enum Status {
        WAIT_RESPONSE, END_TURN, WAIT_COMMAND
    }
}
