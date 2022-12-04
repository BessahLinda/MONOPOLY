package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Board;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Dice;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceToBuy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
        Board board = new Board();


        gameloop:
            for(;;){
                GameCommand command = playerFacade.receiveGameCommand(monopoly);
                switch (command.name()) {
                    case "MOVE_PAWN_TO":
                        p.advance(Integer.parseInt(command.body())-p.getPosition());
                        // System.out.println(p.getName()+" has arrived to " + p.getPosition());
                        break;
                    case "BUY_CELL":
                        p.buyLand((SpaceToBuy)board.getSpaceByIndex(Integer.parseInt(command.body())));
                        break;
                    case "SELL_PROPERTY":
                        p.sellProperty(Integer.parseInt(command.body()));
                        break;
                    case "BUY_HOUSE": 
                        p.buyHouse();
                        break;
                    case "SEND_MONEY_TO": // withdraw money for another player 
                        p.withdrawMoney(Integer.parseInt(command.body())); ///??
                        playerFacade.sendGameCommandToPlayer(monopoly, command.body(),new GameCommand("SEND_MONEY", command.body()));
                        break;
                    case "SEND_MONEY": // earn money
                        p.earnMoney(Integer.parseInt(command.body()));
                        System.out.println(p.getName()+" earns" + p.checkBalance());
                        break;
                    case "NOTICE":
                        System.out.println(command.body());
                        break;
                    case "GAME_OVER":
                        System.out.println("$$$$$$$$$$$$$ player won the game $$$$$$$$$$$$$$");
                        break gameloop;
                }
            } 
    }       
}





