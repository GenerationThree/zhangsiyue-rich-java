package rich.command.command;

import rich.commander.Commander;
import rich.Status;
import rich.command.Command;
import rich.commander.GameControl;

public class InitPlayerCommand implements Command {
    private String ids;

    public InitPlayerCommand(String ids) {
        this.ids = ids;
    }

    @Override
    public Status execute(Commander gameControl) {
        int id[] = new int[ids.length()];
        for (int i = 0;i < ids.length(); i ++)
        {
            id[i] = Integer.parseInt(ids.substring(i,i+1));
            try {
                ((GameControl) gameControl).addPlayer(id[i]);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return Status.WAIT_INIT_PLAYER;
            }
        }
        return Status.WAIT_START;
    }
}
