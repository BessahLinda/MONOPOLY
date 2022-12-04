package fr.pantheonsorbonne.miage.game;
import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Board;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Dice;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.PlayerNetwork;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Space;


public abstract class GameLogic {
    protected static Dice d = new Dice();
    protected List<Space> board = new ArrayList<>();
    List<Player> players = new ArrayList<>();

    protected GameLogic(List<Player> players) {
      this.players = players;
    }


    protected GameLogic() {
    }

    public void setBoardPlayer(List<Player> players){
        Board b = new Board();
        this.board = b.getBoard();
    }

    public void setBoardPlayerNetwork(){
        Board b = new Board();
        this.board = b.getBoard();
    }
    
    
    public abstract void makeMove(Player player);

    protected abstract  void isOnSpaceCity(Space destination, Player player);

    protected abstract void isOnSpaceJail(Space destination, Player player);

    protected abstract void isOnSpaceTax(Space destination, Player player);

    protected abstract void isOnSpaceChance(Space destination, Player player);

    protected abstract void checkPlayerInJail(Player p);

    public List<Space> getBoard(){
        return board;
    }

    
    

    
}        
