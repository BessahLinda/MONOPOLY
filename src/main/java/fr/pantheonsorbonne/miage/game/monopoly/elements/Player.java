package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;

public class Player {

    private Strategy strategy;
    private final String name;
    private int money = 1500; //faut supprimer dans partie reseaux 
    private int position = 0;   
    private int prisonDuration; 
    private boolean isInJail = false;
    private ArrayList<SpaceToBuy> property = new ArrayList<>();
    private ArrayList<SpaceCity> colorsetProperty = new ArrayList<>();

    public Player(String name){
        this.name = name;
        
    }

    public void advance(int diceResult){
        if ((this.position + diceResult) >= 40){
            this.position = (this.position +diceResult) % 40;
            money += 200;
        }else
            this.position += diceResult;      
    }

    public int getAsset(){ //game over condtion : asset < 0
        int asset = money;
        for(SpaceToBuy s : property ){
            if(s instanceof SpaceCity){
                SpaceCity spaceCity = (SpaceCity)s;
                asset += spaceCity.getColor().getHousePrice()*spaceCity.getNbHouse()*0.75 + spaceCity.getPrice()*0.75;
            }
            else{
                asset += s.getPrice()*0.75;
            }
        }
        return asset;
    }

    // check if I can pay the toll fee
    public boolean hasEnoughAsset(int payment){
        return getAsset()<payment;
    }

    // check if I can buy 
    public boolean isAffordable(int price){
        return this.money > price;
    }

    public boolean canBuyHouse(){
        if(colorsetProperty.isEmpty()){
            return false;
        }
        return true;
    }

    public void buyHouse(){
        if(canBuyHouse()){
            //buy less then 3 houses in every city
            if(!allCitiesOwnMaison()){  //marron , bleu , orange 
                strategy.buyHouseOnEmptyLand(this);
            }
            }
            else{
                strategy.buyHouseIfallCitiesOwnMaison(this);
            }
        }
    
     
    


    public boolean allCitiesOwnMaison(){
        for (SpaceCity s: colorsetProperty){
            if(s.getNbHouse()<1){
                return false;
            }
        }
        return true;
    }

    public void buyLand(SpaceToBuy s){
        //I'd like to put condition if a player already has one of  blueclaire or marron, I dont want to buy the others
        if (money>200){
            withdrawMoney(s.getPrice());
            s.setOwner(this);
            property.add(s);
            System.out.println(this.getName()+" bought land at "+ s.getName());
            if(s instanceof SpaceCity){
               SpaceCity sc = (SpaceCity)s;
                if(sc.getColor().isColorMonopolist(this)){
                    sc.getColor().setColorMonopolist(this);
                }
            }
            setRentOfProperties();
        } 
    }

    public boolean isBankrupt(){
        return getAsset()< 0;
    }

    public void goToJail(){
        this.setInJail(true);
        this.position = 10;
    }

    public void goOutJail(int price) {
        this.isInJail = false;
        this.prisonDuration = 0;
        withdrawMoney(price);
	}
    
    public void goOutJail() {
        this.isInJail = false;
        this.prisonDuration = 0;
	}

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public void payRent(SpaceToBuy s) {
        int rent = s.getCurrentRentPrice();
        if(isAffordable(rent)){
            withdrawMoney(rent);
            s.getOwner().earnMoney(rent);
        }else{
            sellProperty(rent);
        }
    }

    public void payTax(SpaceTax s){
        int tax = s.getTax();
        if(isAffordable(tax)){
            withdrawMoney(tax);
        }else{
            sellProperty(tax);
        }
    }

    public void payChance(int rndPrice) {
        if(isAffordable(rndPrice)){
            withdrawMoney(rndPrice);
        }else{
            sellProperty(rndPrice);
        }
	}

    
    public void setRentOfProperties(){
        for(SpaceToBuy sp : property ){
            sp.setCurrentRentPrice();
        }
    }

