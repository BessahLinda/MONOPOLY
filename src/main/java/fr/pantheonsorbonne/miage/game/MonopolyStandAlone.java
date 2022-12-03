package fr.pantheonsorbonne.miage.game;
import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.elements.BadStrategy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.StrategyLinda;


public class MonopolyStandAlone {
    public static void main(String[] args){
        List<Player> players = new ArrayList<>(); players.add(new Player("Linda", new StrategyLinda())); players.add(new Player("Yewon",new StrategyLinda()));players.add(new Player("imane",new BadStrategy()));
        Game game = new Game(players);

        do{
            for(int i = 0; i<players.size();++i){ 

                players.get(i).buyHouse();
                
                if(players.get(i).isInJail()){
                    game.playerInJail(players.get(i));
                }
                else{
                    game.nextTour(players.get(i)); 
                }
                if(players.get(i).isBankrupt()){
                    System.out.println(players.get(i).getName()+" doesn't have enough money to pay. You are retired from the game");     
                    players.remove(players.get(i));
                    ++i;
                }
            }
            
        }while (players.size()> 1);
        System.out.println("$$$$$$$$$$ player "+ players.get(0).getName() + " won the game $$$$$$$$$$$");
        
    }
}