package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;
import java.util.Collections;


public class Player {

    protected Strategy strategy;
    private final String name;
    private int money = 1500; //faut supprimer dans partie reseaux 
    private int position = 0;   
    private int prisonDuration; 
    private boolean isInJail = false;
    private ArrayList<SpaceToBuy> property = new ArrayList<>();
    private ArrayList<SpaceCity> colorsetProperty = new ArrayList<>();

    public Player(String name, Strategy s){
        this.name = name;
        this.strategy = s;
        
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
        return !colorsetProperty.isEmpty();

    }

    public void buyHouse(){
        if(canBuyHouse()){
            strategy.buyHouse(this);
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

    public void sellProperty(int payment) {  
        this.strategy.sellProperty(this, payment);
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

    public void sortColorsetProperty(ArrayList<SpaceCity> colorset){
        colorsetProperty.addAll(colorset);
        Collections.sort(colorsetProperty);
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
