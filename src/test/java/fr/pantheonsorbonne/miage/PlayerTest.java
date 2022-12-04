package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceCity;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpacePublicService;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceStation;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceTax;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceToBuy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.GoodStrategy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.Strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PlayerTest {

    @Test
    public void testAdvance() {
        Player p = new Player("Linda", new GoodStrategy());
        p.advance(7);
        assertEquals(7, p.getPosition());
        p.advance(38);
        assertEquals(5,p.getPosition());
        assertEquals(1700, p.checkBalance());

        Player p2 = new Player("Yewon", new GoodStrategy());
        p2.advance(44);
        assertEquals(4,p2.getPosition());
        assertEquals(1700, p2.checkBalance());
        
        Player p3 = new Player("Linda", new GoodStrategy());
        p3.advance(32);
        assertEquals(32,p3.getPosition());
        p3.advance(8);
        assertEquals(0, p3.getPosition());
    }

    @Test
    public void testIsAffordable() {
        Player p = new Player("Linda", new GoodStrategy());
        assertEquals(true,p.isAffordable(200));
        assertEquals(false,p.isAffordable(2003));
    }    
    
    @Test
    public void testBuyCity() {
        Player p = new Player("Linda", new GoodStrategy());
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
        Player p = new Player("Linda", new GoodStrategy());
        p.withdrawMoney(600);
        assertEquals(p.checkBalance(), 1500-600);
    }

    @Test
    public void testAddMoney() {
        Player p = new Player("Linda", new GoodStrategy());
        p.earnMoney(600);
        assertEquals(p.checkBalance(), 1500+600);
    }

    @Test
    public void testBankrupt() {
        Player p = new Player("Linda", new GoodStrategy());
        assertEquals(false,p.isBankrupt());
        p.withdrawMoney(1600);
        assertEquals(true,p.isBankrupt());
    }  
    
    @Test
    public void testPayRentCity(){
        Player p = new Player("Linda", new GoodStrategy());
        Color marron = new Color("marron",50);
        SpaceCity s =new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250});
        p.buyLand(s);
        Player p2 = new Player("Yewon", new GoodStrategy());
        p2.payRent(s);
        assertEquals(p.checkBalance(), (1500-s.getPrice()+s.getCurrentRentPrice()));
    }

    public void testPayRentCity2(){
        Color rose = new Color("rose", 100);
        SpaceCity s1 = new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity s2 = new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity s3 = new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        Player p = new Player("Linda", new GoodStrategy());
        p.buyLand(s1);
        p.buyLand(s2);
        p.buyLand(s3);
        assertEquals(p.checkBalance(), (1500-s1.getPrice()-s2.getPrice()-s3.getPrice()));
        Player p2 = new Player("Yewon", new GoodStrategy());
        p2.payRent(s1);
        assertEquals(20, s1.getCurrentRentPrice());
        assertEquals(p.checkBalance(), 1500-s1.getPrice()-s2.getPrice()-s3.getPrice()+s1.getCurrentRentPrice());
    }

    @Test
    public void testGoToJail(){
        Player p = new Player("Linda", new GoodStrategy());
        p.goToJail();
        assertEquals(true, p.isInJail());
        assertEquals(10, p.getPosition());
    }

    @Test 
    public void testGoOutJail(){
        Player p = new Player("Linda", new GoodStrategy());
        p.goToJail();
        p.goOutJail(50  );
        assertEquals(false, p.isInJail());
        assertEquals(p.checkBalance(),1500-50); 

        p.goToJail();
        p.goOutJail();
        assertEquals(false, p.isInJail());
    }

    @Test
    public void payTaxTest(){
        SpaceTax s = new SpaceTax("impot", 0, 100);
        Player p = new Player("Linda", new GoodStrategy());
        p.payTax(s);
        assertEquals(p.checkBalance(), 1500-100);
    }

    @Test
    public void buyStation(){
        SpaceToBuy s = new SpaceStation("Gare Saint-Lazare", 35,200);
        Player p = new Player("Linda", new GoodStrategy());
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
        Player p = new Player("Linda", new GoodStrategy());
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
        Player p = new Player("Linda", new GoodStrategy());
        SpaceToBuy s = new SpaceStation("Gare Saint-Lazare", 35,200);
        p.buyLand(s);
        Player p2 = new Player("Yewon", new GoodStrategy());
        p2.payRent(s);
        assertEquals(p.checkBalance(), (1500-s.getPrice()+s.getCurrentRentPrice()));
        SpaceToBuy s1 = new SpaceStation("Gare de Lyon", 15,200);
        p.buyLand(s1);
        p2.payRent(s1);
        assertEquals(p2.checkBalance(), 1500-25-50);
    }





    @Test
    public void testAllCitiesOwnMaison(){
        Player p2 = new Player("Yewon", new GoodStrategy());
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
        Player p2 = new Player("Yewon", new GoodStrategy());
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
        assertEquals(50, st.getCurrentRentPrice());
        assertEquals(50, st2.getCurrentRentPrice());
        assertEquals(s.getCurrentRentPrice(), 14*2);


    }

    @Test
    public void testGetNbStation(){
        Player p2 = new Player("Yewon", new GoodStrategy());
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        SpaceStation st2 = new SpaceStation("Gare du Nord", 35,200);
        p2.buyLand(st2);
        p2.buyLand(st);
        assertEquals(2, p2.getNbStation());
    }

    @Test
    public void testGetNbServicePublic(){
        Player p2 = new Player("Yewon", new GoodStrategy());
        SpaceToBuy st = new SpacePublicService("Gare Saint-Lazare", 35,200);
        SpaceToBuy st2 = new SpacePublicService("Gare du Nord", 35,200);
        p2.buyLand(st2);
        p2.buyLand(st);
        assertEquals(2, p2.getNbServicePublic());
    }

    @Test
    public void forceToSellSpaceTest(){
        Player p1 = new Player("LINDA", new GoodStrategy());
        Player p2 = new Player("Yewon", new GoodStrategy());

        Color bleuClair = new Color("bleuClair",50);
        SpaceCity s3 = new SpaceCity("Avenue de la République",9,120,bleuClair,new int[] {8,40,100,300,450,600});
        SpaceCity s4 = new SpaceCity("Rue de Vaugirard",6,100,bleuClair,new int[] {6,30,90,270,400,550});
        SpaceCity s5 = new SpaceCity("Rue de Courcelles",8,100,bleuClair, new int[] {6,30,90,270,400,550});


        p1.buyLand(s3); p1.buyLand(s4);
        p2.buyLand(s5);

        p2.forceToSellSpace();

        assertEquals(p1, bleuClair.getColorMonopolist());

    }

    @Test
    public void forceToSellSpaceTest2(){
        Player p1 = new Player("LINDA", new GoodStrategy());
        Player p2 = new Player("Yewon", new GoodStrategy());

        Color bleuClair = new Color("bleuClair",50);
        SpaceCity s3 = new SpaceCity("Avenue de la République",9,120,bleuClair,new int[] {8,40,100,300,450,600});
        SpaceCity s4 = new SpaceCity("Rue de Vaugirard",6,100,bleuClair,new int[] {6,30,90,270,400,550});
        SpaceCity s5 = new SpaceCity("Rue de Courcelles",8,100,bleuClair, new int[] {6,30,90,270,400,550});


        p1.buyLand(s3); p1.buyLand(s4);
        p2.buyLand(s5);

        p2.earnMoney(1000);
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

        p2.forceToSellSpace();

        assertEquals(p1, bleuClair.getColorMonopolist());
        assertEquals(p1,s4.getOwner() );
    }


    @Test
    public void forceToSellSpaceTest3(){
        Player p1 = new Player("LINDA", new GoodStrategy());
        Player p2 = new Player("Yewon", new GoodStrategy());

        Color bleuClair = new Color("bleuClair",50);
        Color rose = new Color("rose", 100);
        Color orange = new Color("orange",100);
        Color rouge = new Color("rouge",150);
        Color jaune = new Color("jaune",150);
        

    
        

        SpaceCity s3 = new SpaceCity("Avenue de la République",9,120,bleuClair,new int[] {8,40,100,300,450,600});
        SpaceCity s4 = new SpaceCity("Rue de Vaugirard",6,100,bleuClair,new int[] {6,30,90,270,400,550});
        SpaceCity s5 = new SpaceCity("Rue de Courcelles",8,100,bleuClair, new int[] {6,30,90,270,400,550});
        
        SpaceCity s6 = new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        SpaceCity s7 = new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity s8 = new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        
        SpaceCity s9 = new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000});
        SpaceCity s10 = new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950});
        SpaceCity s11 = new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950});

        SpaceCity s12 = new SpaceCity("Avenue Henri-Martin",24,240,rouge,new int[] {20,100,300,750,925,1100});
        SpaceCity s13 = new SpaceCity("Avenue Matignon",21,220,rouge,new int[] {18,90,250,700,875,1050});
        SpaceCity s14 = new SpaceCity("Boulevard Malesherbes",23,220,rouge,new int[] {18,90,250,700,875,1050});

        SpaceCity s15 = new SpaceCity("Rue de la Fayette",29,280,jaune,new int[] {22,120,360,850,1025,1200});
        SpaceCity s16 = new SpaceCity("Faubourg Saint-Honoré",26,260,jaune,new int[] {22,110,330,800,975,1150});
        SpaceCity s17 = new SpaceCity("Place de la Bourse",27,260,jaune,new int[] {22,110,330,800,975,1150});
        
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        SpaceStation st2 = new SpaceStation("Gare du Nord", 35,200);

        p1.earnMoney(10000);
        p2.earnMoney(10000);

       p1.buyLand(s3);p1.buyLand(s6);p1.buyLand(st);p1.buyLand(s15);p1.buyLand(s8);
       p2.buyLand(st2);p1.buyLand(s16);p2.buyLand(s10);p2.buyLand(s7);
        
       p2.forceToSellSpace();
       p1.forceToSellSpace();;
       

        assertEquals(p1, rose.getColorMonopolist());
        
    }


}