public class Move{
    private int idMove;
    private TypeMove typeMove;
    private String name;
    private ElementType elementType;
    private int accuracy;
    private int priority;
    private int ammunition;
    private TargetMove targetMove;
    private StatusType moveStatusType;
    private int basePower;
    private double effHP;
    private double effATk;
    private double effDEF;
    private double effSpATK;
    private double effSpDEF;
    private double effSpeed;

    //konstruktor default move
    public Move(Move move) {
        this.idMove = move.getMoveID();
        this.typeMove = move.getTypeMove();
        this.name = move.getName();
        this.elementType = move.getElementType();
        this.accuracy = move.getAccuracy();
        this.priority = move.getPriority();
        this.ammunition = move.getAmmunition();
        this.targetMove = move.getTargetMove();
        this.moveStatusType = move.getMoveStatusType();
        this.basePower = move.getBasePower();
        this.effHP = move.getEffHP();
        this.effATk = move.getEffATk();
        this.effDEF = move.getEffDEF();
        this.effSpATK = move.getEffSpATK();
        this.effSpDEF = move.getEffSpDEF();
        this.effSpeed = move.getEffSpeed();
    }

    public Move(String something){
        if (something.equals("skip")){
            this.idMove = -1;
            this.typeMove = TypeMove.STATUS;
            this.name = "SWITCH";
            this.elementType = ElementType.NORMAL;
            this.accuracy = 0;
            this.priority = -1;
            this.ammunition = -1;
            this.targetMove = TargetMove.OWN;
            this.moveStatusType = moveStatusType.NONE;
            this.basePower = 0;
            this.effHP = 0;
            this.effATk = 0;
            this.effDEF = 0;
            this.effSpATK = 0;
            this.effSpDEF = 0;
            this.effSpeed = 0;
        }
    }
    
    public Move (){
        this.idMove = 0;
        this.typeMove = TypeMove.DEFAULT;
        this.name = "Default";
        this.elementType = ElementType.NORMAL;
        this.accuracy = 100;
        this.priority = 0;
        this.ammunition = -1;
        this.targetMove = TargetMove.ENEMY;
        this.moveStatusType = StatusType.NONE;
        this.basePower = 50;
    }

    //konstruktor Normal, Special Move
    public Move(int idMove, TypeMove typeMove, String name, ElementType elementType, int accuracy, int priority, int ammunition, int basePower){
        this.idMove = idMove;
        this.typeMove = typeMove;
        this.name = name;
        this.elementType = elementType;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
        this.targetMove = TargetMove.ENEMY;
        this.moveStatusType = StatusType.NONE;
        this.basePower = basePower;
    }
    
    //konstruktor Status Move
    public Move(int idMove, String name, ElementType elementType, int accuracy, int priority, int ammunition, TargetMove targetMove, StatusType moveStatusType, double effHP, double effATk, double effDEF, double effSpATK, double effSpDEF, double effSpeed){
        this.idMove = idMove;
        this.typeMove = TypeMove.STATUS;
        this.name = name;
        this.elementType = elementType;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
        this.targetMove = targetMove;
        this.moveStatusType = moveStatusType;
        this.effHP = effHP;
        this.effATk = effATk;
        this.effDEF = effDEF;
        this.effSpATK = effSpATK;
        this.effSpDEF = effSpDEF;
        this.effSpeed = effSpeed;
    }


    public String moveName(){
        return this.name;
    }

    public int getMoveID() {
        return this.idMove;
    }

    public TypeMove getTypeMove() {
        return typeMove;
    }

    public String getName() {
        return name;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getPriority() {
        return priority;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void reduceAmmunition(){
        this.ammunition -=1;
    }

    public TargetMove getTargetMove() {
        return targetMove;
    }

    public StatusType getMoveStatusType() {
        return moveStatusType;
    }

    public int getBasePower() {
        return basePower;
    }

    public double getEffHP() {
        return effHP;
    }

    public double getEffATk() {
        return effATk;
    }

    public double getEffDEF() {
        return effDEF;
    }

    public double getEffSpATK() {
        return effSpATK;
    }

    public double getEffSpDEF() {
        return effSpDEF;
    }

    public double getEffSpeed() {
        return effSpeed;
    }

}
