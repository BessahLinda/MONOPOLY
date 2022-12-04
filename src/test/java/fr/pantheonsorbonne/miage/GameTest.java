package fr.pantheonsorbonne.miage;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.GameLogic;
import fr.pantheonsorbonne.miage.game.MonopolyStandAlone;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.GoodStrategy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.Strategy;

class GameTest {
    
    @Test
    public void playerInJail(){
        List<Player> players = new ArrayList<>(); players.add(new Player("Linda", new GoodStrategy())); players.add(new Player("Yewon", new GoodStrategy())); players.add(new Player("syna", new GoodStrategy())); players.add(new Player("imane", new GoodStrategy()));
        MonopolyStandAlone game = new MonopolyStandAlone(players);
        game.setBoardPlayer(players);
        players.get(0).goToJail();
        game.checkPlayerInJail(players.get(0));
        //false si double
        //assertTrue(players.get(0).isInJail());    

    }

    @Test
    public void isOnSpaceTax(){
        List<Player> players = new ArrayList<>(); players.add(new Player("Linda", new GoodStrategy())); players.add(new Player("Yewon", new GoodStrategy())); players.add(new Player("syna", new GoodStrategy())); players.add(new Player("imane", new GoodStrategy()));
        MonopolyStandAlone game = new MonopolyStandAlone(players);
        game.setBoardPlayer(players);
        game.isOnSpaceTax(game.getBoard().get(4),players.get(0) );
        assertEquals(1500-200, players.get(0).checkBalance());
        

    }


}
