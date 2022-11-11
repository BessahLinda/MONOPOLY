package fr.pantheonsorbonne.miage.game.monopoly.elements;

public class Space {
    private String name;
    private int index;

    public Space(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getName(){
        return this.name;
    }
}
