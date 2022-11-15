package fr.pantheonsorbonne.miage.game.monopoly.elements;

public class Space {

    private final String name;
    private final int index;

    public Space(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getName(){
        return this.name;
    }

    
    public int getIndex(){
        return this.index;
    }
}
