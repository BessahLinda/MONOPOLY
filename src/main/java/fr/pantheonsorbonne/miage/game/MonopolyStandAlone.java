package fr.pantheonsorbonne.miage.game;
import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceChance;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceJail;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceTax;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceToBuy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.BadStrategy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.Strategy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.Strategy;



public class MonopolyStandAlone extends GameLogic {
    public MonopolyStandAlone(List<Player> players) {
        super(players);
    }

    public static void main(String[] args){
        List<Player> players = new ArrayList<>(); players.add(new Player("Linda", new Strategy())); players.add(new Player("Yewon",new Strategy())); players.add(new Player("Imane",new Strategy())); players.add(new Player("Syna",new Strategy()));
        MonopolyStandAlone game = new MonopolyStandAlone(players);
        game.setBoardPlayer(players);
        int cpt =0;
        do{
            for(int i = 0; i<players.size();++i){ 

                players.get(i).buyHouse();
                players.get(i).forceToSellSpace();
                
                if(players.get(i).isInJail()){
                    game.checkPlayerInJail(players.get(i));
                }
                else{
                    game.makeMove(players.get(i)); 
                }
                if(players.get(i).isBankrupt()){
                    System.out.println(players.get(i).getName()+" doesn't have enough money to pay. You are retired from the game");     
                    players.remove(players.get(i));
                    break;
                }
            }
            ++cpt;
        }while (players.size()> 1);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$ player "+ players.get(0).getName() + " won the game $$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+ cpt);
    }

    @Override
    public void makeMove(Player player) {
        player.advance(d.rollDices());
        Space destination = board.get(player.getPosition());
        System.out.println(player.getName() +" has arrived at " + destination.getName().toUpperCase() );

        if (destination instanceof SpaceJail) {
            isOnSpaceJail(destination, player);

        } else if (destination instanceof SpaceTax) {
            isOnSpaceTax(destination, player);

        } else if (destination instanceof SpaceChance) {
            isOnSpaceChance(destination, player);

        } else if (destination instanceof SpaceToBuy){
            isOnSpaceCity(destination, player);
        }
        System.out.println("\n**********************\n");        
    }

    @Override
    protected void isOnSpaceCity(Space destination, Player player) {
        SpaceToBuy space = (SpaceToBuy) destination;
        
        if (!space.isSpaceOwned()) {
            player.buyLand(space);           
        } else if (space.getOwner()!=player){
            player.payRent(space);
            System.out.println(player.getName() + " paid " + space.getCurrentRentPrice() + " for " + space.getOwner().getName());
        }
        System.out.println(player.getName() + " has now " + player.checkBalance() );
        player.toStringP();
        
    }

    @Override
    protected void isOnSpaceJail(Space destination, Player player) {
        SpaceJail space = (SpaceJail) destination;
        if (space.getType() != 0) { 
            player.goToJail();
        }
    }

    @Override
    public void isOnSpaceTax(Space destination, Player player) {
        SpaceTax space = (SpaceTax) destination;
        player.payTax(space);
        
        System.out.println(player.getName()+" paid " + space.getTax() +"$ You have "+ player.checkBalance()); 
                    
    }

    @Override
    protected void isOnSpaceChance(Space destination, Player player) {
        SpaceChance space = (SpaceChance) destination;
        System.out.println(player.getName() + " has a lucky card" );
        space.imFeelingLucky(player, this);        
        System.out.println(player.getName() + " has now " + player.checkBalance() );        
    }

    @Override
    public void checkPlayerInJail(Player p) {
        d.rollDices();
        if(d.isDouble()){
            p.goOutJail();
            makeMove(p);
        }else if(p.getPrisonDuration()==2){
            p.goOutJail(50);
            System.out.println(p.getName()+" went out from prison");
            makeMove(p);
        }else if(p.getPrisonDuration()<2){
            p.setPrisonDuration(); 
            System.out.println(p.getName()+" is still in prison");
        }
    }
}