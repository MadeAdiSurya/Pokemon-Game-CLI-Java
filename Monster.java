import java.util.ArrayList;
import java.util.List;

public class Monster extends Stats <Double> implements StatsInterface <Double>{
    private int monsterID;
    private String nama;
    private List<ElementType> elementTypes;
    private Status statusCondition;
    private List<Move> moves;
    private boolean playable;
   // private static int countCreatedMonster;

    //konstruktor
    public Monster(int monsterID, String nama, List<ElementType> elementTypes, List<Move> moves, double healthPoint, double attack, double defense, double specialAttack, double specialDefense, double speed){
        super(healthPoint,attack,defense, specialAttack, specialDefense,speed);
        this.monsterID = monsterID;
        this.nama = nama;
        this.elementTypes = elementTypes;
        this.statusCondition = new Status();
        this.moves = moves;
        this.playable =  true;
    }



    //getter
    public int getMonsterID() {
        return this.monsterID;
    }

    public String getname(){
        return this.nama;
    }

    public List<Move> getListMove(){
        return this.moves;
    }

    public List<ElementType> getelementTypes(){
        return elementTypes;
    }

    public void printElementTypes(){

        for (ElementType eltype : this.getelementTypes()){
            System.out.println(eltype.name());
        }
    }

    public Status getStatusCondition(){
        return this.statusCondition;
    }

    //mengembalikan null jika tidak ada
    public Move getMoveWithID(int MoveID){
        List<Move> tempListMoves = this.moves;
        Move searchMove = null;
        for (Move themove : tempListMoves){
            if (themove.getMoveID() == MoveID){
                searchMove = themove;
            }
        }
        return searchMove;
    }

    public boolean checkAvailableMove(Move move){
        return (move != null);
    }

    public void updateAmunitionMove(Monster selectedMonster, int moveID){
        if (selectedMonster.getMoveWithID(moveID).getAmmunition()>0){
            selectedMonster.getMoveWithID(moveID).reduceAmmunition();
        }
    }

    public boolean isPlayable(){
        return this.getHP() >0;
    }

    public boolean setPlayable(boolean playable){
        return this.playable = playable;
    }

    //setter
    public void setMonsterID(int monsterID) {
        this.monsterID = 0;
        this.monsterID = monsterID;
    }
    public void setname(String name){
        this.nama = name;
    }

    public void setelementTypes(List<ElementType> elementTypes){
        this.elementTypes = elementTypes;
    }

    public void getmoves(){
        for(Move move : this.moves) {
            System.out.println(move.moveName());
        }
    }
    
    public void modifHP(Double amount) {
        setHP(Math.floor(getHP() + amount));
    }

    public void modifATK(Double amount) {
        setATK(Math.floor(getATK() + amount));
    }

    public void modifDEF(Double amount) {
        setDEF(Math.floor(getDEF() + amount));
    }

    public void modifSpATK(Double amount) {
        setSpATK(Math.floor(getSpATK() + amount));
    }

    public void modifSpDEF(Double amount) {
        setSpDEF(Math.floor(getSpDEF() + amount));
    }

    public void modifSpeed(Double amount) {
        setSpeed(Math.floor(getSpeed()+ amount));
    }

    public void printMoves(){
        for (Move themove : this.moves){
            System.out.println(" ");
            System.out.println("Nama move: " + themove.getMoveStatusType().name());
            System.out.println("Nama move: " + themove.getName());
            System.out.println("Nama move: " + themove.getAmmunition());
        }
    }
    
    @Override
    public void printStats(){
        System.out.println("Stats dari " + this.getname());
        System.out.println("Health Point " + this.getHP());
        System.out.println("Attack " + this.getATK());
        System.out.println("Defense " + this.getDEF());
        System.out.println("Special Attack " + this.getSpATK());
        System.out.println("Special Defense " + this.getSpDEF());
        System.out.println("Speed " + this.getSpeed());
    }
    

}