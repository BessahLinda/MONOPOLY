package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;
import java.util.Collections;

public class Strategy implements AIStrategy{

    @Override
    public void buyHouse(Player p) {
        if(!everyCityHas2House(p)){
            for(int i =0; i<p.getColorsetProperty().size();++i){
                while(p.checkBalance()>400 &&  p.getColorsetProperty().get(i).getNbHouse() < 2 && p.getColorsetProperty().get(i).getColor().getValue()<6){
                    p.getColorsetProperty().get(i).buildHouse(1);
                    p.withdrawMoney(p.getColorsetProperty().get(i).getColor().getHousePrice());  
                }
            }
        }
        while(p.checkBalance()>400 && !allCitiesHave4Houses(p)){
            for(int i =0; i<p.getColorsetProperty().size();++i){
                if(ownOnlyBadColors(p) && p.getColorsetProperty().get(i).getNbHouse() < 5){
                    buyTwoHouses(p, i);

                }else{
                    if(p.getColorsetProperty().get(i).getColor().getValue()>6 && everyCityHas4House(p)){
                        buyTwoHouses(p,i);
                    }else if(p.getColorsetProperty().get(i).getNbHouse() < 2 && p.getColorsetProperty().get(i).getColor().getValue()<6){
                        while(p.checkBalance()>400 && p.getColorsetProperty().get(i).getNbHouse() < 2 ){
                            p.getColorsetProperty().get(i).buildHouse(1);
                            p.withdrawMoney(p.getColorsetProperty().get(i).getColor().getHousePrice());
                            
                        }
                    }
                    else if(p.getColorsetProperty().get(i).getNbHouse() < 5 && p.getColorsetProperty().get(i).getColor().getValue()<6){
                        buyTwoHouses(p,i);
                    }                    
                }

            }
        }
    }

    public void buyTwoHouses(Player p,int i){
        int cpt=0;

        while(p.checkBalance()>400 && cpt < 2){
            if (p.getColorsetProperty().get(i).getNbHouse() == 4){
                break;
            }
            p.getColorsetProperty().get(i).buildHouse(1);
            p.withdrawMoney(p.getColorsetProperty().get(i).getColor().getHousePrice());
            ++cpt;
            
        }
    }

    //check if colorset property spaces have at least 4 houses except color blueclaire & marron 
    public boolean everyCityHas4House(Player p){
        for(SpaceCity city: p.getColorsetProperty()){
            if(city.getNbHouse() != 4 && city.getColor().getValue() < 7){
                return false;
            }
        }
        return true;
    }

    
    //check if colorset property spaces have at least 2 houses except color blueclaire & marron 
    public boolean everyCityHas2House(Player p){
        for(SpaceCity city: p.getColorsetProperty()){  
            if(city.getNbHouse() < 3 && city.getColor().getValue() < 7 ){
                return false;
            }
        }
        return true;
    }

    

    public boolean allCitiesHave4Houses(Player p){
        for(SpaceCity city: p.getColorsetProperty()){
            if(city.getNbHouse() != 4 ){
                return false;
            }
        }
        return true;
    }


    //player only has colorset of less valued color (blueclaire,marron)
    public boolean ownOnlyBadColors(Player p){

        for(SpaceCity city: p.getColorsetProperty()){
            if(city.getColor().getValue() < 7 ){
                return false;
            } 
        }
        return true;
    }


    //sorting player's properties by its value and color
    public static ArrayList<SpaceToBuy> arrangePriority(Player p){
        ArrayList<SpaceToBuy> priority = new ArrayList<>();
        ArrayList<SpaceCity> priorityColor = p.getColorsetProperty();
        Collections.sort(priorityColor,Collections.reverseOrder());
        ArrayList<SpaceToBuy> prioritySpecial = new ArrayList<>();

        for(SpaceToBuy s : p.getProperty()){
            if(s instanceof SpaceCity){
                if(!((SpaceCity) s).getColor().isColorOwned()){
                    priority.add(s);
                }
            }
            else{
                prioritySpecial.add(s);
            }    
        }

        
        int indexMin = 0;
        for(int i =0; i < prioritySpecial.size(); i++){
            for(int j=i+1; j< prioritySpecial.size(); j++){
                if(prioritySpecial.get(indexMin) instanceof SpacePublicService ){
                    indexMin = j;
                }
            }
            SpaceToBuy tmp = prioritySpecial.get(i);
            prioritySpecial.set(i,prioritySpecial.get(indexMin));
            prioritySpecial.set(indexMin,tmp);
        }

        //sorting space without owning color set
        indexMin = 0;
        for(int i =0; i < priority.size(); i++){
            for(int j=i+1; j< priority.size(); j++){
                if(priority.get(indexMin).getPrice()>priority.get(j).getPrice()){
                    indexMin = j;
                }
            }
            SpaceToBuy tmp = priority.get(i);
            priority.set(i,priority.get(indexMin));
            priority.set(indexMin,tmp);
        }

        priority.addAll(priorityColor);
        priority.addAll(prioritySpecial);
        return priority;
    }

    @Override
    public void sellProperty(Player p,int payment) {
        
        if(p.getAsset() < payment){
            p.getProperty().clear();
            p.getColorsetProperty().clear();
            p.withdrawMoney(100000) ;

            return;
        }    

        ArrayList<SpaceToBuy> priority = arrangePriority(p);

        int index = 0;
        //if priority 0
        while( payment > p.checkBalance()){
            if(priority.get(index) instanceof SpaceCity){
                SpaceCity currentCity = (SpaceCity) priority.get(index);
                if(currentCity.getNbHouse()!=0){
                    p.earnMoney((int)(currentCity.getColor().getHousePrice()*0.75));
                    currentCity.deconstructHouse();
                }
                else{
                    if(currentCity.getColor().getColorMonopolist()==null){
                        p.earnMoney(priority.get(index).getCurrentResellPrice());
                        priority.get(index).setOwner(null);
                        p.getProperty().remove(currentCity);
                        System.out.println(p.getName()+" sold " + priority.get(index).getName());
                        index++;
                    }
                    else{
                        index++;
                    }
                }
            }
            else{
                p.earnMoney(priority.get(index).getCurrentResellPrice());
                priority.get(index).setOwner(null);
                p.getProperty().remove(priority.get(index));
                System.out.println(p.getName()+" sold " + priority.get(index).getName());
                index++; //color monopolist change status
            }

            
            if(index>=priority.size()){ 
                while(payment > p.checkBalance()){
                    for(SpaceToBuy s : priority){
                        if(s instanceof SpaceCity){
                            SpaceCity currentCity = (SpaceCity) s;
                            if(currentCity.owner == p){
                                System.out.println(p.getName()+" sold " + currentCity.getName());
                                p.earnMoney((int)(currentCity.getPrice()*0.75));
                                s.setOwner(null);
                                p.getProperty().remove(currentCity);
                                p.getColorsetProperty().remove(currentCity);
                                currentCity.getColor().removeColorMonopolist();
                                break;
                            }
                        }    
                    }
                }
            }
        }
        p.setRentOfProperties();
        p.withdrawMoney(payment);
        
    }
}