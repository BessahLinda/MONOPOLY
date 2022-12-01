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
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.model.Game;
import fr.pantheonsorbonne.miage.model.GameCommand;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This is an example for the host in the tictactoe game
 */
public final class MonopolyHost {
    private static final int PLAYER_COUNT = 3;
    private final HostFacade hostFacade;
    private final Set<String> players;
    private final Game monopoly;

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
        for (String playerName : this.players) {
            players.add(new Player(playerName));
        }
        
        GameLogic game = new GameLogic(players);
    }

 
}
