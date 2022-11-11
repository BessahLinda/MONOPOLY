package fr.pantheonsorbonne.miage.game.monopoly.elements;


public  class Dice {
    

    private int dice1;
    private int dice2;
//hhj

    

    public int generateRandomVal2(){
        this.dice1= (int) ((Math.random() * 6) + 1);
        this.dice2 = (int) ((Math.random() * 6) + 1);
        return(dice1+dice2);
    }

    public int generateRandomVal1(){
        this.dice1= (int) ((Math.random() * 6) + 1);
        return this.dice1;
    }
    


}

