package fr.pantheonsorbonne.miage.game.monopoly.elements;


public  class Dice {
    
    private int dice1;
    private int dice2;
    private boolean isDouble;

    public int rollDice(){
        isDouble = false;
        this.dice1= (int) ((Math.random() * 6) + 1);
        this.dice2 = (int) ((Math.random() * 6) + 1);

        if(dice1==dice2){
            isDouble = true;
        }
        
        return(dice1+dice2);
    }

    public int rollIfDouble(){
        if(isDouble){
            rollDice();
        }
        return 0;
    }


    public boolean isDouble(){
        if(dice1==dice2){
            return true;
        }
        return false;
    }
}

