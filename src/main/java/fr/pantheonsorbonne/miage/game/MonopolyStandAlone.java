package fr.pantheonsorbonne.miage.game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;


public class MonopolyStandAlone {
    public static void main(String[] args){
        List<Player> p = new ArrayList<>(); p.add(new Player("Linda")); p.add(new Player("Yewon"));p.add(new Player("Imane"));
        
       
        //List<Player> players = Arrays.asList( new Player("Linda"),new Player("Yewon"), new Player("Imane"));
        Game game = new Game(p);

        do{
            for(int i = 0; i<p.size();++i){
                //game.askBuild(player);
                
                if(p.get(i).isInJail()){
                    game.playerInJail(p.get(i));
                }
                else if(!p.get(i).bankrupt()){
                    game.nextTour(p.get(i));
                }else{
                    System.out.println("player "+ p.get(i).getName() + " is bankrupt");
                    p.remove(p.get(i));
                    ++i;
                }
            }
            
        }while (p.size()> 1);
        System.out.println("player "+ p.get(0).getName() + " is win µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ");
        
    }
}