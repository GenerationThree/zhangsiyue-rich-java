package rich.place;

import rich.Player;

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
    public Player.Status arrive(Player player) {
        if (owner == null || owner == player)
            return Player.Status.WAIT_RESPONSE;
        else {
            if (player.payFee())
                return Player.Status.END_TURN;
            else
                return Player.Status.END_GAME;
        }
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
