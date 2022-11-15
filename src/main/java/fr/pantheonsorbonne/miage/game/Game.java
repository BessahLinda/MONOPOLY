package fr.pantheonsorbonne.miage.game;
import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Color;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Dice;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceChance;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceCity;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceJail;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpacePublicService;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceStation;
import fr.pantheonsorbonne.miage.game.monopoly.elements.SpaceTax;


public class Game {
    private static Dice d = new Dice();
    private static List<Space> board = new ArrayList<>();
    List<Player> players = new ArrayList<>();

    public Game(List<Player> players){
        Color marron = new Color("marron",50);
        Color bleuClair = new Color("bleuClair",50);
        Color rose = new Color("rose", 100);
        Color orange = new Color("orange",100);
        Color rouge = new Color("rouge",150);
        Color jaune = new Color("jaune",150);
        Color vert = new Color("vert",200);
        Color bleu = new Color("bleu",200);

        board.add(new Space("Depart",0));
        board.add(new SpaceCity("Boulevard de Bellvile",1,marron,60, new int[] {2,10,30,90,160,250}));
        board.add(new SpaceChance("Chance", 2));
        board.add(new SpaceCity("Rue Lecourbe",3,marron,60,new int[]{4,20,60,180,320,450}));
        board.add(new SpaceTax("Impôts sur les revenus",4,200));
        board.add(new SpaceStation("Gare Montparnasse", 5,200));
        board.add(new SpaceCity("Rue de Vaugirard",6,bleuClair,100,new int[] {6,30,90,270,400,550}));
        board.add(new SpaceChance("Chance", 7));
        board.add(new SpaceCity("Rue de Courcelles",8,bleuClair,100, new int[] {6,30,90,270,400,550}));
        board.add(new SpaceCity("Avenue de la République",9,bleuClair,120,new int[] {8,40,100,300,450,600}));
        board.add(new SpaceJail("Prison", 10, 0));
        board.add(new SpaceCity("Boulevard de la Villette",11,rose,140,new int[] {10,50,150,450,625,750}));
        board.add(new SpacePublicService("Compagine de distribution d'éléctricité", 12,150));
        board.add(new SpaceCity("Avenue de Neuilly",13,rose,140,new int[] {10,50,150,450,625,750}));
        board.add(new SpaceCity("Rue de Paradis",14,rose,160,new int[] {12,60,180,500,700,900}));
        board.add(new SpaceStation("Gare de Lyon", 15,200));
        board.add(new SpaceCity("Avenue de Mozart",16,orange,180, new int[] {14,70,200,550,750,950}));
        board.add(new SpaceChance("Chance", 17));
        board.add(new SpaceCity("Boulevard Saint-Michel",18,orange,180, new int[] {14,70,200,550,750,950}));
        board.add(new SpaceCity("Place Pigalle",19,orange,200,new int[] {16,80,220,600,800,1000}));
        board.add(new Space("Parc",20));
        board.add(new SpaceCity("Avenue Matignon",21,rouge,220,new int[] {18,90,250,700,875,1050}));
        board.add(new SpaceChance("Chance", 22));
        board.add(new SpaceCity("Boulevard Malesherbes",23,rouge,220,new int[] {18,90,250,700,875,1050}));
        board.add(new SpaceCity("Avenue Henri-Martin",24,rouge,240,new int[] {20,100,300,750,925,1100}));
        board.add(new SpaceStation("Gare du Nord", 25,200));
        board.add(new SpaceCity("Faubourg Saint-Honoré",26,jaune,260,new int[] {22,110,330,800,975,1150}));
        board.add(new SpaceCity("Place de la Bourse",27,jaune,260,new int[] {22,110,330,800,975,1150}));
        board.add(new SpacePublicService("Compagine de distribution des eaux", 28,150));
        board.add(new SpaceCity("Rue de la Fayette",29,jaune,280,new int[] {22,120,360,850,1025,1200}));
        board.add(new SpaceJail("Allez en Prison", 30, 1));
        board.add(new SpaceCity("Avenue de Breteuil",31,vert,300, new int[] {26,130,390,900,1100,1275}));
        board.add(new SpaceCity("Avenue Foch",32,vert,300,new int[] {26,130,390,900,1100,1275}));
        board.add(new SpaceChance("Chance", 33)); 
        board.add(new SpaceCity("Boulevard des Capucines",34,vert,320,new int[] {28,150,450,1000,1200,1400}));
        board.add(new SpaceStation("Gare Saint-Lazare", 35,200));
        board.add(new SpaceChance("Chance", 36));
        board.add(new SpaceCity("Avenue des Champs-Elysées",37,bleu,350, new int[] {35,175,500,1100,1300,1500}));
        board.add(new SpaceTax("Taxe de luxe",4,100));
        board.add(new SpaceCity("Rue de la Paix",39,bleu,400,new int[] {50,200,600,1400,1700,2000}));

        this.players =players;
        // when we buy a house 1)call change owner fonction from SpaceCity 2) add city to Player property
    }
    
    

	public void nextTour(Player player) {

        player.advance( d.rollDice());
        Space playerSpaceAfterMove = board.get(player.getPosition());
        System.out.println(player.getName() +" are now on " + playerSpaceAfterMove.getName().toUpperCase() );

        if (playerSpaceAfterMove instanceof SpaceJail) {
            onSpaceJail(playerSpaceAfterMove, player);

        } else if (playerSpaceAfterMove instanceof SpaceTax) {
            onSpaceTax(playerSpaceAfterMove, player);

        } else if (playerSpaceAfterMove instanceof SpaceChance) {
            onSpaceChance(playerSpaceAfterMove, player);

        } else if (playerSpaceAfterMove instanceof SpaceCity){
            onSpaceCity(playerSpaceAfterMove, player);
        }

        System.out.println("\n**********************\n");
    }

    public void askBuild(Player player) {
        player.buyHouse(board.get(player.getPosition()));
    }



    public void playerInJail(Player p){
        d.rollDice();
        if(d.isDouble()){
            p.goOutJail();
            nextTour(p);
        }else if(p.getPrisonDuration()==3){
            p.goOutJail(50);
            nextTour(p);
        }else if(p.getPrisonDuration()<3){
            p.setPrisonDuration();
        }
    }

    
    private void onSpaceTax(Space playerAfterMove, Player player){
        SpaceTax space = (SpaceTax) playerAfterMove;
        player.payTax(space);
        System.out.println(player.getName()+" paid " + space.getTax() +"$ You now have "+ player.checkBalance());     
    }

    private void onSpaceChance(Space playerAfterMove, Player player){
        SpaceChance space = (SpaceChance) playerAfterMove;
        space.imFeelingLucky(player, this);        
    }

    private void onSpaceCity(Space playerAfterMove, Player player){
        SpaceCity space = (SpaceCity) playerAfterMove;
        if (!space.isSpaceOwned()) {
            player.buyLand(space);
        } else if (space.getOwner()!=player){
            player.payRent(space);
        }
    }

    private void onSpaceJail(Space playerAfterMove, Player player){
        SpaceJail space = (SpaceJail) playerAfterMove;
        if (space.getType() != 0) { // it means this block is goToJail so block the player and take him to the jail
            player.goToJail();
        }
    }
    
}        

	
