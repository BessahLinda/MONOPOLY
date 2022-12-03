package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;
import java.util.List;

public class Color {

    
    private Player colorMonopolist; //guess we can remove this and replace by spaces 
    private final ColorEnum color;
    private final int housePrice;
    private ArrayList<SpaceCity> spaces = new ArrayList<>();

    public Color(String name, int housePrice){
        this.color = ColorEnum.valueOf(name);
        this.housePrice = housePrice;
    }

    
    public void setColorMonopolist(Player colorMonopolist){ 
       
            this.colorMonopolist=colorMonopolist; //set colorist dans color // on peut enlever cette partie 

            ArrayList<SpaceCity> priorityList = new ArrayList<>(); // + la fonctionne qui ajout une liste de colorSet dans player
            int maxind = 0;
            priorityList.add(spaces.get(maxind));
            for(int i = 1; i<spaces.size() ; i++){
                priorityList.add(spaces.get(i));
                if(spaces.get(i).getPrice() >spaces.get(maxind).getPrice()){
                    maxind = i;
                }
            }
            SpaceCity tmp = priorityList.get(0); //sorted descending order
            priorityList.set(0,priorityList.get(maxind));
            priorityList.set(maxind,tmp);
            colorMonopolist.sortColorsetProperty(priorityList);
    }

    public boolean isColorMonopolist(Player colorMonopolist){ //on peut merger dans setColor 
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

    public ArrayList getSpaces(){
        return this.spaces;
    }

    public Player getColorMonopolist(){
        return this.colorMonopolist;
    }

    public String getColorName(){
        return this.color.name();
    }
    
    public void removeColorMonopolist(){
        this.colorMonopolist = null;
    }

    public int getValue(){
        return this.color.value;
    }


   

}
