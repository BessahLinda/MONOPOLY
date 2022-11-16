package fr.pantheonsorbonne.miage.game.monopoly.elements;

public class SpacePublicService extends SpaceToBuy {
    
    public SpacePublicService(String name, int index, int price) {
        super(name, index, price);
    }

    @Override
    public void setCurrentRentPrice() {
        if(this.owner.getNbServicePublic() ==2){
            this.currentRentPrice =  (int) ((Math.random() * 6) + 1) * 10;
        }  
    }

    @Override
    public int getCurrentResellPrice() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getCurrentRentPrice(){
        return (int) ((Math.random() * 6) + 1) * 4;

    }



    
    
}