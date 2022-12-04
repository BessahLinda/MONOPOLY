package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Dice;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.util.Random;
import java.util.Set;

import org.checkerframework.common.returnsreceiver.qual.This;

/**
 * this is an example for the Guest in the tictactoe game
 */
public class MonopolyGuest {
    static final String playerId = "Player-" + new Random().nextInt();
    static final PlayerFacade playerFacade = Facade.getFacade();
    static Player p;
    static Game monopoly;
    public static void main(String[] args) throws Exception {
        playerFacade.waitReady();
        playerFacade.createNewPlayer(playerId);
        p = new Player(playerId, new Strategy());
        monopoly = playerFacade.autoJoinGame("monopoly");

        gameloop:  // pour finir loop faut decalarer 
        for(;;){
            GameCommand command = playerFacade.receiveGameCommand(monopoly); //blocking everything till it gets command
            switch (command.name()) { //cd
                case "MOVE_PAWN_TO": // index
                    p.advance(Integer.parseInt(command.body())-p.getPosition());
                    System.out.println(p.getName()+" has arrived to " + p.getPosition());
                    break;
                case "BUY_CELL":
                    p.buyLand(Integer.parseInt(command.body()));
                case "Sell_Propery":
                    p.sellProperty();
                    break;
                case "BUY_HOUSE": 
                    p.buildHouse();
                    break;
                case "SEND_MONEY_TO": // envoyer de l'argent à t
                    p.withdrawMoney(Integer.parseInt(command.body()));
                    break;
                case "SEND_MONEY": // player de guest gagner de l'agent (-100, player)
                    p.earnMoney(Integer.parseInt(command.body()));
                    System.out.println(p.getName()+" earns" + p.checkBalance());
                    break;
                case "GAME_OVER":
                    System.out.println("$$$$$$$$$$$$$ player won the game $$$$$$$$$$$$$$");
                    break gameloop;
            }
        }    
}





