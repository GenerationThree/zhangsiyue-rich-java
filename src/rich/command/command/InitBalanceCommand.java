package rich.command.command;

import rich.commander.Commander;
import rich.Status;
import rich.command.Command;
import rich.commander.GameControl;

public class InitBalanceCommand implements Command{
    private double balance;

    public InitBalanceCommand(double balance) {
        this.balance = balance;
    }

    @Override
    public Status execute(Commander gameControl) {
        try {
            ((GameControl) gameControl).setInitBalance(balance);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Status.WAIT_INIT_BALANCE;
        }
        return Status.WAIT_INIT_PLAYER;
    }
}
