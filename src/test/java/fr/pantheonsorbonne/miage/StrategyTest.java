package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceCity;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpacePublicService;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceStation;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceTax;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceToBuy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StrategyTest {

    @Test
    public void testBuydHouse(){
        Player p2 = new Player("Yewon");
        Color bleuClair = new Color("bleuClair",50);
        SpaceCity s = new SpaceCity("Rue de Vaugirard",6,100,bleuClair,new int[] {6,30,90,270,400,550});
        SpaceCity s1 =new SpaceCity("Rue de Courcelles",8,100,bleuClair, new int[] {6,30,90,270,400,550});
        SpaceCity s2 =new SpaceCity("Avenue de la République",9,120,bleuClair,new int[] {8,40,100,300,450,600});
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);
        p2.buyHouse();
        assertEquals(0,s2.getNbHouse());
    }

    
    @Test
    public void testBuydHouse2(){
        Player p2 = new Player("Yewon");
        Color orange = new Color("orange",100);
        SpaceCity s = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s1 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s2 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
    
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);
        p2.buyHouse();
        assertEquals(2,s2.getNbHouse());
        p2.checkBalance();
    }

    
    @Test
    public void testBuydHouse3(){
         Player p2 = new Player("Yewon");
        Color orange = new Color("orange",100);
        Color rose = new Color("rose", 100);
        Color jaune = new Color("jaune",150);
        SpaceCity s = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s1 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s2 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
        SpaceCity r1 = new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity r2 = new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity r3 = new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        SpaceCity j = new SpaceCity("Faubourg Saint-Honoré",26,260,jaune,new int[] {22,110,330,800,975,1150});
        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);p2.buyLand(j);
        p2.earnMoney(2000);
        p2.buyHouse();
        assertEquals(2,r3.getNbHouse());
        p2.checkBalance();
    }
}
