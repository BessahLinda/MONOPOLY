package fr.pantheonsorbonne.miage.game.monopoly.elements;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.Space;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceChance;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceCity;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceJail;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpacePublicService;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceStation;
import fr.pantheonsorbonne.miage.game.monopoly.elements.Spaces.SpaceTax;

public class Board {
    private List<Space> board = new ArrayList<>();
    public Board(){
        Color marron = new Color("marron",50);
        Color bleuClair = new Color("bleuClair",50);
        Color rose = new Color("rose", 100);
        Color orange = new Color("orange",100);
        Color rouge = new Color("rouge",150);
        Color jaune = new Color("jaune",150);
        Color vert = new Color("vert",200);
        Color bleu = new Color("bleu",200);

        //Create a standard monopoly board
        board.add(new Space("Depart",0));
        board.add(new SpaceCity("Boulevard de Bellvile",1,60,marron, new int[] {2,10,30,90,160,250}));
        String chance = "Chance";
        board.add(new SpaceChance(chance, 2));
        board.add(new SpaceCity("Rue Lecourbe",3,60,marron,new int[]{4,20,60,180,320,450}));
        board.add(new SpaceTax("Impôts sur les revenus",4,200));
        board.add(new SpaceStation("Gare Montparnasse", 5,200));
        board.add(new SpaceCity("Rue de Vaugirard",6,100,bleuClair,new int[] {6,30,90,270,400,550}));
        board.add(new SpaceChance(chance, 7));
        board.add(new SpaceCity("Rue de Courcelles",8,100,bleuClair, new int[] {6,30,90,270,400,550}));
        board.add(new SpaceCity("Avenue de la République",9,120,bleuClair,new int[] {8,40,100,300,450,600}));
        board.add(new SpaceJail("Prison", 10, 0));
        board.add(new SpaceCity("Boulevard de la Villette",11,140,rose,new int[] {10,50,150,450,625,750}));
        board.add(new SpacePublicService("Compagine de distribution d'éléctricité", 12,150));
        board.add(new SpaceCity("Avenue de Neuilly",13,140,rose,new int[] {10,50,150,450,625,750}));
        board.add(new SpaceCity("Rue de Paradis",14,160,rose,new int[] {12,60,180,500,700,900}));
        board.add(new SpaceStation("Gare de Lyon", 15,200));
        board.add(new SpaceCity("Avenue de Mozart",16,180,orange, new int[] {14,70,200,550,750,950}));
        board.add(new SpaceChance(chance, 17));
        board.add(new SpaceCity("Boulevard Saint-Michel",18,180,orange, new int[] {14,70,200,550,750,950}));
        board.add(new SpaceCity("Place Pigalle",19,200,orange,new int[] {16,80,220,600,800,1000}));
        board.add(new Space("Parc",20));
        board.add(new SpaceCity("Avenue Matignon",21,220,rouge,new int[] {18,90,250,700,875,1050}));
        board.add(new SpaceChance(chance, 22));
        board.add(new SpaceCity("Boulevard Malesherbes",23,220,rouge,new int[] {18,90,250,700,875,1050}));
        board.add(new SpaceCity("Avenue Henri-Martin",24,240,rouge,new int[] {20,100,300,750,925,1100}));
        board.add(new SpaceStation("Gare du Nord", 25,200));
        board.add(new SpaceCity("Faubourg Saint-Honoré",26,260,jaune,new int[] {22,110,330,800,975,1150}));
        board.add(new SpaceCity("Place de la Bourse",27,260,jaune,new int[] {22,110,330,800,975,1150}));
        board.add(new SpacePublicService("Compagine de distribution des eaux", 28,150));
        board.add(new SpaceCity("Rue de la Fayette",29,280,jaune,new int[] {22,120,360,850,1025,1200}));
        board.add(new SpaceJail("Allez en Prison", 30, 1));
        board.add(new SpaceCity("Avenue de Breteuil",31,300,vert, new int[] {26,130,390,900,1100,1275}));
        board.add(new SpaceCity("Avenue Foch",32,300,vert,new int[] {26,130,390,900,1100,1275}));
        board.add(new SpaceChance(chance, 33)); 
        board.add(new SpaceCity("Boulevard des Capucines",34,320,vert,new int[] {28,150,450,1000,1200,1400}));
        board.add(new SpaceStation("Gare Saint-Lazare", 35,200));
        board.add(new SpaceChance(chance, 36));
        board.add(new SpaceCity("Avenue des Champs-Elysées",37,350,bleu, new int[] {35,175,500,1100,1300,1500}));
        board.add(new SpaceTax("Taxe de luxe",4,100));
        board.add(new SpaceCity("Rue de la Paix",39,400,bleu,new int[] {50,200,600,1400,1700,2000}));
  
    }

    public Space getSpaceByIndex(int index){
        return board.get(index);
    }

    public List<Space> getBoard(){
        return board;
    }
    
}
