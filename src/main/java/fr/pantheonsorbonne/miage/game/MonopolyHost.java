/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.pantheonsorbonne.miage.game;


import fr.pantheonsorbonne.miage.Facade;
import fr.pantheonsorbonne.miage.HostFacade;
import fr.pantheonsorbonne.miage.PlayerFacade;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Dice;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceChance;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceJail;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceTax;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceToBuy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.Strategy;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy.Strategy;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This is an example for the host in the tictactoe game
 */
public final class MonopolyHost extends GameLogic{
    private static final int PLAYER_COUNT = 2;
    private final HostFacade hostFacade;
    private final Set<String> players;
    private final Game monopoly;
    private static Dice d = new Dice();

    private MonopolyHost(HostFacade hostFacade,Set<String> set, fr.pantheonsorbonne.miage.model.Game monopoly) {
        this.hostFacade = hostFacade;
        this.players = set;
        this.monopoly = monopoly;
    }


    public static void main(String[] args) throws Exception{
       
        HostFacade hostFacade = Facade.getFacade();
        hostFacade.waitReady();

        hostFacade.createNewPlayer("Host Player");

        Game monopoly = hostFacade.createNewGame("monopoly");
        hostFacade.waitForExtraPlayerCount(PLAYER_COUNT);
    
        MonopolyHost host = new MonopolyHost(hostFacade, monopoly.getPlayers(), monopoly);
        host.play();
    }

    public void play() {
        List<Player> players = new ArrayList<>(); 
        Iterator names = this.players.iterator();
        while(names.hasNext()){
            players.add(new Player((String)names.next(), new Strategy()));
        }
        setBoardPlayer(players);
        System.out.println("New round:");
        
        playRound();
        System.exit(0);
    }

    @Override
    public void makeMove(Player player) {
        player.advance(d.rollDices());
        hostFacade.sendGameCommandToPlayer(monopoly, player.getName(),new GameCommand("BUY_CELL", Integer.toString(player.getPosition())));
        Space destination = board.get(player.getPosition());
        String notif = player.getName() +" has arrived at " + destination.getName().toUpperCase();
        hostFacade.sendGameCommandToAll(monopoly, new GameCommand("NOTICE",notif));


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
    protected void isOnSpaceTax(Space destination, Player player) {
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
    protected void checkPlayerInJail(Player p) {
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
            System.out.println(p.getName()+" is still in the prison");
        }
    }

}
