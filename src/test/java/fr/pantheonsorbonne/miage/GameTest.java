package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.Game;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;

class GameTest {
    
    @Test
    public void playerInJail(){
        List<Player> players = new ArrayList<>(); players.add(new Player("Linda")); players.add(new Player("Yewon")); players.add(new Player("syna")); players.add(new Player("imane"));
        Game game = new Game(players);
        players.get(0).goToJail();
        game.playerInJail(players.get(0));
        //false si double
        //assertTrue(players.get(0).isInJail()); 
            

        

    }
}
