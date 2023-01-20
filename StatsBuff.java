public class StatsBuff {
    //default value is 0
    private int buffAttack = 0;
    private int buffDefense = 0;
    private int buffSpAttack = 0;
    private int buffSpDefense = 0;
    private int buffSpeed= 0;

    //menyimpan nilai Stats dari Buff
    private double differenceBuffAttack;
    private double differenceBuffDefense;
    private double differenceBuffSpAttack;
    private double differenceBuffSpDefense;
    private double differenceBuffSpeed;

    private double originAttack;
    private double originDefense;
    private double originSpAttack;
    private double originSpDefense;
    private double originSpeed;

    //konstruktor
    public StatsBuff(Monster monster){
        // super(healthPoint,attack,defense, specialAttack, specialDefense,speed);
        buffAttack = (int)Math.floor(Math.random()*(4)-1);
        buffDefense = (int)Math.floor(Math.random()*(4)-1);
        buffSpAttack= (int)Math.floor(Math.random()*(4)-1);
        buffSpDefense = (int)Math.floor(Math.random()*(4)-1);
        buffSpeed = (int)Math.floor(Math.random()*(4)-1);

        this.originAttack = monster.getATK();
        this.originDefense = monster.getDEF();
        this.originSpAttack = monster.getSpATK();
        this.originSpDefense = monster.getSpDEF();
        this.originSpeed = monster.getSpeed();
    }

    

    //metode untuk mengembalikan buffFactor
    public double buffFactor(int buffName){
        double tempBuffFactor = 0;
        switch (buffName){
            case -4 :
                tempBuffFactor = 2/6;
                break;
            case -3 :
                tempBuffFactor = 2/5;
                break;
            case -2 :
                tempBuffFactor = 2/4;
                break;
            case -1 :
                tempBuffFactor = 2/3;
                break;
            case 0 :
                tempBuffFactor = 1;
                break;
            case 1 :
                tempBuffFactor = 3/2;
                break;
            case 2 :
                tempBuffFactor = 4/2;
                break;
            case 3 :
                tempBuffFactor = 5/2;
                break;
            case 4 :
                tempBuffFactor = 6/2;
                break;
            default:
                break;
        }
        return tempBuffFactor;
    }

    //metode untuk menghitung selisih Stats Buff dengan base Stats dengan rumus  :(baseStat * buffFactor) -baseStat
    public void DifferenceBuffStats(Monster monster){
        double tempAttack;
        double tempDefense;
        double tempSpAttack;
        double tempSpDefense;
        double tempSpeed;

        tempAttack = Math.floor(monster.getATK() * buffFactor(this.buffAttack));
        tempDefense = Math.floor(monster.getATK() * buffFactor(this.buffDefense));
        tempSpAttack = Math.floor(monster.getATK() * buffFactor(this.buffSpAttack));
        tempSpDefense = Math.floor(monster.getATK() * buffFactor(this.buffSpDefense));
        tempSpeed = Math.floor(monster.getATK() * buffFactor(this.buffSpeed));

        this.differenceBuffAttack = tempAttack - monster.getATK();
        this.differenceBuffDefense = tempDefense - monster.getATK();
        this.differenceBuffSpAttack = tempSpAttack - monster.getATK();
        this.differenceBuffSpDefense = tempSpDefense - monster.getATK();
        this.differenceBuffSpeed = tempSpeed - monster.getATK();
    }

    //mengubah nilai stat awal menjadi stat buff
    public void setBufftoStats(Monster monster){
        monster.modifHP(differenceBuffAttack);
        monster.modifDEF(differenceBuffDefense);
        monster.modifSpATK(differenceBuffSpAttack);
        monster.modifSpDEF(differenceBuffSpDefense);
        monster.modifSpeed(differenceBuffSpeed);
    }

    public void resetBuffStats(Monster monster){
        monster.setHP(originAttack);
        monster.setDEF(originDefense);
        monster.setSpATK(originSpAttack);
        monster.setSpDEF(originSpDefense);
        monster.setSpeed(originSpeed);
    }
}
