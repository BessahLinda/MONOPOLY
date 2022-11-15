package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

    private final String name;
    private int money = 1500;
    private int position = 0;   
    private int prisonDuration; 
    private boolean isInJail = false;
    private ArrayList<SpaceCity> property = new ArrayList<>();
    // private ArrayList<SpaceToBuy> property = new ArrayList<>();
    //private ArrayList<SpaceToBuy> publicServiceAndStations = new ArrayList<>();

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

    public int getAsset(){ 
        int asset = 0;
        
        for(SpaceCity s : this.property ){
            asset += s.getColor().getHousePrice()*s.getNbHouse() + s.getPrice() + money;
        }
        return asset;
    }

    public boolean isAffordable(int price){
        if (this.money - price < 0){
            return false;
        }else {
            return true;
        }
        
    }

    public int checkBalance(){
        return money;
     }

    public void buyHouse(Space s){
        //s.setCurrentRentPrice();
        //buildHouse on space city


        
        
    }



    public void buyLand(SpaceCity s){
        if (isAffordable(s.getPrice())){
            withdrawMoney(s.getPrice());
            s.setOwner(this);
            property.add(s);
            if(s instanceof SpaceCity){
                SpaceCity spaceCity = (SpaceCity)s;
                spaceCity.getColor().setColorMonopolist(this);
            }  
        } 
    }

    /**public void buySpecialSpace(SpaceToBuy s){
        if (checkBalance(s.getPrice())){
            withdrawMoney(s.getPrice());
            s.setOwner(this);
            property.add(s);
            s.getColor().setColorMonopolist(this);
        } 
    }**/

    public boolean isBankrupt(){
        if (getAsset() < 0){
            return true;
        }else
            return false;
    
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

    public void payRent(SpaceCity s) {
        if(isAffordable(s.getCurrentRentPrice())){
            withdrawMoney(s.getCurrentRentPrice());
            s.getOwner().earnMoney(s.getCurrentRentPrice());
        }else{
            sellProperty(s.getCurrentRentPrice());
            withdrawMoney(s.getCurrentRentPrice());
            s.getOwner().earnMoney(s.getCurrentRentPrice());
        }
    }

    public void payTax(SpaceTax s){
        if(isAffordable(s.getTax())){
            withdrawMoney(s.getTax());
        }else{
            sellProperty(s.getTax());
            withdrawMoney(s.getTax());
        }
    }

    public void payChance(int rnPrice) {
        if(isAffordable(rnPrice)){
            withdrawMoney(rnPrice);
        }else{
            sellProperty(rnPrice);
            withdrawMoney(rnPrice);
        }
	}

    private void sellProperty(int price) {

        ArrayList<SpaceCity> priority = new ArrayList<>();
        ArrayList<SpaceCity> priorityColor = new ArrayList<>();


        for(SpaceCity s : property){
            if(s.getColor().isColorOwned()){
                priorityColor.add(s);
            }
            else{
                priority.add(s);
            }
        }

        int indexMin = 0;
        for(int i =0; i < priorityColor.size(); i++){
            for(int j=i+1; j< priorityColor.size(); j++){
                if(priorityColor.get(indexMin).getCurrentRentPrice()>priorityColor.get(j).getCurrentRentPrice()){
                    indexMin = j;
                }
            }   
            SpaceCity tmp = priorityColor.get(i);
            priorityColor.set(i,priorityColor.get(indexMin));
            priorityColor.set(indexMin,tmp);
        }
        // gotta test !

        indexMin = 0;
        for(int i =0; i < priorityColor.size(); i++){
            for(int j=i+1; j< priorityColor.size(); j++){
                if(priorityColor.get(indexMin).getCurrentRentPrice()>priorityColor.get(j).getCurrentRentPrice()){
                    indexMin = j;
                }
            }
            SpaceCity tmp = priority.get(i);
            priority.set(i,priority.get(indexMin));
            priority.set(indexMin,tmp);
        }

        // gotta test !


        priority.addAll(priorityColor);
        
        int index = 0;
        while(price < this.checkBalance()){
            //noooo sell maison one by one
            this.earnMoney(priority.get(index).getCurrentResellPrice());
            priority.get(index).setOwner(null);

            index++;
        }

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

    public int getPrisonDuration(){
        return this.prisonDuration;
    }

    public boolean isInJail(){
        return this.isInJail;
    }

    public void setPrisonDuration(){
        this.prisonDuration +=1;
    }

}
