package fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces;

public class Space {

    private final String name;
    private final int index;

    public int getIndex() {
        return index;
    }

    public Space(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getName(){
        return this.name;
    }

    
}
