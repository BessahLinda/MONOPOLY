package fr.pantheonsorbonne.miage.game.monopoly.elements;

public enum ColorEnum {
    vert(5),
    orange(7),
    rouge(2),
    marron(1),
    bleuClair (6),
    rose (100),
    jaune (150),
    bleu (200);

    int value;

    private ColorEnum(int value) {
        this.value = value;
    }

}
