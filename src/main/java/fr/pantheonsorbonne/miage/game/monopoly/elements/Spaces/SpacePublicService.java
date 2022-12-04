package fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Dice;

public class SpacePublicService extends SpaceToBuy {
    
    private Dice d = new Dice();
    private int a = 4;

    public SpacePublicService(String name, int index, int price) {
        super(name, index, price);
    }

    @Override
    public void setCurrentRentPrice() {
        if(this.owner.getNbServicePublic() ==2){
            a= 10;
        }  
    }


    @Override
    public int getCurrentRentPrice(){
        return d.rollDices() * a;
    }

    
}