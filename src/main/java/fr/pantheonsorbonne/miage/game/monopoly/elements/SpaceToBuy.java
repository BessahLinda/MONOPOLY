package fr.pantheonsorbonne.miage.game.monopoly.elements;

public abstract class  SpaceToBuy extends Space {

    protected Player owner; 
    protected int price ;
    protected int currentRentPrice;

    public SpaceToBuy(String name, int index,int price) {
        super(name, index);
        this.price = price;
    }
    
    public int getPrice(){
        return this.price;
    }

    public Player getOwner(){
        return this.owner;
    }
    public void setOwner(Player player) {
        this.owner =player;
    }

    public int getCurrentRentPrice(){
        return this.currentRentPrice;
    }
    
    public boolean isSpaceOwned() {
        return this.owner != null;
    }

    public abstract int getCurrentResellPrice();

    public abstract void setCurrentRentPrice();

}
