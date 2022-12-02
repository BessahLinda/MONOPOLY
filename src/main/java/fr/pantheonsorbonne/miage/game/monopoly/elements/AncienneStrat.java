package fr.pantheonsorbonne.miage.game.monopoly.elements;

public class AncienneStrat implements AIStrategy{

    public void buyHouseWhile(Player p, SpaceCity city, int money, int nbHouse){

        while( p.checkBalance() > money && city.getNbHouse()< nbHouse ){  
            city.buildHouse(1);
            p.withdrawMoney(city.getColor().getHousePrice());
        }
    }


    public void buyHouseOnEmptyLand(Player p){

        for(SpaceCity city: p.getColorsetProperty()){
            if(p.checkBalance()<250){
                break;
            }
            if(city.getColor().getColorName().equals("marron")||city.getColor().getColorName().equals("bleuClair")){ //priority x
                buyHouseWhile(p,city,1500,2);
            }
            else if(city.getColor().getColorName().equals("orange")||city.getColor().getColorName().equals("rouge")||city.getColor().getColorName().equals("jaune")||city.getColor().getColorName().equals("rose")){  //priority 1
                buyHouseWhile(p,city,400,2);
            }    
            else if(city.getColor().getColorName().equals("bleu")){ //priority 2
                while(p.checkBalance()>400&&city.getName().equals("Rue de la Paix")&&city.getNbHouse()<2){
                    city.buildHouse(1);
                    p.withdrawMoney(city.getColor().getHousePrice());      
                }
                while(p.checkBalance()>400&&city.getName().equals("Avenue des Champs-Elysées")&&city.getNbHouse()<1){
                    city.buildHouse(1);
                    p.withdrawMoney(city.getColor().getHousePrice());      
                }
            }
            else if(city.getColor().getColorName().equals("vert")){ //priority 3
                buyHouseWhile(p,city,400,2);}
        }
    }

    public void buyHouseIfallCitiesOwnMaison(Player p){

        for(SpaceCity city: p.getColorsetProperty()){
            if(p.checkBalance()<2000){
                break;
            }
            if(city.getColor().getColorName().equals("marron")||city.getColor().getColorName().equals("bleuClair")){ //priority x
                buyHouseWhile(p,city,800,4);
            }
            //since colorsetProperty is already sorted, the most important city comes first
            else if(city.getColor().getColorName().equals("orange")||city.getColor().getColorName().equals("rouge")||city.getColor().getColorName().equals("jaune")||city.getColor().getColorName().equals("rose")){  //priority 1
                buyHouseWhile(p,city,3000,4);
            }    
            else if(city.getColor().getColorName().equals("bleu")){ //priority 2

                while(p.checkBalance()>3000&&city.getName().equals("Rue de la Paix")){
                    if(city.getNbHouse()<4){
                        city.buildHouse(1);
                        p.withdrawMoney(city.getColor().getHousePrice());
                    }
                }

                while(p.checkBalance()>3000&&city.getName().equals("Avenue des Champs-Elysées")){
                    if(city.getNbHouse()<4){
                        city.buildHouse(1);
                        p.withdrawMoney(city.getColor().getHousePrice());
                    }
                }
            }
            else if(city.getColor().getColorName().equals("vert")){ //priority 3
                buyHouseWhile(p,city,3000,4);
            }
       }
    }


   
    public void buyHouse(Player p) {
        //buy less then 3 houses in every city
        if(!p.allCitiesOwnMaison()){  //marron , bleu , orange 
            buyHouseOnEmptyLand(p);
        }else{
            buyHouseIfallCitiesOwnMaison(p);
        }
    }


    @Override
    public void sellProperty(Player p) {
        // TODO Auto-generated method stub
        
    }

}
