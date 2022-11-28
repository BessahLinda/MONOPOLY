// /*
//  * Licensed to the Apache Software Foundation (ASF) under one or more
//  * contributor license agreements.  See the NOTICE file distributed with
//  * this work for additional information regarding copyright ownership.
//  * The ASF licenses this file to You under the Apache License, Version 2.0
//  * (the "License"); you may not use this file except in compliance with
//  * the License.  You may obtain a copy of the License at
//  *
//  *      http://www.apache.org/licenses/LICENSE-2.0
//  *
//  * Unless required by applicable law or agreed to in writing, software
//  * distributed under the License is distributed on an "AS IS" BASIS,
//  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  * See the License for the specific language governing permissions and
//  * limitations under the License.
//  */
// package fr.pantheonsorbonne.miage.game;


// import fr.pantheonsorbonne.miage.Facade;
// import fr.pantheonsorbonne.miage.HostFacade;
// import fr.pantheonsorbonne.miage.PlayerFacade;

// import fr.pantheonsorbonne.miage.model.Game;
// import fr.pantheonsorbonne.miage.model.GameCommand;

// import java.util.Random;
// import java.util.Set;

// /**
//  * This is an example for the host in the tictactoe game
//  */
// public final class MonopolyHost {
//     private static final int PLAYER_COUNT = 4;
//     private final HostFacade hostFacade;
//     private final Set<String> players;
//     private final Game monopoly;

//     private MonopolyHost(HostFacade hostFacade, Set<String> players, fr.pantheonsorbonne.miage.model.Game monopoly) {
//         this.hostFacade = hostFacade;
//         this.players = players;
//         this.monopoly = monopoly;
//     }

//     public static void main(String[] args) throws Exception, FullBoardException {
       
//         HostFacade hostFacade = Facade.getFacade();
//         hostFacade.waitReady();

//         hostFacade.createNewPlayer("Nicolas" + new Random().nextInt());

//         //play the game until the program quits
//     //     while (true) {
//     //         Game game = hostFacade.createNewGame("monopoly");
//     //         hostFacade.waitForExtraPlayerCount(2);
//     //         playTheGame(playerFacade, game);
//     //     }
//     // }

//     // private static void playTheGame(PlayerFacade playerFacade, Game game) throws FullBoardException {
//     //     //create a new board
//     //     TicTacToe board = new TicTacToeImpl(6);
//     //     //I'll be X, the other player will be O
//     //     char myMark = 'X';
//     //     //send its mark to the other player
//     //     playerFacade.sendGameCommandToAll(game, new GameCommand("youare", "O"));

//     //     // loop until the game is other
//     //     while (true) {

//     //         //check if the game is over
//     //         if (handleGameOver(playerFacade, game, board, myMark))
//     //             break;

//     //         //if the game is not over, use my mark on the board
//     //         board.addRand(myMark);
//     //         System.out.println("-----------------------\n"+board);
//     //         //send the board to the other player
//     //         playerFacade.sendGameCommandToAll(game, new GameCommand("board", board.toFlatString()));

//     //         //get the other player's move and retreive the board
//     //         GameCommand command = playerFacade.receiveGameCommand(game);
//     //         board = new TicTacToeImpl(command.body());
//     //     }
//     // }

//     // private static boolean handleGameOver(PlayerFacade playerFacade, Game game, TicTacToe board, char myMark) {
//     //     //check if the game is over
//     //     if (board.getWinner() == myMark) {
//     //         //we've won :-)
//     //         playerFacade.sendGameCommandToAll(game, new GameCommand("gameover", "defeat"));
//     //         playerFacade.sendGameCommandToAll(game, new GameCommand("board", board.toFlatString()));
//     //         System.out.println("victory!\n" + board);
//     //         return true;
//     //     } else if (board.getWinner() == 'O') {
//     //         //we've lost :-(
//     //         playerFacade.sendGameCommandToAll(game, new GameCommand("gameover", "victory"));
//     //         playerFacade.sendGameCommandToAll(game, new GameCommand("board", board.toFlatString()));
//     //         System.out.println("defeat!\n" + board);
//     //         return true;
//     //     } else if (board.isFull()) {
//     //         //it's a tie :-/
//     //         playerFacade.sendGameCommandToAll(game, new GameCommand("gameover", "tie"));
//     //         playerFacade.sendGameCommandToAll(game, new GameCommand("board", board.toFlatString()));
//     //         System.out.println("tie!\n" + board);
//     //         return true;
//     //     }
//     //     //the game is not over
//     //     return false;
//     // }

// }
