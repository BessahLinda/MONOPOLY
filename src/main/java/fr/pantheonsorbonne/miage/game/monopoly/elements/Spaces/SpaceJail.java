package fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces;

public class SpaceJail extends Space {

    private int type;
    public static int goToJailLocationIndex;
    public static int jailLocationIndex;

    public SpaceJail(String name, int index, int type) {
        super(name, index);
        this.type = type;
        if (type == 0) { // it means this Jail block
            jailLocationIndex = index;
        } else { // it means this goToJail block
            goToJailLocationIndex = index;
        }
    }

    public int getType() {
        return type;
    }
}