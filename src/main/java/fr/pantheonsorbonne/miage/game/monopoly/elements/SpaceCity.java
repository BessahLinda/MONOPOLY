package fr.pantheonsorbonne.miage.game.monopoly.elements;

public class SpaceCity extends Space {

    private Player owner; 
    private Color color;
    private int price;
    private int currentRentPrice;
    private int[] rentPrice;
    private int nbHouse = 0; 

    public SpaceCity(String name, int index,Color color,int price, int[] rentPrice) {
        super(name, index);
        this.color = color;
        this.price =price;
        this.rentPrice = rentPrice;
        currentRentPrice = rentPrice[0];
        color.getSpaces().add(this);

    }

    public void setOwner(Player p){
        this.owner = p;
    }

    public boolean isSpaceOwned() {
        return this.owner != null;
    }

    public void setCurrentRentPrice(){
        if(this.nbHouse == 4){
            this.currentRentPrice = rentPrice[5];
        } else if(this.nbHouse == 3){
            this.currentRentPrice = rentPrice[4];
        }else if(this.nbHouse == 2){
            this.currentRentPrice = rentPrice[3];
        }else if(this.nbHouse == 1){
            this.currentRentPrice = rentPrice[2];
        }else if(this.color.isColorOwned()){
            this.currentRentPrice = rentPrice[1];
        }else{
            this.currentRentPrice = rentPrice[0];
        }
        
    }

    public int getCurrentRentPrice(){
        return this.currentRentPrice;
    }

    public int getNbHouse() {
        return nbHouse;
    }

    public Player getOwner(){
        return this.owner;
    }

    public int getPrice(){
        return price;
    }

    public Color getColor(){
        return color;
    }
}

