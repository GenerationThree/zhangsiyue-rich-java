package rich.Place;

import rich.Player;

public class Estate implements Place {
    private Player owner;
    private double price;

    public Estate(double price) {
        this.owner = null;
        this.price = price;
    }

    public static Estate createEstateWithOwner(double price, Player owner){
        Estate estate = new Estate(price);
        estate.owner = owner;
        return estate;
    }

    public boolean buy(Player player){
        if(owner == null) {
            owner = player;
            return true;
        }
        return false;
    }

    public Player getOwner() {
        return owner;
    }

    public double getPrice() {
        return price;
    }
}
