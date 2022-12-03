package fr.pantheonsorbonne.miage.game.monopoly.elements;


public class BadStrategy extends Strategy {

    @Override
    public void buyHouse(Player p) {
        int a = (int) ((Math.random() * p.getColorsetProperty().size()) + 1);
        while(p.checkBalance()>400 && p.getColorsetProperty().get(a).getNbHouse()<5){
            p.getColorsetProperty().get(a).buildHouse(1);
        }
    }

    @Override
    public void sellProperty(Player p, int payment) {
        int b = (int) ((Math.random() * p.getProperty().size()) + 1);
        while(p.checkBalance()> payment){
            p.getColorsetProperty().get(b).getCurrentResellPrice();
        }
    }
    
}
