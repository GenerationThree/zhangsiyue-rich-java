package rich;

public class Player {
    private Status status;
    private Place currentPlace;

    public void executeCommand(Command command) {
        status = command.execute(this);
    }

    public void respond(Response response) {
        status = response.execute(this);
    }

    public void moveTo(Place place){
        currentPlace = place;
    }

    public Status getStatus() {
        return status;
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    public enum Status {
        WAIT_RESPONSE, END_TURN, WAIT_COMMAND
    }
}