    public ArrayList<SpaceToBuy> arrangePriority(){
        ArrayList<SpaceToBuy> priority = new ArrayList<>();
        ArrayList<SpaceToBuy> priorityColor = new ArrayList<>();
        ArrayList<SpaceToBuy> prioritySpecial = new ArrayList<>();

        for(SpaceToBuy s : property){

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

    public void sellProperty(int payment) {  

        if(getAsset() < payment){
            property.clear();
            colorsetProperty.clear();
            this.money = -10000000;
            return;
        }    

        ArrayList<SpaceToBuy> priority = this.arrangePriority();

        int index = 0;
        //if priority 0
        while( payment > this.checkBalance()){
            if(priority.get(index) instanceof SpaceCity){
                SpaceCity currentCity = (SpaceCity) priority.get(index);
                if(currentCity.getNbHouse()!=0){
                    this.earnMoney((int)(currentCity.getColor().getHousePrice()*0.75));
                    currentCity.deconstructHouse();
                }
                else{
                    if(currentCity.getColor().getColorMonopolist()==null){
                        this.earnMoney(priority.get(index).getCurrentResellPrice());
                        priority.get(index).setOwner(null);
                        this.property.remove(currentCity);
                        System.out.println(this.getName()+" sold " + priority.get(index).getName());
                        index++;
                    }
                    else{
                        index++;
                    }
                }
            }
            else{
                this.earnMoney(priority.get(index).getCurrentResellPrice());
                priority.get(index).setOwner(null);
                this.property.remove(priority.get(index));
                System.out.println(this.getName()+" sold " + priority.get(index).getName());
                index++; //color monopolist change status
            }

            
            if(index>=priority.size()){ 
                while(payment > this.checkBalance()){
                    for(SpaceToBuy s : priority){
                        if(s instanceof SpaceCity){
                            SpaceCity currentCity = (SpaceCity) s;
                            if(currentCity.owner == this){
                                System.out.println(this.getName()+" sold " + currentCity.getName());
                                this.earnMoney((int)(currentCity.getPrice()*0.75));
                                s.setOwner(null);
                                this.property.remove(currentCity);
                                this.colorsetProperty.remove(currentCity);
                                currentCity.getColor().removeColorMonopolist();
                                break;
                            }
                        }    
                    }
                }
            }
        }
        setRentOfProperties();
        this.withdrawMoney(payment);
    }

    public void earnMoney(int m) {
        this.money += m;
    }

    public void withdrawMoney(int amount){
        money = money - amount;
    }

    public String getName() {
        return this.name;
    }

    public int getPosition(){
        return this.position;
    }

    public int checkBalance() {
        return this.money;
    }

    public int getPrisonDuration(){
        return this.prisonDuration;
    }

    public boolean isInJail(){
        return this.isInJail;
    }

    public void setPrisonDuration(){
        this.prisonDuration +=1;
    }

    public int getNbStation() {
        int nb = 0;
        for(SpaceToBuy sp : property ){
            if (sp instanceof SpaceStation){
                nb +=1;
            }    
        }
        return nb;
    }

    public int getNbServicePublic(){
        int nb = 0;
        for(SpaceToBuy sp : property ){
            if (sp instanceof SpacePublicService){
                nb +=1;
            }    
        }
        return nb;
    }

    public void setColorsetProperty(ArrayList<SpaceCity> colorset){
        this.colorsetProperty.addAll(colorset);
        //orange-> rouge->jeune->rose->blue->vert
    }

    public ArrayList<SpaceCity> getColorsetProperty(){
        return this.colorsetProperty;
    }

    public ArrayList<SpaceToBuy> getProperty(){
        return this.property;
    }

    public void toStringP(){
        String string = this.name +" have : ";
        for(SpaceToBuy sp : property ){
            if (sp instanceof SpaceCity){
                SpaceCity sc = (SpaceCity)sp;
                string += " [" + sc.getName()  + " , color:" + sc.getColor().getColorName() +", nbH :" + sc.getNbHouse() + " , rent:" + sc.getCurrentRentPrice()+ "]; ";
            }else{
                string += " [" + sp.getName() +" , rent:" + sp.getCurrentRentPrice() +"]; ";
            }    
        }
        System.out.println(string);
    }

}
