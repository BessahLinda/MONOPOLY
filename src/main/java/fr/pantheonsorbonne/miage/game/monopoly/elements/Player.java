package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;

public class Player {

    private final String name;
    private int money = 1500;
    private int position = 0;   
    private int prisonDuration; 
    private boolean isInJail = false;
    private ArrayList<SpaceToBuy> property = new ArrayList<>();
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

    public int getAsset(){ //game over condtion : asset < 0
        int asset = 0;
        
        for(SpaceToBuy s : property ){
            SpaceCity spaceCity = (SpaceCity)s;
            asset += spaceCity.getColor().getHousePrice()*spaceCity.getNbHouse() + s.getPrice() + money;
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

    public void buyHouse(){
        
        //s.setCurrentRentPrice();
    }

    public void buyLand(SpaceToBuy s){
        if (isAffordable(s.getPrice())){
            withdrawMoney(s.getPrice());
            s.setOwner(this);
            property.add(s);
            if(s instanceof SpaceCity){
                SpaceCity spaceCity = (SpaceCity)s;
                spaceCity.getColor().setColorMonopolist(this);
                
            }  
            setRentOfProperties();
        } 
    }

    /**public void buySpecialSpace(SpaceToBuy s){
        if (isAffordable(s.getPrice())){
            withdrawMoney(s.getPrice());
            s.setOwner(this);
            property.add(s);
            s.getColor().setColorMonopolist(this);
        } 
    }**/

    public boolean isBankrupt(){
        if (this.money < 0){
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

    public void payRent(SpaceToBuy s) {
        if(isAffordable(s.getCurrentRentPrice())){
            withdrawMoney(s.getCurrentRentPrice());
            s.getOwner().earnMoney(s.getCurrentRentPrice());
        }else{
            //sellProperty();
        }
    }

    public void payTax(SpaceTax s){
        if(isAffordable(s.getTax())){
            withdrawMoney(s.getTax());
        }else{
            //sellProperty();
        }
    }

    public void payChance(int rndPrice) {
        if(isAffordable(rndPrice)){
            withdrawMoney(rndPrice);
        }else{
           // sellProperty();
        }
	}

    
    private void setRentOfProperties(){
        for(SpaceToBuy sp : property ){
            //if(((SpaceCity) sp).getColor()==spaceCity.getColor()){
                sp.setCurrentRentPrice();
            //}
            
        }
    }

    private void sellProperty() {  
        ArrayList<SpaceToBuy> priority = new ArrayList<>();
        ArrayList<SpaceCity> priorityColor = new ArrayList<>();


        for(SpaceToBuy s : property){

            if(((SpaceCity) s).getColor().isColorOwned()){
                priorityColor.add((SpaceCity) s);
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
            SpaceToBuy tmp = priority.get(i);
            priority.set(i,priority.get(indexMin));
            priority.set(indexMin,tmp);
        }

        // gotta test !


        priority.addAll(priorityColor);

        int index = 0;
        while(priority.get(index).getPrice() < this.checkBalance()){
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

}
