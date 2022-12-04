package fr.pantheonsorbonne.miage.game.monopoly.elements;

public interface AIStrategy {
    
    public void buyHouse(Player p);
    public void sellProperty(Player p,int payment);
    public boolean goOutJail(Player p);
    
}
