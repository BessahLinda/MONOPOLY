package fr.pantheonsorbonne.miage.game.monopoly.elements;

public enum ColorEnum {
    vert(5),
    orange(7),
    rouge(2);

    int value;

    private ColorEnum(int value) {
        this.value = value;
    }

}
