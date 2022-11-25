package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceCity;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceStation;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceToBuy;

class SpaceCityTest {

    @Test
    public void setCurrentRentPriceTest(){
        Color marron = new Color("marron",50);
        SpaceCity s = new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250}); 
        Player p = new Player("Linda");
        assertEquals(2,s.getCurrentRentPrice());
        p.buyLand(s);
        marron.setColorMonopolist(p );
        s.setCurrentRentPrice();
        assertEquals(4, s.getCurrentRentPrice());
        s.buildHouse(3);
        s.setCurrentRentPrice();
        assertEquals(90, s.getCurrentRentPrice());
    }

    @Test
    public void setCurrentRentPriceTest2(){ //spaceStation
        Player p = new Player("Yewon");
        Color marron = new Color("marron",50);
        assertEquals(p,marron.getColorMonopolist());
        SpaceCity s = new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250}); 
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        p.buyLand(s); p.buyLand(st);
        p.setRentOfProperties();
        assertEquals(25, st.getCurrentRentPrice());
        assertEquals(p,marron.getColorMonopolist()); //??
        assertEquals(false, marron.isColorOwned());
        assertEquals(2,s.getCurrentRentPrice());

    }

}
