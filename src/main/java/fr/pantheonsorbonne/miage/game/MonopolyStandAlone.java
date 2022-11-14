package fr.pantheonsorbonne.miage.game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;


public class MonopolyStandAlone {
    public static void main(String[] args){
        List<Player> players = new ArrayList<>(); players.add(new Player("Linda")); players.add(new Player("Yewon"));players.add(new Player("Imane"));
        
       
        //List<Player> players = Arrays.asList( new Player("Linda"),new Player("Yewon"), new Player("Imane"));
        Game game = new Game(players);

        do{
            for(int i = 0; i<players.size();++i){
                //game.askBuild(player);
                
                if(players.get(i).isInJail()){
                    game.playerInJail(players.get(i));
                }
                else if(!players.get(i).bankrupt()){
                    game.nextTour(players.get(i));
                }else{
                    System.out.println("player "+ players.get(i).getName() + " is bankrupt");
                    players.remove(players.get(i));
                    ++i;
                }
            }
            
        }while (players.size()> 1);
        System.out.println("player "+ players.get(0).getName() + " is win µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ");
        
    }
}