package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceCity;

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

    public List<SpaceCity> getSpaces(){
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

    public Player getPlayerhasTwoSpaces(){
        boolean a= false;
        for(int i= 0; i<spaces.size()-1;++i){
            for(int j=i+1;j<spaces.size();++j){
                if (spaces.get(i).getOwner()==spaces.get(j).getOwner()){
                    a= true;
                    break;
                }         
            }
            if(a) return spaces.get(i).getOwner();
        }
        return null;
    }

    public boolean hasPlayerTwoSpaces(Player p){
        int cpt=0;
        for (SpaceCity s : spaces){
            if(s.getOwner() == p){
                ++cpt;
            }
        }
        return cpt==2;
    }
}
