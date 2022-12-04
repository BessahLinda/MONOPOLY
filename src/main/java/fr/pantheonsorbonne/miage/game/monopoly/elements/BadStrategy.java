package fr.pantheonsorbonne.miage.game.monopoly.elements;


public class BadStrategy implements AIStrategy {

    @Override
    public void buyHouse(Player p) {
        int a = (int) ((Math.random() * p.getColorsetProperty().size()) + 1);
        while(p.checkBalance()>400 && p.getColorsetProperty().get(a).getNbHouse()<5){
            p.getColorsetProperty().get(a).buildHouse(1);
        }
    }

    @Override
    public void sellProperty(Player p, int payment) {
        for(SpaceToBuy s : p.getProperty()){
            while(p.checkBalance()> payment){
                if(s instanceof SpaceCity){
                    SpaceCity space = (SpaceCity)s;
                    while(space.getNbHouse()>0){
                        space.deconstructHouse();
                        p.earnMoney((int)(space.getColor().getHousePrice()*0.75));
                    }
                }else{
                    p.earnMoney(s.getCurrentResellPrice());
                    s.setOwner(null);
                    p.getProperty().remove(s);
                    System.out.println(p.getName()+" sold " + s.getName());
                   
                }
                
            }
        }
        
    }
    
}
