package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;

public class Player {

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

    public void buildHouse(){
        if(canBuyHouse()){
            //buy less then 3 houses in every city
            if(!allCitiesOwnMaison()){  //marron , bleu , orange 
                for(SpaceCity city: colorsetProperty){
                    if(money<500){
                        break;
                    }
                    if(city.getColor().getColorName().equals("marron")||city.getColor().getColorName().equals("bleuClair")){ //priority x
                        while(money>2000&&city.getNbHouse()<2){  
                            city.buildHouse(1);
                            this.withdrawMoney(city.getColor().getHousePrice());
                        }
                    }
                    else if(city.getColor().getColorName().equals("orange")||city.getColor().getColorName().equals("rouge")||city.getColor().getColorName().equals("jeune")||city.getColor().getColorName().equals("rose")){  //priority 1
                        while(money>400&&city.getNbHouse()<2){ 
                            city.buildHouse(1);
                            this.withdrawMoney(city.getColor().getHousePrice());
                        }
                    }    
                    else if(city.getColor().getColorName().equals("bleu")){ //priority 2
                        while(money>400&&city.getName().equals("Rue de la Paix")&&city.getNbHouse()<2){
                            city.buildHouse(1);
                            this.withdrawMoney(city.getColor().getHousePrice());      
                        }
                    }
                    else if(city.getColor().getColorName().equals("vert")){ //priority 3
                        while(money>400&&city.getNbHouse()<2){
                            city.buildHouse(1);
                            this.withdrawMoney(city.getColor().getHousePrice());
                        }
                    }
                }
            }
            else{
                for(SpaceCity city: colorsetProperty){
                    if(money<2000){
                        break;
                    }
                    if(city.getColor().getColorName().equals("marron")||city.getColor().getColorName().equals("bleuClair")){ //priority x
                        while(money>800&&city.getNbHouse()<4){ 
                            city.buildHouse(1);
                            this.withdrawMoney(city.getColor().getHousePrice());
                        }
                    }
                    //since colorsetProperty is already sorted, the most important city comes first
                    else if(city.getColor().getColorName().equals("orange")||city.getColor().getColorName().equals("rouge")||city.getColor().getColorName().equals("jeune")||city.getColor().getColorName().equals("rose")){  //priority 1
                        while(money>3000&&city.getNbHouse()<4){ 
                            city.buildHouse(1);
                            this.withdrawMoney(city.getColor().getHousePrice()); 
                        }
                    }    
                    else if(city.getColor().getColorName().equals("bleu")){ //priority 2
                        while(money>3000&&city.getName().equals("Rue de la Paix")){
                            if(city.getNbHouse()<4){
                                city.buildHouse(1);
                                this.withdrawMoney(city.getColor().getHousePrice());
                            }
                        }
                    }
                    else if(city.getColor().getColorName().equals("vert")){ //priority 3
                        while(money>3000&&city.getNbHouse()<4){
                            city.buildHouse(1);
                            this.withdrawMoney(city.getColor().getHousePrice());
                        }
                    }
            }   }
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
        if (money>400){
            withdrawMoney(s.getPrice());
            s.setOwner(this);
            property.add(s);
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
        if(isAffordable(s.getCurrentRentPrice())){
            withdrawMoney(s.getCurrentRentPrice());
            s.getOwner().earnMoney(s.getCurrentRentPrice());
        }else{
            sellProperty(s.getCurrentRentPrice());
        }
    }

    public void payTax(SpaceTax s){
        if(isAffordable(s.getTax())){
            withdrawMoney(s.getTax());
        }else{
            sellProperty(s.getTax());
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
        for(int k=1; k< prioritySpecial.size(); k++){
            if(prioritySpecial.get(k) instanceof SpacePublicService){
                prioritySpecial.remove(prioritySpecial.get(k));
                prioritySpecial.add(0,prioritySpecial.get(k));
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
            }    

        ArrayList<SpaceToBuy> priority = this.arrangePriority();
       
        int index = 0;
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
                index++; //color monopolist change status
            }

            
            if(index>=priority.size()){ // optimser plus , also think about when we update currentRent, currentResell price
                while(payment > this.checkBalance()){
                    for(SpaceToBuy s : priority){
                        if(s instanceof SpaceCity){
                            SpaceCity currentCity = (SpaceCity) s;
                            if(currentCity.owner == this){
                                System.out.println(this.getName()+"sell his");
                                this.earnMoney((int)(currentCity.getPrice()*0.75));
                                s.setOwner(null);
                                this.property.remove(currentCity);
                                this.colorsetProperty.remove(currentCity);
                                currentCity.getColor().setColorMonopolist(null);
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

}
