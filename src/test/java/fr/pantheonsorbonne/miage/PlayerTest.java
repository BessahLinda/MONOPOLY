package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceCity;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceJail;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceTax;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    public void testAdvance() {
        Player p = new Player("Linda");
        p.advance(7);
        assertEquals(p.getPosition(), 7);
        p.advance(38);
        assertEquals(p.getPosition(),5);
        assertEquals(p.checkBalance(), 1700);

        Player p2 = new Player("Yewon");
        p2.advance(44);
        assertEquals(p2.getPosition(),4);
        assertEquals(p2.checkBalance(), 1700);
        
        Player p3 = new Player("Linda");
        p3.advance(32);
        assertEquals(p3.getPosition(),32);
        p3.advance(8);
        assertEquals(p3.getPosition(), 0);
    }

    @Test
    public void testAffordable() {
        Player p = new Player("Linda");
        assertEquals(p.isAffordable(200),true);
        assertEquals(p.isAffordable(2003),false);
    }    
    
    @Test
    public void testBuyLand() {
        Player p = new Player("Linda");
        Color marron = new Color("marron",50);
        SpaceCity s =new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250});
        p.buyLand(s);
        assertEquals(s.getOwner(), p);
        assertEquals(p.checkBalance(), 1500-60);
        assertEquals( marron.getSpaces().get(0),s); 
    } 

    @Test
    public void testWithdrawMoney() {
        Player p = new Player("Linda");
        p.withdrawMoney(600);
        assertEquals(p.checkBalance(), 1500-600);
    }

    @Test
    public void testEarnMoney() {
        Player p = new Player("Linda");
        p.earnMoney(600);
        assertEquals(p.checkBalance(), 1500+600);
    }

    @Test
    public void testBankrupt() {
        Player p = new Player("Linda");
        assertEquals(p.isBankrupt(),false);
        p.withdrawMoney(1600);
        assertEquals(p.isBankrupt(),true);
    }  
    
    @Test
    public void testPayRent(){
        Player p = new Player("Linda");
        Color marron = new Color("marron",50);
        SpaceCity s =new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250});
        p.buyLand(s);
        Player p2 = new Player("Yewon");
        p2.payRent(s);
        assertEquals(p.checkBalance(), (1500-s.getPrice()+s.getCurrentRentPrice()));
    }

    @Test
    public void testGoToJail(){
        Player p = new Player("Linda");
        p.goToJail();
        assertEquals(p.isInJail(), true);
        assertEquals(p.getPosition(), 10);
        

    }

    @Test 
    public void testGoOutJail(){
        Player p = new Player("Linda");
        p.goToJail();
        p.goOutJail(50  );
        assertEquals(p.isInJail(), false);
        assertEquals(p.checkBalance(),1500-50); 
    }

    @Test
    public void payTaxTest(){
        SpaceTax s = new SpaceTax("impot", 0, 100);
        Player p = new Player("Linda");
        p.payTax(s);
        assertEquals(p.checkBalance(), 1500-100);
    }

}