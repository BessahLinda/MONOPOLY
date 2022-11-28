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

class PlayerTest {

    @Test
    public void testAdvance() {
        Player p = new Player("Linda");
        p.advance(7);
        assertEquals(7, p.getPosition());
        p.advance(38);
        assertEquals(5,p.getPosition());
        assertEquals(1700, p.checkBalance());

        Player p2 = new Player("Yewon");
        p2.advance(44);
        assertEquals(4,p2.getPosition());
        assertEquals(1700, p2.checkBalance());
        
        Player p3 = new Player("Linda");
        p3.advance(32);
        assertEquals(32,p3.getPosition());
        p3.advance(8);
        assertEquals(0, p3.getPosition());
    }

    @Test
    public void testIsAffordable() {
        Player p = new Player("Linda");
        assertEquals(true,p.isAffordable(200));
        assertEquals(false,p.isAffordable(2003));
    }    
    
    @Test
    public void testBuyCity() {
        Player p = new Player("Linda");
        Color bleuClair = new Color("bleuClair",50);
        SpaceCity s = new SpaceCity("Rue de Vaugirard",6,100,bleuClair,new int[] {6,30,90,270,400,550});
        SpaceCity s1 =new SpaceCity("Rue de Courcelles",8,100,bleuClair, new int[] {6,30,90,270,400,550});
        SpaceCity s2 =new SpaceCity("Avenue de la République",9,120,bleuClair,new int[] {8,40,100,300,450,600});
        p.buyLand(s);p.buyLand(s2);
        assertEquals(s.getOwner(), p);
        assertEquals(s2.getOwner(), p);
        assertEquals(null, bleuClair.getColorMonopolist());
        assertEquals(p.checkBalance(), 1500-100-120);
        assertEquals(6, s.getCurrentRentPrice());

        p.buyLand(s1);
        assertEquals(bleuClair.getColorMonopolist(), p);
        assertEquals(12, s.getCurrentRentPrice());
        assertEquals(16, s2.getCurrentRentPrice());
    } 

    @Test
    public void testWithdrawMoney() {
        Player p = new Player("Linda");
        p.withdrawMoney(600);
        assertEquals(p.checkBalance(), 1500-600);
    }

    @Test
    public void testAddMoney() {
        Player p = new Player("Linda");
        p.earnMoney(600);
        assertEquals(p.checkBalance(), 1500+600);
    }

    @Test
    public void testBankrupt() {
        Player p = new Player("Linda");
        assertEquals(false,p.isBankrupt());
        p.withdrawMoney(1600);
        assertEquals(true,p.isBankrupt());
    }  
    
