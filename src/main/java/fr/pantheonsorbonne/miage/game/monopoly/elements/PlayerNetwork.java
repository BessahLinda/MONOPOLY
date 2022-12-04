package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;
import java.util.Collections;

import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;


public class PlayerNetwork extends Player {

    protected StrategyNetwork strategy;
    private String name;
    private int money = 0; 
    private int position = 0;   
    private int prisonDuration; 
    private boolean isInJail = false;
    private ArrayList<SpaceToBuy> property = new ArrayList<>();
    private ArrayList<SpaceCity> colorsetProperty = new ArrayList<>();
    private HostFacade hostFacade;
    private Game monopoly;

    public PlayerNetwork(String name, StrategyNetwork s,HostFacade hostFacade,fr.pantheonsorbonne.miage.model.Game monopoly){
   
        super(name, s);
        this.hostFacade = hostFacade;
        this.monopoly = monopoly;
    }

    public void advance(int diceResult){
        super.advance(diceResult);
    }


    public int getAsset(){ //game over condtion : asset < 0
        return super.getAsset();
     }

    // check if I can pay the toll fee
    public boolean hasEnoughAsset(int payment){
        return super.hasEnoughAsset(payment);
    }

    // check if I can buy 
    public boolean isAffordable(int price){
        return super.isAffordable(price);
    }

    public boolean canBuyHouse(){
        return super.canBuyHouse();
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
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("BUY_CELL", Integer.toString(this.getPosition())));
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
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("SEND_MONEY_TO", String.valueOf(rent)+String.valueOf(s.getOwner())));
            s.getOwner().earnMoney(rent);
        }else{
            sellProperty(rent);
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("SELL_PROPERTY", String.valueOf(rent)));
            s.getOwner().earnMoney(rent);
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("SEND_MONEY_TO", String.valueOf(rent)+String.valueOf(s.getOwner())));
        }
    }

    public void payTax(SpaceTax s){
        int tax = s.getTax();
        if(isAffordable(tax)){
            withdrawMoney(tax);
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("SEND_MONEY", String.valueOf(-tax)));
        }else{
            sellProperty(tax);
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("SELL_PROPERTY", String.valueOf(tax)));
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("SEND_MONEY", String.valueOf(-tax)));
        }
    }

    public void payChance(int rndPrice) {
        if(isAffordable(rndPrice)){
            withdrawMoney(rndPrice);
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("SEND_MONEY", String.valueOf(rndPrice)));
        }else{
            sellProperty(rndPrice);
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("SELL_PROPERTY", String.valueOf(rndPrice)));
            hostFacade.sendGameCommandToPlayer(monopoly, this.getName(),new GameCommand("SEND_MONEY", String.valueOf(rndPrice)));
        }
	}

    
    public void setRentOfProperties(){
        super.setRentOfProperties();
    }

    public void sellProperty(int payment) {  
        strategy.sellProperty(this, payment);
    }

    public void earnMoney(int m) {
        this.money += m;
    }

    public void withdrawMoney(int amount){
        money = money - amount;
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
        String string = this.name +" owns : ";
        for(SpaceToBuy sp : property ){
            if (sp instanceof SpaceCity){
                SpaceCity sc = (SpaceCity)sp;
                string += " [" + sc.getName()  + " , color:" + sc.getColor().getColorName() +", nbH :" + sc.getNbHouse() + " , rent:" + sc.getCurrentRentPrice()+ "] ";
            }else{
                string += " [" + sp.getName() +" , rent : " + sp.getCurrentRentPrice() +"] ";
            }    
        }
        System.out.println(string);
    }


}
