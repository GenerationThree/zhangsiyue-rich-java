package rich;

public class Player {
    private Status status;

    public void executeCommand(Command command) {
        status = command.execute(this);
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        WAIT_RESPONSE, END_TURN, WAIT_COMMAND
    }
}
