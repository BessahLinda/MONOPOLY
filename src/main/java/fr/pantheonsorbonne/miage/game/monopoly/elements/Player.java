package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;

public class Player {

    private String name;
    private int money = 1500;
    private int position = 0;   
    private int prisonDuration; 

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

    

    public void setInJail(boolean b) {
    }

    public void payRent(SpaceCity s) {
        if(checkBalance(s.getCurrentRentPrice())){
            withdrawMoney(s.getCurrentRentPrice());
            s.getOwner().addMoney(s.getCurrentRentPrice());
        }else{
            sale();
        }
    }

    private void sale() {
    }

    private void addMoney(int m) {
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

}
