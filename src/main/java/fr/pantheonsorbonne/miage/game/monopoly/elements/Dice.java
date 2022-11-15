package fr.pantheonsorbonne.miage.game.monopoly.elements;


public  class Dice {
    
    private int dice1;
    private int dice2;
    
    public int rollDices(){
        this.dice1= (int) ((Math.random() * 6) + 1);
        this.dice2 = (int) ((Math.random() * 6) + 1);
        return(dice1+dice2);
    }

    public boolean isDouble(){
        if(dice1==dice2){
            return true;
        }else{
            return false;
        }
    }

    public int rollDice(){
        this.dice1= (int) ((Math.random() * 6) + 1);
        return this.dice1;
    }
    


}

