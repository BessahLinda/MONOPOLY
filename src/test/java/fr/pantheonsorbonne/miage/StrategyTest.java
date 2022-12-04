package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceCity;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpacePublicService;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceStation;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceToBuy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class StrategyTest {

    @Test
    public void testBuydHouse(){
        Player p2 = new Player("Yewon", new Strategy());
        Color bleuClair = new Color("bleuClair",50);
        SpaceCity s = new SpaceCity("Rue de Vaugirard",6,100,bleuClair,new int[] {6,30,90,270,400,550});
        SpaceCity s1 =new SpaceCity("Rue de Courcelles",8,100,bleuClair, new int[] {6,30,90,270,400,550});
        SpaceCity s2 =new SpaceCity("Avenue de la République",9,120,bleuClair,new int[] {8,40,100,300,450,600});
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);
        p2.buyHouse();
        assertEquals(4,s2.getNbHouse());
        assertEquals(4,s.getNbHouse());
        assertEquals(4,s1.getNbHouse());

        assertEquals(450, s2.getCurrentRentPrice());
        assertEquals(400, s1.getCurrentRentPrice());
    }

    
    @Test
    public void testBuydHouse2(){
        Player p2 = new Player("Yewon", new Strategy());
        Color orange = new Color("orange",100);
        SpaceCity s = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s1 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s2 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
    
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);
        p2.buyHouse();
        assertEquals(2,s2.getNbHouse());
        p2.earnMoney(300);
        p2.buyHouse();
        assertEquals(2,s.getNbHouse());
        assertEquals(2,s1.getNbHouse());
        p2.earnMoney(200);
        p2.buyHouse();
        assertEquals(4,s2.getNbHouse());
        p2.earnMoney(50);
        p2.buyHouse();
        assertEquals(2,s.getNbHouse());
    }

    
    @Test
    public void testBuydHouse3(){
         Player p2 = new Player("Yewon", new Strategy());
        Color orange = new Color("orange",100);
        Color rose = new Color("rose", 100);
        Color jaune = new Color("jaune",150);

        SpaceCity s2 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
        SpaceCity s = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s1 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});
       
        SpaceCity r3 = new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        SpaceCity r1 = new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity r2 = new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        
        SpaceCity j = new SpaceCity("Faubourg Saint-Honoré",26,260,jaune,new int[] {22,110,330,800,975,1150});
        
        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);p2.buyLand(j);

        p2.earnMoney(2000);
        p2.buyHouse();
        assertEquals(4,s2.getNbHouse());
        assertEquals(3,s.getNbHouse());
        assertEquals(2,s1.getNbHouse());
        assertEquals(2,r3.getNbHouse());

        p2.earnMoney(100);
        assertEquals(3,s.getNbHouse());
    }


    @Test
    public void testBuyHouse4(){

        Player p = new Player("Linda", new Strategy());
        Color marron = new Color("marron",50);
        Color orange = new Color("orange",100);
        SpaceCity s1 = new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250});
        SpaceCity s2 = new SpaceCity("Rue Lecourbe",3,60,marron,new int[]{4,20,60,180,320,450});
        SpaceCity s9 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
        SpaceCity s10 = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s11 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});

        p.buyLand(s1);p.buyLand(s2);p.buyLand(s9);p.buyLand(s10);p.buyLand(s11);

        p.buyHouse();
        assertEquals(2, s9.getNbHouse());
        assertEquals(2, s10.getNbHouse());

        p.earnMoney(100);
        p.buyHouse();
        assertEquals(1, s11.getNbHouse());

        p.earnMoney(500);
        p.buyHouse();

        assertEquals(2, s11.getNbHouse());

        assertEquals(4, s9.getNbHouse());
        assertEquals(4, s10.getNbHouse());
        assertEquals(0, s1.getNbHouse());
        assertEquals(0, s2.getNbHouse());

        p.earnMoney(500);
        p.buyHouse();

        assertEquals(4, s11.getNbHouse());

    }

    @Test
    public void testArrangePriority(){
        Player p2 = new Player("Yewon", new Strategy());
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
        new SpaceCity("Place de la Bourse",27,260,jaune,new int[] {22,110,330,800,975,1150});
        new SpaceCity("Rue de la Fayette",29,280,jaune,new int[] {22,120,360,850,1025,1200});
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        p2.earnMoney(460); //500 
        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);p2.buyLand(j);p2.buyLand(st); //150
        s.buildHouse(1);s1.buildHouse(1);s1.buildHouse(1); 
        r1.buildHouse(1);r2.buildHouse(1);r3.buildHouse(1); //600
        j.buildHouse(2); 

        List<SpaceToBuy> result = new ArrayList<>(Arrays.asList(j,r1,r2,s2,r3,s,s1,st));
        //assertEquals(result,p2.getStrategy().arrangePriority(p2));
        
    }

    @Test
    public void testBuydHouse5(){
        Player p2 = new Player("Yewon", new Strategy());
        Color orange = new Color("orange",100);
        Color rose = new Color("rose", 100);


        SpaceCity s2 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
        SpaceCity s = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s1 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});
       
        SpaceCity r3 = new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        SpaceCity r1 = new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity r2 = new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        
        
        
        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);

        p2.earnMoney(100);
        p2.buyHouse();
        assertEquals(1,s2.getNbHouse());
        p2.earnMoney(100);
        p2.buyHouse();
        assertEquals(2,s2.getNbHouse());

        p2.earnMoney(100);
        p2.buyHouse();
        assertEquals(1,s.getNbHouse());

        p2.earnMoney(100);
        p2.buyHouse();
        assertEquals(2,s.getNbHouse());

        p2.earnMoney(100);
        p2.buyHouse();
        assertEquals(1,s1.getNbHouse());

        p2.earnMoney(100);
        p2.buyHouse();
        assertEquals(2,s1.getNbHouse());

        p2.earnMoney(100);
        p2.buyHouse();
        assertEquals(1,r3.getNbHouse());

        p2.earnMoney(500);
        p2.buyHouse();
        assertEquals(2,r3.getNbHouse());
        assertEquals(2,r1.getNbHouse());
        assertEquals(2,r2.getNbHouse());

        p2.earnMoney(100);
        p2.buyHouse();
        assertEquals(3,s2.getNbHouse());

        p2.earnMoney(500);
        p2.buyHouse();
        assertEquals(4,s2.getNbHouse());
        assertEquals(4,s.getNbHouse());
        assertEquals(4,s1.getNbHouse());

        Color rouge = new Color("rouge",150);
        SpaceCity s12 = new SpaceCity("Avenue Henri-Martin",24,240,rouge,new int[] {20,100,300,750,925,1100});
        SpaceCity s13 = new SpaceCity("Avenue Matignon",21,220,rouge,new int[] {18,90,250,700,875,1050});
        SpaceCity s14 = new SpaceCity("Boulevard Malesherbes",23,220,rouge,new int[] {18,90,250,700,875,1050});
        p2.earnMoney(1500); p2.buyLand(s14);p2.buyLand(s12);p2.buyLand(s13);

        p2.buyHouse();
        assertEquals(2,s12.getNbHouse());
        assertEquals(2,s13.getNbHouse());
        assertEquals(2,s14.getNbHouse());
        assertEquals(2,r3.getNbHouse());

        p2.earnMoney(100);
        p2.buyHouse();
        assertEquals(3,s12.getNbHouse());


    }

    @Test
    public void testSellProperty(){  //check if it's selling non coloset space first
        Player p2 = new Player("Yewon", new Strategy());
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
        new SpaceCity("Place de la Bourse",27,260,jaune,new int[] {22,110,330,800,975,1150});
        new SpaceCity("Rue de la Fayette",29,280,jaune,new int[] {22,120,360,850,1025,1200});

        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);p2.buyLand(j);

        List<SpaceToBuy> result = new ArrayList<>(Arrays.asList(r3,r1,r2,s,s1,s2));

        p2.earnMoney(260); //500 
        p2.sellProperty(550);
        assertEquals(result,p2.getProperty());
    }


    @Test
    public void testSellProperty2(){  //check if it keeps colorset-land and only sells maison 
        Player p2 = new Player("Yewon", new Strategy());
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
        new SpaceCity("Place de la Bourse",27,260,jaune,new int[] {22,110,330,800,975,1150});
        new SpaceCity("Rue de la Fayette",29,280,jaune,new int[] {22,120,360,850,1025,1200});
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        p2.earnMoney(460);
        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);p2.buyLand(j);p2.buyLand(st); 
        s.buildHouse(1);s1.buildHouse(1);s2.buildHouse(1); 
        r1.buildHouse(1);r2.buildHouse(1);r3.buildHouse(1); 
        j.buildHouse(2); 

        List<SpaceToBuy> result = new ArrayList<>(Arrays.asList(r3,r1,r2,s,s1,s2,st));
        assertEquals(1, r2.getNbHouse());

        p2.sellProperty(1369);//500
        assertEquals(0, s.getNbHouse());
        assertEquals(0, r1.getNbHouse());
        assertEquals(result,p2.getProperty());
    }

    
    @Test
    public void testSellProperty3(){
        Player p2 = new Player("Yewon", new Strategy());
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
        new SpaceCity("Place de la Bourse",27,260,jaune,new int[] {22,110,330,800,975,1150});
        new SpaceCity("Rue de la Fayette",29,280,jaune,new int[] {22,120,360,850,1025,1200});
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        SpaceToBuy sp = new SpacePublicService("Compagine de distribution des eaux", 28,150);

        p2.earnMoney(1060);
        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);p2.buyLand(j);p2.buyLand(st); p2.buyLand(sp);
        s.buildHouse(1);s1.buildHouse(1);s2.buildHouse(1); 
        r1.buildHouse(1);r2.buildHouse(1);r3.buildHouse(1); 
        j.buildHouse(2); 

        List<SpaceToBuy> result = new ArrayList<>(Arrays.asList(r3,r1,r2,s,s1,s2,st,sp));
        assertEquals(1, r2.getNbHouse());

        p2.sellProperty(1369);//500
        assertEquals(1, s.getNbHouse());
        assertEquals(1, r1.getNbHouse());
        assertEquals(result,p2.getProperty());

    }

    @Test
    public void testSellProperty4(){
        Player p2 = new Player("Yewon", new Strategy());
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
        new SpaceCity("Place de la Bourse",27,260,jaune,new int[] {22,110,330,800,975,1150});
        new SpaceCity("Rue de la Fayette",29,280,jaune,new int[] {22,120,360,850,1025,1200});
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        SpaceToBuy sp = new SpacePublicService("Compagine de distribution des eaux", 28,150);

        p2.earnMoney(1060);
        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);p2.buyLand(j);p2.buyLand(st); p2.buyLand(sp);
        s.buildHouse(1);s1.buildHouse(1);s2.buildHouse(1); 
        r1.buildHouse(1);r2.buildHouse(1);r3.buildHouse(1); 
        j.buildHouse(2); 

        List<SpaceToBuy> result = new ArrayList<>(Arrays.asList(r3,r1,r2,s,s1,s2,st,sp));
        assertEquals(1, r2.getNbHouse());

        p2.sellProperty(1369);//500
        assertEquals(1, s.getNbHouse());
        assertEquals(1, r1.getNbHouse());
        assertEquals(result,p2.getProperty());

    }

    @Test // pas assez d'argent pour acheter
    public void testBuydHouse6(){
        Player p2 = new Player("Yewon", new Strategy());
        Color bleuClair = new Color("bleuClair",50);
        SpaceCity s = new SpaceCity("Rue de Vaugirard",6,100,bleuClair,new int[] {6,30,90,270,400,550});
        SpaceCity s1 =new SpaceCity("Rue de Courcelles",8,100,bleuClair, new int[] {6,30,90,270,400,550});
        SpaceCity s2 =new SpaceCity("Avenue de la République",9,120,bleuClair,new int[] {8,40,100,300,450,600});
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);
        p2.withdrawMoney(1000);
        p2.buyHouse();
        assertEquals(0,s2.getNbHouse());
        assertEquals(0,s.getNbHouse());
        assertEquals(0,s1.getNbHouse());

        
        
    }

    

    
}
