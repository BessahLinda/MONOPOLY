package fr.pantheonsorbonne.miage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.pantheonsorbonne.miage.game.GameLogic;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;

class GameTest {
    
    @Test
    public void playerInJail(){
        List<Player> players = new ArrayList<>(); players.add(new Player("Linda")); players.add(new Player("Yewon")); players.add(new Player("syna")); players.add(new Player("imane"));
        GameLogic game = new GameLogic(players);
        players.get(0).goToJail();
        game.checkPlayerInJail(players.get(0));
        assertTrue(players.get(0).isInJail());
        

    }
}
