package fr.pantheonsorbonne.miage.game.monopoly.elements;

public class  SpaceTax extends Space {

    private final int taxPrice;

    public SpaceTax(String name, int index, int taxPrice) {
        super(name, index);
        this.taxPrice=taxPrice;
    }

    public int getTax(){
        return this.taxPrice;
    }

}