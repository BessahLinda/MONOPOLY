package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Dice;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.Random;
import java.util.Set;

/**
 * this is an example for the Guest in the tictactoe game
 */
public class MonopolyGuest {
    static final String playerId = "Player-" + new Random().nextInt();
    static final PlayerFacade playerFacade = Facade.getFacade();
    static Game monopoly;
    static int maxPlayer = 3;
    public static void main(String[] args) throws Exception {
        playerFacade.waitReady();
        playerFacade.createNewPlayer(playerId);
        monopoly = playerFacade.autoJoinGame("monopoly");


        gameloop:  // pour finir loop faut decalarer 
        for(;;){
            GameCommand command = playerFacade.receiveGameCommand(monopoly); //blocking everything till it gets command
            switch (command.name()) { //cd
                case "MOVE_PAWN_TO": // index
                    p1.advance(Integer.parseInt(command.body()));
                    System.out.println(p1.getName()+" has arrived to " + p1.getPosition());
                    break;
                case "BUY_CELL":
                    p1.buyLand(Integer.parseInt(command.body()));
                case "SELL_CELL": 
                case "SELL_HOUSE":
                    p1.sellProperty();
                    break;
                case "BUY_HOUSE": 
                    p1.buildHouse();
                    break;
                case "SEND_MONEY_TO": // cashAmount, playerName
                    p1.earnMoney(Integer.parseInt(command.body()));
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
        
    

       protected Set<String> getInitialPlayers() {
           return this.getPlayers();
       }

}





