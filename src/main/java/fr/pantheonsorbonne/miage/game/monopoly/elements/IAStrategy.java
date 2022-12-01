package fr.pantheonsorbonne.miage.game.monopoly.elements;

public interface IAStrategy {
    
    public void buyHouseIfallCitiesOwnMaison(Player p);
    public void buyHouseOnEmptyLand(Player p);
    public void buyHouseWhile(Player p, SpaceCity city, int money, int nbHouse);

}
