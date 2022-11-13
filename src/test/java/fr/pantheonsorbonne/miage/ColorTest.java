package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceCity;

class ColorTest {
    
    @Test
    public void spacesTest(){
        Player p = new Player("Linda");
        Color marron = new Color("marron",50);
        SpaceCity s =new SpaceCity("Boulevard de Bellvile",1,marron,60, new int[] {2,10,30,90,160,250});
        assert(marron.getSpaces().contains(s));

    }

    @Test
    public void isColorMonoplistTest(){
        Player p = new Player("Linda");
        Color bleuClair = new Color("bleuClair",50);
        SpaceCity s = new SpaceCity("Rue de Vaugirard",6,bleuClair,100,new int[] {6,30,90,270,400,550});
        SpaceCity s1 =new SpaceCity("Rue de Courcelles",8,bleuClair,100, new int[] {6,30,90,270,400,550});
        SpaceCity s2 =new SpaceCity("Avenue de la République",9,bleuClair,120,new int[] {8,40,100,300,450,600});
        p.buyLand(s2); p.buyLand(s); p.buyLand(s1);
        assertEquals(bleuClair.isColorOwned(),true );
        assertEquals(bleuClair.getColorMonopolist(), p);
        
    }
    
    @Test
    public void isColorMonoplistTest2(){
        Color rose = new Color("rose", 100);
        SpaceCity s1 = new SpaceCity("Boulevard de la Villette",11,rose,140,new int[] {10,50,150,450,625,750});
        SpaceCity s2 = new SpaceCity("Avenue de Neuilly",13,rose,140,new int[] {10,50,150,450,625,750});
        SpaceCity s3 = new SpaceCity("Rue de Paradis",14,rose,160,new int[] {12,60,180,500,700,900});
        Player p = new Player("Linda");
        Player y = new Player("Yewon");
        p.buyLand(s3);
        p.buyLand(s2);
        y.buyLand(s1);
        assertFalse(rose.isColorOwned()); 
        assertEquals( s1.getCurrentRentPrice(),10);
    }
}
