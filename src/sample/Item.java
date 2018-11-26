package sample;

public class Item {

    private String name;
    private double cost;
    private int amount;

    private boolean modX;
    private boolean modY;

    private double totalCost = 0.0;

    Item(String name, double cost, boolean modX, boolean modY, int amount) {
        this.name = name;
        this.cost = cost;
        this.modX = modX;
        this.modY = modY;
        this.amount = amount;

        if(this.isModX()) this.cost += 2.0;
        if(this.isModY()) this.cost += 3.0;

        this.totalCost = this.cost*(double)this.amount;
    }

    public String getName() {
        return name;
    }

    public boolean isModX() {
        return modX;
    }

    public boolean isModY() {
        return modY;
    }

    public double getCost() {
        return cost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModX(boolean modX) {
        this.modX = modX;
    }

    public void setModY(boolean modY) {
        this.modY = modY;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
