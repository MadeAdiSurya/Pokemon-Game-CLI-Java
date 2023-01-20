import java.util.*;

import javax.swing.text.html.parser.Entity;
public class Player {
    private Monster currentMonster;
    private int playerID;
    private String playerName;
    private List<Monster> playerMonster;
    // private List<Integer> IDMonsterAlive;
    private int monsterAlive;

    public Player(int playerID, String playerName, List<Monster> playerMonster){
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerMonster = playerMonster;
        this.monsterAlive = 6;

    }
    
    //getter
    public Monster getCurrentMonster(){
        return this.currentMonster;
    }



    public int getPlayerID(){
        return this.playerID;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    //mengembalikan null jika tidak ada
    public Monster getMonsterWithID(int searchID){
        Monster thereMonster = null;
        List<Monster> tempListMonster = getPlayerMonster();
        for (Monster monster: tempListMonster){
            if (monster.getMonsterID() == searchID){
                thereMonster = monster;
            }
        }
        return thereMonster;
    }
    

    public void printPlayerMonster() {
        System.out.println("Monster milik "+ this.getPlayerName()+":");
        String fainted = " (FAINTED)";
        
        for (Monster monster : playerMonster){
            String visibleInfo = "";
            if (monster.getHP() != 0){
                visibleInfo = " (HP : " + monster.getHP() + " )"; 
            }
            else{
                visibleInfo = fainted;
            }

            System.out.println(monster.getMonsterID()+". "+ monster.getname() + visibleInfo);
        }
    }


    public List<Monster> getPlayerMonster(){
        return this.playerMonster;
    }

    public int getMonsterAlive() {
        return this.monsterAlive;
    }

    public void setMonsterAlive(int CountAlive){
        this.monsterAlive = CountAlive;
    }

    //setter
    public int CountMonsterAlive(){
        int tempCount = 6;
        for (Monster monster : playerMonster){
            if (monster.getHP() == 0){
                tempCount--;
                monster.setPlayable(false);
            }
        }
        return tempCount;
    }

    public void selectMonster(int monsterID){
        List<Monster> tempMonster = this.getPlayerMonster();
        for (Monster themonster : tempMonster){
            if (themonster.getMonsterID() == monsterID){
                    this.currentMonster = themonster;
            }
        }
    }

    public void printElTypeSelectedMonster(){
        List<ElementType> listElmtType = this.currentMonster.getelementTypes();
        for (int i= 0; i< listElmtType.size(); i++){
            String elTypeName = listElmtType.get(i).name();
            System.out.println(elTypeName);
        }
    }

    public void printSelectedMonster(){
        System.out.println(this.getPlayerName() + " menggunakan monster " + this.getCurrentMonster().getname() + " untuk bertarung");
        System.out.println("-----INFORMASI MONSTER-----");
        System.out.println("ID : " + this.currentMonster.getMonsterID());
        System.out.println("Nama : " + this.currentMonster.getname());
        System.out.println("Tipe Elemen : ");
        printElTypeSelectedMonster();
        this.currentMonster.printStats();
        System.out.println("*STATUS CONDITION*");
        System.out.println(this.currentMonster.getStatusCondition().getStatus().name());

    }

    public void printMovesSelectedMonster(){
        Monster themonster = this.currentMonster;
        List <Move> themonsterMoveList = themonster.getListMove();
        for (Move theMove: themonsterMoveList){
            if (theMove.getMoveID() != -1){
                System.out.println("----MOVE----");
                System.out.println("Move ID : " + theMove.getMoveID());
                System.out.println("Move name : " + theMove.getName());
                System.out.println("Target move : " + theMove.getTargetMove().name());
                if (theMove.getTypeMove() == TypeMove.DEFAULT){
                    System.out.println("Ammunition : " + theMove.getAmmunition() + " (infinity)");
                }
                else if(theMove.getTypeMove() != TypeMove.DEFAULT){
                    System.out.println("Ammunition : " + theMove.getAmmunition());
                }
                System.out.println("------------");
            }
        }
    }
}
