package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Board;

class BoardTest {

    @Test
    public void spacesTest(){
        Board b = new Board();
        assertEquals("Boulevard de Bellvile", b.getSpaceByIndex(1).getName());
        

    }
}
