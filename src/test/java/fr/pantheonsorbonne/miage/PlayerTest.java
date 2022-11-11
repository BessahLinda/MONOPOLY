package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    public void testSetPosition() {
        Player p = new Player("Linda");
        p.setPosition(7);
        assertEquals(p.getPosition(), 7);
        p.setPosition(38);
        assertEquals(p.getPosition(),5);
        assertEquals(p.getMoney(), 1700);
    }

    

}