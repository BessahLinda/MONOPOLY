package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Dice;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.Random;

/**
 * this is an example for the Guest in the tictactoe game
 */
public class MonopolyGuest {
    public static void main(String[] args) throws Exception {
        PlayerFacade facade = Facade.getFacade();
        facade.waitReady();
        //set our palyer name
        final String playerName="Yewon - " + new Random().nextInt();
        Player p1 = new Player(playerName);
        facade.createNewPlayer(playerName);
        System.out.println("I am: "+ playerName);
        //wait until we are able to join a new game
        Game currentGame = facade.autoJoinGame("monopoly-room-1");

        //get our mark
        
        // if (!command.name().equals("SEND_MONEY")) {
        //     throw new RuntimeException();
        // }
        // System.out.println(command.body()); // directory 
        // System.exit(0);

        gameloop:  // pour finir loop faut decalarer 
        for(;;){
            GameCommand command = facade.receiveGameCommand(currentGame); //blocking everything till it gets command
            switch (command.name()) { //cd
                case "MOVE_PAWN_TO": // index
                    p1.advance(Integer.parseInt(command.body()));
                    System.out.println(p1.getName()+"is arrived to" + p1.getPosition());
                    break;
                case "BUY_CELL":  // index
                    // player.buy_cell(params)
                    // player.makeDecision(action, params)
                    
                case "SELL_CELL": // index
                    break;
                case "BUY_HOUSE": // index
                    break;
                case "SELL_HOUSE": // index
                    break;
                case "SEND_MONEY_TO": // cashAmount, playerName
                    // facade.sendGameCommandToPlayer(currentGame, playerName, new GameCommand("SEND_MONEY", cashAmount));
                    break;
                case "SEND_MONEY": // cashAmount
                    p1.earnMoney(Integer.parseInt(command.body()));
                    System.out.println(p1.getName()+"earns" + p1.checkBalance());
                    break;

                case "GAME_OVER":
                    System.out.println("★★★★★★★★ player won the game ★★★★★★★");
                    break gameloop;
            }

        }    
        
    

       }

}





