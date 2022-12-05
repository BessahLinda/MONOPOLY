package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceStation;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceToBuy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.Strategy;


class SpaceStationTest {
    
    @Test
    public void setCurrentRentPriceTest(){
        SpaceToBuy s = new SpaceStation("Gare Saint-Lazare", 35,200);
        Player p = new Player("Linda", new Strategy());
        p.buyLand(s);
        SpaceToBuy s1 = new SpaceStation("Gare de Lyon", 15,200);
        p.buyLand(s1);
        assertEquals(50, s.getCurrentRentPrice());
        assertEquals(50, s1.getCurrentRentPrice());
    }

}
