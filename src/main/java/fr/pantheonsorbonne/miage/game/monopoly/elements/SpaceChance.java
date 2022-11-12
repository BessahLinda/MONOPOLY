package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.List;
import java.util.Random;

import fr.pantheonsorbonne.miage.game.Game;

public class SpaceChance extends Space{

    private int prices[] = {-500,-400,-300,-200,-100,100,200,300,400,500};

    public SpaceChance(String name, int index) {
        super(name, index);
        //TODO Auto-generated constructor stub
    }

    public void imFeelingLucky(Player player, Game game) {
        int ret = (int)((Math.random() * 2));
        switch (ret) {
            case 0:
                luckyChangeCash(player);
                break;
            case 1:
                luckyTakePlayerToTheJail(player);
                break;
            case 2:
                luckyTakePlayerToAnotherLocation(player, game);
                break;
                default:
                    break;
        }
    }

    private void luckyTakePlayerToAnotherLocation(Player player, Game game) {
        game.nextTour(player);
    }

    private void luckyTakePlayerToTheJail(Player player) {
        System.out.println("Lucky card : You are in jail now.");
        player.setInJail(true);
        player.setPosition(SpaceJail.jailLocationIndex);
    }

    private void luckyChangeCash(Player player) {
        int rndIndex = new Random().nextInt(prices.length);
        int rndPrice = prices[rndIndex];
        if(rndPrice < 0) {
            player.addMoney(rndPrice);
            System.out.println("You take a lucky card and your cash has been decreased by "
                    + rndPrice + "$");
        } else {
            player.payChance(rndPrice);
            System.out.println("You take a lucky card and your cash has been increased by "
                    + rndPrice + "$");
        }
        
    }
    
}
