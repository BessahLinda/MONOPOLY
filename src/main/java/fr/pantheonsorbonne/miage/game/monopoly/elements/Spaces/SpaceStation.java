package fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces;

public class SpaceStation extends SpaceToBuy {

    
    public SpaceStation(String name, int index,int price) {
        super(name, index,price);
        this.currentRentPrice =25;
    }

    @Override
    public void setCurrentRentPrice() {
        this.currentRentPrice = this.getOwner().getNbStation() * 25;        
    }
 
}