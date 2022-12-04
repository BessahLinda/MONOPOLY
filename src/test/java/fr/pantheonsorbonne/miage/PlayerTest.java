package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceCity;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpacePublicService;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceStation;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceTax;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceToBuy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PlayerTest {

    @Test
    public void testAdvance() {
        Player p = new Player("Linda", new Strategy());
        p.advance(7);
        assertEquals(7, p.getPosition());
        p.advance(38);
        assertEquals(5,p.getPosition());
        assertEquals(1700, p.checkBalance());

        Player p2 = new Player("Yewon", new Strategy());
        p2.advance(44);
        assertEquals(4,p2.getPosition());
        assertEquals(1700, p2.checkBalance());
        
        Player p3 = new Player("Linda", new Strategy());
        p3.advance(32);
        assertEquals(32,p3.getPosition());
        p3.advance(8);
        assertEquals(0, p3.getPosition());
    }

    @Test
    public void testIsAffordable() {
        Player p = new Player("Linda", new Strategy());
        assertEquals(true,p.isAffordable(200));
        assertEquals(false,p.isAffordable(2003));
    }    
    
    @Test
    public void testBuyCity() {
        Player p = new Player("Linda", new Strategy());
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
        Player p = new Player("Linda", new Strategy());
        p.withdrawMoney(600);
        assertEquals(p.checkBalance(), 1500-600);
    }

    @Test
    public void testAddMoney() {
        Player p = new Player("Linda", new Strategy());
        p.earnMoney(600);
        assertEquals(p.checkBalance(), 1500+600);
    }

    @Test
    public void testBankrupt() {
        Player p = new Player("Linda", new Strategy());
        assertEquals(false,p.isBankrupt());
        p.withdrawMoney(1600);
        assertEquals(true,p.isBankrupt());
    }  
    
    @Test
    public void testPayRentCity(){
        Player p = new Player("Linda", new Strategy());
        Color marron = new Color("marron",50);
        SpaceCity s =new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250});
        p.buyLand(s);
        Player p2 = new Player("Yewon", new Strategy());
        p2.payRent(s);
        assertEquals(p.checkBalance(), (1500-s.getPrice()+s.getCurrentRentPrice()));
    }

    public void testPayRentCity2(){
        Color rose = new Color("rose", 100);
        SpaceCity s1 = new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity s2 = new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750});
        SpaceCity s3 = new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900});
        Player p = new Player("Linda", new Strategy());
        p.buyLand(s1);
        p.buyLand(s2);
        p.buyLand(s3);
        assertEquals(p.checkBalance(), (1500-s1.getPrice()-s2.getPrice()-s3.getPrice()));
        Player p2 = new Player("Yewon", new Strategy());
        p2.payRent(s1);
        assertEquals(20, s1.getCurrentRentPrice());
        assertEquals(p.checkBalance(), 1500-s1.getPrice()-s2.getPrice()-s3.getPrice()+s1.getCurrentRentPrice());
    }

    @Test
    public void testGoToJail(){
        Player p = new Player("Linda", new Strategy());
        p.goToJail();
        assertEquals(true, p.isInJail());
        assertEquals(10, p.getPosition());
    }

    @Test 
    public void testGoOutJail(){
        Player p = new Player("Linda", new Strategy());
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
        Player p = new Player("Linda", new Strategy());
        p.payTax(s);
        assertEquals(p.checkBalance(), 1500-100);
    }

    @Test
    public void buyStation(){
        SpaceToBuy s = new SpaceStation("Gare Saint-Lazare", 35,200);
        Player p = new Player("Linda", new Strategy());
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
        Player p = new Player("Linda", new Strategy());
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
        Player p = new Player("Linda", new Strategy());
        SpaceToBuy s = new SpaceStation("Gare Saint-Lazare", 35,200);
        p.buyLand(s);
        Player p2 = new Player("Yewon", new Strategy());
        p2.payRent(s);
        assertEquals(p.checkBalance(), (1500-s.getPrice()+s.getCurrentRentPrice()));
        SpaceToBuy s1 = new SpaceStation("Gare de Lyon", 15,200);
        p.buyLand(s1);
        p2.payRent(s1);
        assertEquals(p2.checkBalance(), 1500-25-50);
    }





    @Test
    public void testAllCitiesOwnMaison(){
        Player p2 = new Player("Yewon", new Strategy());
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
        Player p2 = new Player("Yewon", new Strategy());
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
        Player p2 = new Player("Yewon", new Strategy());
        SpaceStation st = new SpaceStation("Gare Saint-Lazare", 35,200);
        SpaceStation st2 = new SpaceStation("Gare du Nord", 35,200);
        p2.buyLand(st2);
        p2.buyLand(st);
        assertEquals(2, p2.getNbStation());
    }

    @Test
    public void testGetNbServicePublic(){
        Player p2 = new Player("Yewon", new Strategy());
        SpaceToBuy st = new SpacePublicService("Gare Saint-Lazare", 35,200);
        SpaceToBuy st2 = new SpacePublicService("Gare du Nord", 35,200);
        p2.buyLand(st2);
        p2.buyLand(st);
        assertEquals(2, p2.getNbServicePublic());
    }


}