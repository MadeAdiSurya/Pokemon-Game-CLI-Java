// import java.io.ObjectInputFilter.Status;
import java.util.*;

public class Status {
    private boolean canMove;
    private StatusType monsterStatus;
    private int sleepCount;

    //konstruktor
    /**
     * @param sleepCount //memiliki nilai jumlah turn monster dalam status sleep, jika sleepCount > 0 maka monster dalam status sleep
     * @param canMove //true jika move dapat dilakukan, false jika tidak
     */
    public Status(){
        this.canMove = true;
        this.monsterStatus = StatusType.NONE;
        this.sleepCount = 0;
    }

    
    /**
     * 
     * Getter
     */

     //mengembalikan status type dari monster
    public StatusType getStatus(){
        return this.monsterStatus;
        //NONE jika monster tidak menerima status effect apapun
    }

    public int getSleepCount(){
        return this.sleepCount;
    }

    
    //true jika mosnter dapat melakukan move, false jika tidak
    public boolean isCanMove(){
        return this.canMove ;
    }
    
    
    //true jika monster memiliki status conditios SLEEP
    public boolean isSlept(){
        return (this.sleepCount > 0) || this.getStatus().equals(StatusType.SLEEP);
    }

     //true jika monster memiliki status condition POISON 
    public boolean isPoisoned(){
        return this.getStatus().equals(StatusType.POISON);
    }

    //true jika monster memiliki status conditios PARALYZE
    public boolean isParalyzed(){
        return this.getStatus().equals(StatusType.PARALYZE);
    }

    //true jika monster memiliki status conditios BURN
    public boolean isBurned(){
        return this.getStatus().equals(StatusType.BURN);
    }

    
    /**
     * Method status condition
     * 
     */
    public void setStatus(Monster monster, StatusType statusType) {
        if(statusType==StatusType.BURN) {
            doBurn(monster);
        } else if(statusType==StatusType.PARALYZE) {
            doParalyze(monster);
        } else if(statusType==StatusType.POISON) {
            doPoison(monster);
        } else if(statusType==StatusType.SLEEP) {
            doSleep(monster);
        }
    }
    //STATUS BURN
    public void doBurn(Monster monster){
        updateStatusCondition(StatusType.BURN);
        burnCalculation(monster);
        // double damage = -1 *((0.5)* monster.getATK()); //Monster yang terkena burn akan berkurang damage outputnya sebesar 50%
        // monster.modifATK(damage);
    }

    //memberikan effect burn untuk tiap turn monster
    double tempHealth = 0;
    public void burnCalculation(Monster monster){
        // Monster theMonster = (Monster) monster;
        System.out.println("-----BURN EFFECT-----");
        tempHealth = -1*( 0.125 * monster.getFullHP());
        monster.modifHP(tempHealth); //HP berkurang setiap turn
        System.out.println(monster.getname()+" terkena damage akibat memiliki status BURN");
        System.out.println("HP " + monster.getname() + " berkurang sebesar " + -tempHealth);
        
    }
    
    //STATUS SLEEP
    public void doSleep(Monster monster){
        updateStatusCondition(StatusType.SLEEP);
        int tempSleep = (int)Math.floor(Math.random()*(7)+1);
        this.sleepCount = tempSleep;
        monster.getStatusCondition().cannotMove();
        //takeSleep dibuat di luar method ini
    }
    
    //STATUS PARALYZE
    public void doParalyze(Monster monster){
        updateStatusCondition(StatusType.PARALYZE);
        double tempSpeed = -1*(monster.getSpeed()*(0.5));
        monster.modifSpeed(tempSpeed);
        paralyzeCalculation(monster);
        System.out.println("Speed " + monster.getname() + " berkurang sebesar" + -tempSpeed);
    }

    public void paralyzeCalculation(Monster monster){
        System.out.println("-----PARALYZE EFFECT-----");
        boolean tempCanMove = false;
        double tempRandom = (double)(Math.random()*(1)+(0.01)); //random 0.01 - 1.00
        
        if (tempRandom <=0.25){ //monster yang terkena status PAR memiliki chance 25% tidak dapat melakukan move
            tempCanMove = true; 
        }

        if ((!isCanMove() && (tempCanMove = false))){
            this.cannotMove();
            System.out.println(monster.getname() + " tidak dapat melakukan move karena efek PARALYZE");
        }
        
    }

    //STATUS POISON
    public void doPoison(Monster monster){
        updateStatusCondition(StatusType.POISON);
        poisonCalculation(monster);
    }

    public void poisonCalculation(Monster monster){
        System.out.println("-----POISON EFFECT-----");
        double tempHealth = -1* (0.0625 * monster.getFullHP());
        monster.modifHP(tempHealth);
        System.out.println(monster.getname()+" terkena damage akibat memiliki status POISON");
        System.out.println("HP " + monster.getname() + " berkurang sebesar" + -tempHealth);
        
    }
    
    /**
     * 
     * Method terkait status condition
     */

    //memperbarui status condition monster
    public void updateStatusCondition(StatusType monsterStatus){
        this.monsterStatus = monsterStatus;
    }

    //melakukan iterasi sleep count saat monster mendapatkan effect sleep
    public void sleepCalculation(Monster monster){
        System.out.println("-----SLEEP EFFECT-----");
        if (sleepCount > 0){
                this.sleepCount -=1;
                System.out.println(monster.getname() + " masih tertidur untuk " + monster.getStatusCondition().getSleepCount() + " turn lagi");
                System.out.println(monster.getname() + " tidak dapat melakukan move karena efek SLEEP");
            }
        else{
            this.updateStatusCondition(StatusType.NONE);
            this.canMove = true;
            System.out.println(monster.getname() + " WAKE UP");
        }
    }

    //monster tidak dapat melakukan move
    public void cannotMove(){
        this.canMove = false;
    }

    // bikin method yang ngecekin status dari monster untuk tiap turn 
    //nanti kalau statusnya bukan none bakal langsung kena effectnya
    //jadi dicekinnya pake 1 method 

    public void checkStatusCondition(Monster monster){
        if (monster.isPlayable()){
            if (monster.getStatusCondition().getStatus() == StatusType.BURN){
                burnCalculation(monster);
            }
            else if (monster.getStatusCondition().getStatus() == StatusType.SLEEP){
                sleepCalculation(monster);
            }
            else if (monster.getStatusCondition().getStatus() == StatusType.POISON){
                poisonCalculation(monster);
            }
            else if (monster.getStatusCondition().getStatus() == StatusType.PARALYZE){
                paralyzeCalculation(monster);
            }
        }
    }
    
  

    //mengecek status monster
    public void printStatusCondition(){
        String tempStatus= this.getStatus().name();
        System.out.println("Monster memiliki status condition "+ tempStatus);
    }

}
