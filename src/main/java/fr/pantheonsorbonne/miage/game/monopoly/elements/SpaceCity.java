package fr.pantheonsorbonne.miage.game.monopoly.elements;

public class SpaceCity extends SpaceToBuy {

    private Player owner; 
    private final Color color;
    private int price;
    private final int[] rentPrice;
    private int currentRentPrice;
    private int nbHouse = 0; 

    public SpaceCity(String name, int index,Color color, int price, int[] rentPrice) {
        super(name, index, price);
        this.color = color;
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
            this.currentRentPrice = rentPrice[4];
        } else if(this.nbHouse == 3){
            this.currentRentPrice = rentPrice[3];
        }else if(this.nbHouse == 2){
            this.currentRentPrice = rentPrice[2];
        }else if(this.nbHouse == 1){
            this.currentRentPrice = rentPrice[1];
        }else{
            this.currentRentPrice = rentPrice[0];
        }
        if(this.color.isColorOwned()){
            this.currentRentPrice = this.currentRentPrice*2; 
        }
    }

    @Override
    public int getCurrentRentPrice(){
        return this.currentRentPrice;
    }

    public void buildHouse(int nbHouse){
        this.nbHouse += nbHouse;
    }
    
    public int getNbHouse() {
        return nbHouse;
    }

    public Color getColor(){
        return color;
    }

    public Player getOwner(){
        return this.owner;
    }

    

    public int getCurrentResellPrice(){
        return (int)((price+nbHouse*this.color.getHousePrice())*0.75);
    }

    // public SpaceCity getSpacecityInfoByIndex(int index){
        
    ///???
    // }
}


