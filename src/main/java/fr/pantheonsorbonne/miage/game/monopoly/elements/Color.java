package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;
import java.util.List;

public class Color {

    
    private Player colorMonopolist;
    private final String name;
    private final int housePrice;
    private List<SpaceCity> spaces = new ArrayList<>();

    public Color(String name, int housePrice){
        this.name = name;
        this.housePrice = housePrice;
    }

    
    public void setColorMonopolist(Player colorMonopolist){
        if(isColorMonopolist(colorMonopolist)){
            this.colorMonopolist=colorMonopolist;
        }else{
            this.colorMonopolist = null;
        }   
    }

    public boolean isColorMonopolist(Player colorMonopolist){
        for (SpaceCity s : spaces){
            if(s.getOwner() != colorMonopolist){
                return false;
            }
        }
        return true;
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

    public Player getColorMonopolist(){
        return this.colorMonopolist;
    }

}
