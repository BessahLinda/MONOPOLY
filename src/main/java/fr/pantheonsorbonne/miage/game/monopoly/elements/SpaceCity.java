package fr.pantheonsorbonne.miage.game.monopoly.elements;

public class SpaceCity extends SpaceToBuy implements Comparable{

    
    private final Color color;
    private final int[] rentPrice;
    private int nbHouse = 0; 

    public SpaceCity(String name, int index,int price,Color color, int[] rentPrice) {
        super(name, index, price);
        this.color = color;
        this.rentPrice = rentPrice;
        currentRentPrice = rentPrice[0];
        color.getSpaces().add(this);
    }

    // depends on the number of houses, rent price changes
    @Override
    public void setCurrentRentPrice(){
        if(this.nbHouse == 4){
            this.currentRentPrice = rentPrice[4];
        } else if(this.nbHouse == 3){
            this.currentRentPrice = rentPrice[3];
        }else if(this.nbHouse == 2){
            this.currentRentPrice = rentPrice[2];
        }else if(this.nbHouse == 1){
            this.currentRentPrice = rentPrice[1];
        }else if(this.color.isColorOwned()){
            this.currentRentPrice = rentPrice[0]*2;
        }else{
            this.currentRentPrice = rentPrice[0];
        }    
    }

    public void buildHouse(int nbHouse){
        this.nbHouse += nbHouse;
    }

    public void deconstructHouse(){
        this.nbHouse --;
    }
    
    public int getNbHouse() {
        return nbHouse;
    }

    public Color getColor(){
        return color;
    }

    @Override
    public int getCurrentResellPrice(){
        return (int)((this.price +nbHouse*this.color.getHousePrice())*0.75);
    }

    @Override
    public int compareTo(Object o) {
       if(o instanceof SpaceCity){
           SpaceCity otherColor = (SpaceCity) o;
           return this.getColor().getValue()-otherColor.getColor().getValue();
       }
       throw new RuntimeException("not a color");
    }
    
}

