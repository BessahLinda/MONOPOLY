package fr.pantheonsorbonne.miage;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.Game;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.StrategyLinda;

class GameTest {
    
    @Test
    public void playerInJail(){
        List<Player> players = new ArrayList<>(); players.add(new Player("Linda", new StrategyLinda())); players.add(new Player("Yewon", new StrategyLinda())); players.add(new Player("syna", new StrategyLinda())); players.add(new Player("imane", new StrategyLinda()));
        Game game = new Game(players);
        players.get(0).goToJail();
        game.playerInJail(players.get(0));
        //false si double
        //assertTrue(players.get(0).isInJail());    

    }

    @Test
    public void isOnSpaceTax(){
        List<Player> players = new ArrayList<>(); players.add(new Player("Linda", new StrategyLinda())); players.add(new Player("Yewon", new StrategyLinda())); players.add(new Player("syna", new StrategyLinda())); players.add(new Player("imane", new StrategyLinda()));
        Game game = new Game(players);
        game.isOnSpaceTax(game.getBoard().get(4),players.get(0) );
        assertEquals(1500-200, players.get(0).checkBalance());
        

    }


}
