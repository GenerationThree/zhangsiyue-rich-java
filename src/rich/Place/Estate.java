package rich.Place;

import rich.Player;

public class Estate implements Place {
    private Player owner;

    public Estate() {
        this.owner = null;
    }

    public Player getOwner() {
        return owner;
    }
}
