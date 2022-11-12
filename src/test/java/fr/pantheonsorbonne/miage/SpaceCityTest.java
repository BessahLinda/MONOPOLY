package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceCity;

class SpaceCityTest {

    @Test
    public void setCurrentRentPriceTest(){
        Color marron = new Color("marron",50);
        SpaceCity s = new SpaceCity("Boulevard de Bellvile",1,marron,60, new int[] {2,10,30,90,160,250}); 
        Player p = new Player("Linda");
        assertEquals(s.getCurrentRentPrice(),2);
        p.buyLand(s);
        marron.setPlayer(p );
        s.setCurrentRentPrice();
        assertEquals(s.getCurrentRentPrice(), 4);
        s.buildHouse(3);
    }
    
}
