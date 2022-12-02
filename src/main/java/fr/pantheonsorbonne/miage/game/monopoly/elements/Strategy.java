package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;

public class  Strategy implements AIStrategy{

   

    @Override
    public void buyHouse(Player p) {
        //buy less then 3 houses in every city
        
        while(p.checkBalance()>400 && !allCitiesHave4Houses(p)){
            for(int i =0; i<p.getColorsetProperty().size();++i){
                if(haveJusteBrownOrBlue(p) && p.getColorsetProperty().get(i).getNbHouse() < 5){
                    buyTwoHouses(p, i);

                }else{
                    if(p.getColorsetProperty().get(i).getColor().getValue()>6 && allCitiesHave4HousesExceptBrownAndBlueClair(p)){
                        buyTwoHouses(p,i);
                    }else if(p.getColorsetProperty().get(i).getNbHouse() < 5 && p.getColorsetProperty().get(i).getColor().getValue()<6){
                        buyTwoHouses(p,i);
                        continue;
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

    public boolean allCitiesHave4HousesExceptBrownAndBlueClair(Player p){
        for(SpaceCity city: p.getColorsetProperty()){
            if(city.getColor().getValue() == 8 ){
                continue;
            }else if(city.getNbHouse() != 4 ){
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


    public boolean haveJusteBrownOrBlue(Player p){

        for(SpaceCity city: p.getColorsetProperty()){
            if(city.getColor().getValue() < 7 ){
                return false;
            } 
        }
        return true;
    }


    public static ArrayList<SpaceToBuy> arrangePriority(Player p){
        ArrayList<SpaceToBuy> priority = new ArrayList<>();
        ArrayList<SpaceToBuy> priorityColor = new ArrayList<>();
        ArrayList<SpaceToBuy> prioritySpecial = new ArrayList<>();

        for(SpaceToBuy s : p.getProperty()){

            if(s instanceof SpaceCity){
                if(((SpaceCity) s).getColor().isColorOwned()){
                    priorityColor.add(s);
                }
                else{
                    priority.add(s);
                }
            }
            else{
                prioritySpecial.add(s);
            }
            
        }

        //sorting Color + optimser plus
        int indexMin = 0;
        for(int i =0; i < priorityColor.size(); i++){ 
            for(int j=i+1; j< priorityColor.size(); j++){
                if(priorityColor.get(indexMin).getPrice()>priorityColor.get(j).getPrice()){
                    indexMin = j;
                }
            }   
            SpaceToBuy tmp = priorityColor.get(i);
            priorityColor.set(i,priorityColor.get(indexMin));
            priorityColor.set(indexMin,tmp); 
        }

        //sorting public sation and public service (Space Station has higher value than Public service)
        if(prioritySpecial.size()>=2){
            for(int k=1; k< prioritySpecial.size(); k++){
                if(prioritySpecial.get(k) instanceof SpacePublicService){
                    prioritySpecial.remove(prioritySpecial.get(k));
                    prioritySpecial.add(0,prioritySpecial.get(k));
                }
            }
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
            p.withdrawMoney(10000000) ;
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