package fr.pantheonsorbonne.miage.game.monopoly.elements;


public class SpacePublicService extends SpaceToBuy {
    
    private Dice d = new Dice();
    
    public SpacePublicService(String name, int index, int price) {
        super(name, index, price);
    }

    @Override
    public void setCurrentRentPrice() {
        if(this.owner.getNbServicePublic() ==2){
            this.currentRentPrice =  d.rollDices() * 10;
        }  
    }


    @Override
    public int getCurrentRentPrice(){
        return d.rollDices() * 4;
    }

    
}