    @Test
    public void testPayRentCity(){
        Player p = new Player("Linda");
        Color marron = new Color("marron",50);
        SpaceCity s =new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250});
        p.buyLand(s);
        Player p2 = new Player("Yewon");
        p2.payRent(s);
        assertEquals(p.checkBalance(), (1500-s.getPrice()+s.getCurrentRentPrice()));
    }

    public void testPayRentCity2(){
        Color rose = new Color("rose", 100);
        SpaceCity s1 = new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity s2 = new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity s3 = new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        Player p = new Player("Linda");
        p.buyLand(s1);
        p.buyLand(s2);
        p.buyLand(s3);
        assertEquals(p.checkBalance(), (1500-s1.getPrice()-s2.getPrice()-s3.getPrice()));
        Player p2 = new Player("Yewon");
        p2.payRent(s1);
        assertEquals(20, s1.getCurrentRentPrice());
        assertEquals(p.checkBalance(), 1500-s1.getPrice()-s2.getPrice()-s3.getPrice()+s1.getCurrentRentPrice());
    }

    @Test
    public void testGoToJail(){
        Player p = new Player("Linda");
        p.goToJail();
        assertEquals(true, p.isInJail());
        assertEquals(10, p.getPosition());
    }

    @Test 
    public void testGoOutJail(){
        Player p = new Player("Linda");
        p.goToJail();
        p.goOutJail(50  );
        assertEquals(false, p.isInJail());
        assertEquals(p.checkBalance(),1500-50); 
    }

    @Test
    public void payTaxTest(){
        SpaceTax s = new SpaceTax("impot", 0, 100);
        Player p = new Player("Linda");
        p.payTax(s);
        assertEquals(p.checkBalance(), 1500-100);
    }

    @Test
    public void buyStation(){
        SpaceToBuy s = new SpaceStation("Gare Saint-Lazare", 35,200);
        Player p = new Player("Linda");
        p.buyLand(s);
        assertEquals(s.getOwner(), p);
        assertEquals(p.checkBalance(), 1500-200);
        assertEquals(25, s.getCurrentRentPrice());
        assertEquals(1, p.getNbStation());

        SpaceToBuy s1 = new SpaceStation("Gare de Lyon", 15,200);
        p.buyLand(s1);

        assertEquals(p.checkBalance(), 1500-400);
        assertEquals(2, p.getNbStation());
        assertEquals(50, s.getCurrentRentPrice());
        assertEquals(50, s1.getCurrentRentPrice());
    }

    @Test
    public void buyServicePublic(){
        SpaceToBuy s = new SpacePublicService("Compagine de distribution des eaux", 28,150);
        Player p = new Player("Linda");
        p.buyLand(s);
        assertEquals(s.getOwner(), p);
        assertEquals(p.checkBalance(), 1500-150);
        s.getCurrentRentPrice();
        assertEquals(1, p.getNbServicePublic());

        SpaceToBuy s1 = new SpacePublicService("Compagine de distribution d'éléctricité", 12,150);
        p.buyLand(s1);

        assertEquals(p.checkBalance(), 1500-300);
        assertEquals(2, p.getNbServicePublic());
    }

    @Test
    public void testPayRentStation(){
        Player p = new Player("Linda");
        SpaceToBuy s = new SpaceStation("Gare Saint-Lazare", 35,200);
        p.buyLand(s);
        Player p2 = new Player("Yewon");
        p2.payRent(s);
        assertEquals(p.checkBalance(), (1500-s.getPrice()+s.getCurrentRentPrice()));
        SpaceToBuy s1 = new SpaceStation("Gare de Lyon", 15,200);
        p.buyLand(s1);
        p2.payRent(s1);
        assertEquals(p2.checkBalance(), 1500-25-50);
    }


    @Test
    public void testBuildHouse(){
        Player p2 = new Player("Yewon");
        Color bleuClair = new Color("bleuClair",50);
        SpaceCity s = new SpaceCity("Rue de Vaugirard",6,100,bleuClair,new int[] {6,30,90,270,400,550});
        SpaceCity s1 =new SpaceCity("Rue de Courcelles",8,100,bleuClair, new int[] {6,30,90,270,400,550});
        SpaceCity s2 =new SpaceCity("Avenue de la République",9,120,bleuClair,new int[] {8,40,100,300,450,600});
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);
        p2.buildHouse();
        assertEquals(0,s2.getNbHouse());
    }

    
    @Test
    public void testBuildHouse2(){
        Player p2 = new Player("Yewon");
        Color orange = new Color("orange",100);
        SpaceCity s = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s1 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s2 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
    
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);
        p2.buildHouse();
        assertEquals(2,s2.getNbHouse());
        p2.checkBalance();
    }

    
    @Test
    public void testBuildHouse3(){
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
        p2.buildHouse();
        assertEquals(2,r3.getNbHouse());
        p2.checkBalance();
    }

    @Test
    public void testSellProperty(){  //check if it's selling non coloset space first
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
        SpaceCity j1 = new SpaceCity("Place de la Bourse",27,260,jaune,new int[] {22,110,330,800,975,1150});
        SpaceCity j2 = new SpaceCity("Rue de la Fayette",29,280,jaune,new int[] {22,120,360,850,1025,1200});

        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);p2.buyLand(j);

        List<SpaceToBuy> result = new ArrayList<>(Arrays.asList(r3,r1,r2,s,s1,s2));

        p2.earnMoney(260); //500 
        p2.sellProperty(550);
        assertEquals(result,p2.getProperty());
    }


    @Test
    public void testSellProperty2(){  //check if it keeps colorset-land and only sells maison 
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
        SpaceCity j1 = new SpaceCity("Place de la Bourse",27,260,jaune,new int[] {22,110,330,800,975,1150});
        SpaceCity j2 = new SpaceCity("Rue de la Fayette",29,280,jaune,new int[] {22,120,360,850,1025,1200});
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        SpaceToBuy sp = new SpacePublicService("Compagine de distribution des eaux", 28,150);

        p2.earnMoney(460);
        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);p2.buyLand(j);p2.buyLand(st); p2.buyLand(sp);
        s.buildHouse(1);s1.buildHouse(1);s2.buildHouse(1); 
        r1.buildHouse(1);r2.buildHouse(1);r3.buildHouse(1); 
        j.buildHouse(2); 

        List<SpaceToBuy> result = new ArrayList<>(Arrays.asList(r3,r1,r2,s,s1,s2,st,sp));
        assertEquals(1, r2.getNbHouse());

        p2.sellProperty(1369);//500
        assertEquals(0, s.getNbHouse());
        assertEquals(0, r1.getNbHouse());
        assertEquals(result,p2.getProperty());

    }

    // @Test
    // public void testSellProperty4(){  
    //  //space public service & space station     
    // }

    @Test
    public void testArrangePriority(){
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
        assertEquals(result,p2.arrangePriority());
        
    }

    @Test
    public void testAllCitiesOwnMaison(){
        Player p2 = new Player("Yewon");
        Color orange = new Color("orange",100);
        Color rose = new Color("rose", 100);
        SpaceCity s = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s1 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s2 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
        SpaceCity r1 = new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity r2 = new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity r3 = new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        

        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);

        s.buildHouse(1);s1.buildHouse(1);s2.buildHouse(1);r1.buildHouse(1);r2.buildHouse(1);r3.buildHouse(1);
        assert(p2.allCitiesOwnMaison());

    }

    @Test
    public void testSetRentOfProperties(){
        Player p2 = new Player("Yewon");
        Color orange = new Color("orange",100);
        Color rose = new Color("rose", 100);
        SpaceCity s = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s1 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s2 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
        SpaceCity r1 = new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity r2 = new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity r3 = new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        SpaceStation st2 = new SpaceStation("Gare du Nord", 35,200);
        p2.buyLand(r3);p2.buyLand(r1);p2.buyLand(r2);
        p2.buyLand(s);p2.buyLand(s1);p2.buyLand(s2);
        p2.earnMoney(1000);
        p2.buyLand(st2);
        p2.buyLand(st);

        p2.setRentOfProperties();
        assertEquals(st.getCurrentRentPrice(), 50);
        assertEquals(st2.getCurrentRentPrice(), 50);
        assertEquals(s.getCurrentRentPrice(), 14*2);


    }

    @Test
    public void testGetNbStation(){
        Player p2 = new Player("Yewon");
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        SpaceStation st2 = new SpaceStation("Gare du Nord", 35,200);
        p2.buyLand(st2);
        p2.buyLand(st);
        assertEquals(p2.getNbStation(), 2);
    }

    @Test
    public void testGetNbServicePublic(){
        Player p2 = new Player("Yewon");
        SpaceToBuy st = new SpacePublicService("Gare Saint-Lazare", 35,200);
        SpaceToBuy st2 = new SpacePublicService("Gare du Nord", 35,200);
        p2.buyLand(st2);
        p2.buyLand(st);
        assertEquals(p2.getNbServicePublic(), 2);
    }


}