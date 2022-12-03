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
import fr.pantheonsorbonne.miage.game.monopoly.elements.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceChance;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceJail;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceTax;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceToBuy;
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
    private static final int PLAYER_COUNT = 4;
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
            players.add(new Player((String)names.next()));
        }
        setBoardPlayer(players);
        System.out.println("New round:");
        playRound();
        System.exit(0);
    }

    @Override
    public void nextTour(Player player) {
        player.advance(d.rollDices());        
        hostFacade.sendGameCommandToPlayer(monopoly, player.getName(),new GameCommand("BUY_CELL", Integer.toString(player.getPosition())));
        Space playerSpaceAfterMove = board.get(player.getPosition());
        System.out.println(player.getName() +" are now on " + playerSpaceAfterMove.getName().toUpperCase() );

        if (playerSpaceAfterMove instanceof SpaceJail) {
            onSpaceJail(playerSpaceAfterMove, player);

        } else if (playerSpaceAfterMove instanceof SpaceTax) {
            onSpaceTax(playerSpaceAfterMove, player);

        } else if (playerSpaceAfterMove instanceof SpaceChance) {
            onSpaceChance(playerSpaceAfterMove, player);

        } else if (playerSpaceAfterMove instanceof SpaceToBuy){
            onSpaceCity(playerSpaceAfterMove, player);
        }
        System.out.println("\n**********************\n");
    }

    @Override
    protected void onSpaceCity(Space playerAfterMove, Player player){
        SpaceToBuy space = (SpaceToBuy) playerAfterMove;
        if (!space.isSpaceOwned()) {
            hostFacade.sendGameCommandToPlayer(monopoly, player.getName(),new GameCommand("BUY_CELL", Integer.toString(player.getPosition())));
            player.buyLand(space);           
        } else if (space.getOwner()!=player){
            hostFacade.sendGameCommandToPlayer(monopoly, player.getName(),new GameCommand("BUY_CELL", Integer.toString(players.get().get)));
            player.payRent(space);
            System.out.println(player.getName() + " paid " + space.getCurrentRentPrice() + " for " + space.getOwner().getName());
        }
        System.out.println(player.getName() + " have now " + player.checkBalance() );
        player.toStringP();
    }

    @Override
    protected void onSpaceJail(Space playerAfterMove, Player player){
        SpaceJail space = (SpaceJail) playerAfterMove;
        if (space.getType() != 0) { 
            player.goToJail();
        }
    }

    @Override
    protected void onSpaceTax(Space playerAfterMove, Player player){
        SpaceTax space = (SpaceTax) playerAfterMove;
        if(player.isAffordable(space.getTax())){
            player.withdrawMoney(space.getTax());
            hostFacade.sendGameCommandToPlayer(monopoly, player.getName(),new GameCommand("SEND_MONEY", Integer.toString(-space.getTax())));    
        }else{
            player.sellProperty(space.getTax());
        }
       
        System.out.println(player.getName()+" paid " + space.getTax() +"$ You now have "+ player.checkBalance()); 
    }

    protected void onSpaceChance(Space playerAfterMove, Player player){
        SpaceChance space = (SpaceChance) playerAfterMove;
        System.out.println(player.getName() + " have a lucky card" );
        space.imFeelingLucky(player, this);        
        System.out.println(player.getName() + " have now " + player.checkBalance() );
    }

    protected void checkPlayerInJail(Player p){
        d.rollDices();
        if(d.isDouble()){
            p.goOutJail();
            nextTour(p);
        }else if(p.getPrisonDuration()==2){
            p.goOutJail(50);
            System.out.println(p.getName()+"went out from prison");
            nextTour(p);
        }else if(p.getPrisonDuration()<2){
            p.setPrisonDuration(); 
            System.out.println(p.getName()+" is still in prison");
        }
    }

}
