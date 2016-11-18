package rich.place;

import com.sun.media.jfxmedia.events.PlayerStateEvent;
import rich.Printer;
import rich.commander.Player;
import rich.Status;

public class Estate implements Place {
    private Player owner;
    private double price;
    private Level level;

    public Estate(double price) {
        this.owner = null;
        this.price = price;
        level = Level.ZERO;
    }

    public static Estate createEstateWithOwner(double price, Player owner) {
        Estate estate = new Estate(price);
        estate.owner = owner;
        return estate;
    }

    public boolean buy(Player player) {
        if (owner == null) {
            owner = player;
            return true;
        }
        return false;
    }

    public double sell(){
        owner = null;
        level = Level.ZERO;
        return  price * (level.ordinal() + 1) * 2;
    }

    public void revert(){
        level = Level.ZERO;
        owner = null;
    }

    public Player getOwner() {
        return owner;
    }

    public double getPrice() {
        return price;
    }

    public Level getLevel() {
        return level;
    }

    public boolean promote() {
        if (level.ordinal() < Level.TOP.ordinal()) {
            level = level.next();
            return true;
        }
        return false;
    }

    @Override
    public Status arrive(Player player) {
        if (owner == null) {
            return Status.WAIT_BUY_RESPONSE;
        }else
        if (owner == player) {
            return Status.WAIT_BUILD_RESPONSE;
        }
        else {
            if (player.payFee()) {
                return Status.END_TURN;
            }
            else {
                return Status.LOSE_GAME;
            }
        }
    }

    @Override
    public Status getStatus(Player player) {
        if(player.getStatus() == Status.WAIT_RESPONSE){
            if (owner == null) {
                return Status.WAIT_BUY_RESPONSE;
            }
            if (owner == player) {
                return Status.WAIT_BUILD_RESPONSE;
            }
        }
        return player.getStatus();
    }

    public enum Level {
        ZERO {
            @Override
            public Level next() {
                return ONE;
            }

            @Override
            public double getFeeTimes() {
                return 0.5;
            }
        },
        ONE {
            @Override
            public Level next() {
                return TOP;
            }

            @Override
            public double getFeeTimes() {
                return 1;
            }
        },
        TWO {
            @Override
            public Level next() {
                return TOP;
            }

            @Override
            public double getFeeTimes() {
                return 2;
            }
        },
        TOP {
            @Override
            public Level next() {
                return TOP;
            }

            @Override
            public double getFeeTimes() {
                return 4;
            }
        };

        public abstract Level next();

        public abstract double getFeeTimes();
    }
}
