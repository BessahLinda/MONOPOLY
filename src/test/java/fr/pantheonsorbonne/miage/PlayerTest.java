package fr.pantheonsorbonne.miage;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceCity;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceJail;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    public void testSetPosition() {
        Player p = new Player("Linda");
        p.setPosition(7);
        assertEquals(p.getPosition(), 7);
        p.setPosition(38);
        assertEquals(p.getPosition(),5);
        assertEquals(p.getMoney(), 1700);
        Player p2 = new Player("Yewon");
        p2.setPosition(44);
        assertEquals(p2.getPosition(),4);
        assertEquals(p2.getMoney(), 1700);
    }

    @Test
    public void testCheckBalance() {
        Player p = new Player("Linda");
        assertEquals(p.checkBalance(200),true);
        assertEquals(p.checkBalance(2003),false);
    }    
    
    @Test
    public void testBuyLand() {
        Player p = new Player("Linda");
        Color marron = new Color("marron",50);
        SpaceCity s =new SpaceCity("Boulevard de Bellvile",1,marron,60, new int[] {2,10,30,90,160,250});
        p.buyLand(s);
        assertEquals(s.getOwner(), p);
        assertEquals(p.getMoney(), 1500-60);
        assertEquals( marron.getSpaces().get(0),s); 

    } 

    @Test
    public void testWithdrawMoney() {
        Player p = new Player("Linda");
        p.withdrawMoney(600);
        assertEquals(p.getMoney(), 1500-600);
    }

    @Test
    public void testAddMoney() {
        Player p = new Player("Linda");
        p.addMoney(600);
        assertEquals(p.getMoney(), 1500+600);
    }

    @Test
    public void testBankrupt() {
        Player p = new Player("Linda");
        assertEquals(p.bankrupt(),false);
        p.withdrawMoney(1600);
        assertEquals(p.bankrupt(),true);
    }  
    
    @Test
    public void testPayRent(){
        Player p = new Player("Linda");
        Color marron = new Color("marron",50);
        SpaceCity s =new SpaceCity("Boulevard de Bellvile",1,marron,60, new int[] {2,10,30,90,160,250});
        p.buyLand(s);
        Player p2 = new Player("Yewon");
        p2.payRent(s);
        assertEquals(p.getMoney(), (1500-s.getPrice()+s.getCurrentRentPrice()));
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
        assertEquals(p.getMoney(),1500-50); 
    }

}