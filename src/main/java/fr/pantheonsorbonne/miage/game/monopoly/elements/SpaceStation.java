package fr.pantheonsorbonne.miage.game.monopoly.elements;

public class SpaceStation extends SpaceSpecial {

    public SpaceStation(String name, int index) {
        super(name, index);
        this.price = 200;
    }

    @Override
    public int calculRentPrice() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    
}