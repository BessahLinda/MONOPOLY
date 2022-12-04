package fr.pantheonsorbonne.miage.game.monopoly.elements.Strategy;

import fr.pantheonsorbonne.miage.game.monopoly.elements.Player;

public interface AIStrategy {
    
    public void buyHouse(Player p);
    public void sellProperty(Player p,int payment);
    
}
