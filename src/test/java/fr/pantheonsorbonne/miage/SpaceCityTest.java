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
        Color rose = new Color("rose", 100);
        SpaceCity s = new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250}); 
        SpaceCity s1 = new SpaceCity("Rue Lecourbe",3,60,marron,new int[]{4,20,60,180,320,450});
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        
        p.buyLand(s); p.buyLand(st);
        p.setRentOfProperties();
        assertEquals(25, st.getCurrentRentPrice());
        assertEquals(null,marron.getColorMonopolist()); //??
        assertEquals(false, marron.isColorOwned());
        assertEquals(2,s.getCurrentRentPrice());
    }
}
