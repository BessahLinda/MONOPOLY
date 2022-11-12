package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;

public class Player {

    private String name;
    private int money = 1500;
    private int position = 0;   
    private int prisonDuration; 
    private boolean isInJail = false;

    private ArrayList<SpaceCity> property = new ArrayList<>();

    public Player(String name){
        this.name = name;
        
    }

    public void setPosition(int diceResult){
        if ((this.position + diceResult) > 40){
            this.position = (this.position +diceResult) % 40;
            money += 200;
        }else
            this.position += diceResult;      
    }

    public int getAsset(){ //game over condtion : asset < 0
        int asset = 0;
        
        for(SpaceCity s : property ){
            asset += s.getColor().getHousePrice()*s.getNbHouse() + s.getPrice() + money;
        }
        return asset;
    }

    public boolean checkBalance(int price){
        if (this.money - price < 0){
            return false;
        }else {
            return true;
        }
        
    }

    public void buyHouse(){
        
    }

    public void buyLand(SpaceCity s){
        if (checkBalance(s.getPrice())){
            withdrawMoney(s.getPrice());
            s.setOwner(this);
            property.add(s);
            s.getColor().setPlayer(this);
        } 
    }

    public boolean bankrupt(){
        if (this.money < 0){
            return true;
        }else
            return false;
    
    }

    public void goToJail(){
        this.setInJail(true);
        this.setPosition(10);
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
        if(checkBalance(s.getCurrentRentPrice())){
            withdrawMoney(s.getCurrentRentPrice());
            s.getOwner().addMoney(s.getCurrentRentPrice());
        }else{
            sale();
        }
    }

    public void payTax(SpaceTax s){
        if(checkBalance(s.getTax())){
            withdrawMoney(s.getTax());
        }else{
            sale();
        }
    }

    public void payChance(int rndPrice) {
        if(checkBalance(rndPrice)){
            withdrawMoney(rndPrice);
        }else{
            sale();
        }
	}

    private void sale() {
    }

    public void addMoney(int m) {
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

    public int getMoney() {
        return this.money;
    }

    public int getPrisonDuration(){
        return this.prisonDuration;
    }

    public boolean isInJail(){
        return this.isInJail;
    }
	

}
