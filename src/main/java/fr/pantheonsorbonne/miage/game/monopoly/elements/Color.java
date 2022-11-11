package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;
import java.util.List;

public class Color {

    
    private Player colorMonopolist;
    private String name;
    private int housePrice;
    private List<SpaceCity> spaces = new ArrayList<>();

    public Color(String name, int housePrice){
        this.name = name;
        this.housePrice = housePrice;
    }

    
    public void setPlayer(Player colorMonopolist){
        if(isOwner(colorMonopolist)){
            this.colorMonopolist=colorMonopolist;
        }else{
            this.colorMonopolist = null;
        }   
    }

    public boolean isOwner(Player colorMonopolist){
        for (SpaceCity s : spaces){
            if(s.getOwner()== colorMonopolist){
                return true;
            }
        }
        return false;
    }

    public int getHousePrice(){
        return housePrice;
    }

    public boolean isColorOwned() {
        return this.colorMonopolist != null;
    }

    public List getSpaces(){
        return this.spaces;
    }

}
