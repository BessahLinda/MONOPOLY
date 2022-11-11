package fr.pantheonsorbonne.miage.game.monopoly.elements;

public abstract class  SpaceSpecial extends Space {
    protected static Player owner; 
    protected int price ;
    private int rentPrice;

    public SpaceSpecial(String name, int index) {
        super(name, index);
    }
    
    public abstract int calculRentPrice();
}
