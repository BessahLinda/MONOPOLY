package fr.pantheonsorbonne.miage.game;
import java.util.Arrays;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;


public class MonopolyStandAlone {
    public static void main(String[] args){
        List<Player> players = Arrays.asList( new Player("Linda"),new Player("Yewon"));
        Game game = new Game(players);

        do{
            for(Player player : players){
                if(!player.bankrupt()){
                    game.nextTour(player);
                }else{
                    System.out.println("players"+ player.getName() + "is bankrupt");
                    players.remove(player);
                }
            }
        }while (players.size()> 1);
    }
}