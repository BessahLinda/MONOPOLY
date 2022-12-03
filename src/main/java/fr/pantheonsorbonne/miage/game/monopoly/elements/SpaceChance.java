package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.game.GameLogic;

public class SpaceChance extends Space{
    private final int prices[] = {-100,-90,-80,-70,-60,-50,-40,-30,30,40,50,60,70,80,90,100};
    //private final int prices[] = {-500,-400,-300,-200,-100,100,200,300,400,500};

    public SpaceChance(String name, int index) {
        super(name, index);
        
    }

    public void imFeelingLucky(Player player, GameLogic game) {
        int ret = (int)((Math.random() * 1));
        switch (ret) {
            case 0:
                luckyChangeCash(player);
                break;
            /**case 1:
                luckyTakePlayerToTheJail(player);
                break;**/
            case 1:
                luckyTakePlayerToAnotherLocation(player, game);
                break;
                default:
                    break;
        }
    }

    private void luckyTakePlayerToAnotherLocation(Player player, GameLogic game) {
        System.out.println(player.getName() + " luckyTakePlayerToAnotherLocation ");
        game.nextTour(player);
    }

    private void luckyChangeCash(Player player) {
        int rndIndex = new Random().nextInt(prices.length);
        int rndPrice = prices[rndIndex];
        if(rndPrice < 0) {
            player.earnMoney(rndPrice);
            System.out.println("You take a lucky card and your cash has been decreased by "
                    + rndPrice + "$");
        } else {
            player.payChance(rndPrice);
            System.out.println("You take a lucky card and your cash has been increased by "
                    + rndPrice + "$");
        }
        
    }
    
}
