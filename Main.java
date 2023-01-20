import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static double effectivityCalculation(Move source, ElementType target, List<String[]> effectivityList) {
        ElementType elmntSource=ElementType.GRASS;
        ElementType elmntTarget=ElementType.GRASS;

        for(String[] effectivity : effectivityList){
            if(effectivity[0].equals("NORMAL")) {
                elmntSource = ElementType.NORMAL;
            } else if(effectivity[0].equals("FIRE")) {
                elmntSource = ElementType.FIRE;
            } else if(effectivity[0].equals("WATER")) {
                elmntSource = ElementType.WATER;
            } else if(effectivity[0].equals("GRASS")) {
                elmntSource = ElementType.GRASS;
            }

            if(effectivity[1].equals("NORMAL")) {
                elmntTarget = ElementType.NORMAL;
            } else if(effectivity[1].equals("FIRE")) {
                elmntTarget = ElementType.FIRE;
            } else if(effectivity[1].equals("WATER")) {
                elmntTarget = ElementType.WATER;
            } else if(effectivity[1].equals("GRASS")) {
                elmntTarget = ElementType.GRASS;
            }

            if(source.getElementType()==elmntSource && target==elmntTarget) {
                return Double.parseDouble(effectivity[2]);
            }
        }
        return 1;
    }

    public static double damageNonElmntCalculation(Move move, Monster source, Monster target, boolean burn) {
        double rndm = (Math.random()*(1)+0.85);
        if (burn) {
            if(move.getTypeMove()==TypeMove.SPECIAL) {
                double hasil = (move.getBasePower() * (source.getSpATK() / target.getSpDEF()) + 2) * rndm * 0.5;
                return hasil;
            } else {
                double hasil = (move.getBasePower() * (source.getATK() / target.getDEF()) + 2) * rndm * 0.5;
                return hasil;
            }
        // else if (move.getTypeMove() == TypeMove.SPECIAL &&{

        // }
        } else {
            if(move.getTypeMove()==TypeMove.SPECIAL) {
                double hasil = (move.getBasePower() * (source.getSpATK() / target.getSpDEF()) + 2) * rndm * 1;
                return hasil;
            } else {
                double hasil = (move.getBasePower() * (source.getATK() / target.getDEF()) + 2) * rndm * 1;
                return hasil;
            }
        }
    }

    public static void NOTSTATUSandNOTSTATUSbyPRIORITY(Player FirstMovingPlayer, Monster FirstMovingMonster, Move FirstMove, Player SecondMovingPlayer, Monster SecondMovingMonster, Move SecondMove, List<String[]> effectivityList){
            double damage1 = 0;
            double damage2 = 0;

            if(FirstMove.getPriority()>SecondMove.getPriority()) {
                damage1 = damageNonElmntCalculation(FirstMove, FirstMovingMonster, SecondMovingMonster, FirstMovingMonster.getStatusCondition().isBurned());
                double effectivityTemp1 = 1;
                //MONSTER1 MOVE BUKAN STATUS
                if(FirstMovingMonster.getStatusCondition().isCanMove() == true) {
                    for(ElementType elementTypeMonster2 : SecondMovingMonster.getelementTypes()) {
                        effectivityTemp1 = effectivityTemp1 * effectivityCalculation(FirstMove, elementTypeMonster2, effectivityList);
                    }
                    damage1 = damage1 * effectivityTemp1;
                    if (SecondMovingMonster.getHP() - damage1 <= 0){
                        SecondMovingMonster.setHP(0.0);
                    } else {
                        SecondMovingMonster.modifHP(-damage1);
                    }
                    System.out.println(FirstMovingMonster.getname()+" menggunakan "+FirstMove.getName()+"!");
                    System.out.println(SecondMovingMonster.getname()+" terkena damage sebesar "+damage1);
                }
                FirstMovingMonster.getStatusCondition().checkStatusCondition(FirstMovingMonster);

                //MONSTER2 MOVE BUKAN STATUS
                if(SecondMovingMonster.isPlayable()){
                    damage2 = damageNonElmntCalculation(SecondMove, SecondMovingMonster, FirstMovingMonster, SecondMovingMonster.getStatusCondition().isBurned());
                    double effectivityTemp2 = 1;
                    if(SecondMovingMonster.getStatusCondition().isCanMove() == true) {
                        for(ElementType elementTypeMonster1 : FirstMovingMonster.getelementTypes()) {
                            effectivityTemp2 = effectivityTemp2 * effectivityCalculation(SecondMove, elementTypeMonster1, effectivityList);
                        }
                        damage2 = damage2 * effectivityTemp2;
                        if (FirstMovingMonster.getHP() - damage2 <= 0){
                            FirstMovingMonster.setHP(0.0);
                        } else {
                            FirstMovingMonster.modifHP(-damage2);
                        }
                        System.out.println(SecondMovingMonster.getname()+" menggunakan "+SecondMove.getName()+"!");
                        System.out.println(FirstMovingMonster.getname()+" terkena damage sebesar "+damage2);
                    }
                    SecondMovingMonster.getStatusCondition().checkStatusCondition(SecondMovingMonster);
                }
            }

    }

    public static void NOTSTATUSandNOTSTATUSbySPEED(Player FirstMovingPlayer, Monster FirstMovingMonster, Move FirstMove, Player SecondMovingPlayer, Monster SecondMovingMonster, Move SecondMove, List<String[]> effectivityList){
        double damage1 = 0;
        double damage2 = 0;
        if(FirstMovingMonster.getSpeed()>SecondMovingMonster.getSpeed()) {
            damage1 = damageNonElmntCalculation(FirstMove, FirstMovingMonster, SecondMovingMonster, FirstMovingMonster.getStatusCondition().isBurned());
            double effectivityTemp1 = 1;
            //MONSTER1 MOVE BUKAN STATUS
            if(FirstMovingMonster.getStatusCondition().isCanMove() == true) {
                for(ElementType elementTypeMonster2 : SecondMovingMonster.getelementTypes()) {
                    effectivityTemp1 = effectivityTemp1 * effectivityCalculation(FirstMove, elementTypeMonster2, effectivityList);
                }
                damage1 = damage1 * effectivityTemp1;
                if (SecondMovingMonster.getHP() - damage1 <= 0){
                    SecondMovingMonster.setHP(0.0);
                } else {
                    SecondMovingMonster.modifHP(-damage1);
                } 
                System.out.println(FirstMovingMonster.getname()+" menggunakan "+FirstMove.getName()+"!");
                System.out.println(SecondMovingMonster.getname()+" terkena damage sebesar "+damage1);
            }
            FirstMovingMonster.getStatusCondition().checkStatusCondition(FirstMovingMonster);

            //MONSTER2 MOVE BUKAN STATUS
            if(SecondMovingMonster.isPlayable()) {
                damage2 = damageNonElmntCalculation(SecondMove, SecondMovingMonster, FirstMovingMonster, SecondMovingMonster.getStatusCondition().isBurned());
                double effectivityTemp2 = 1;
                if(SecondMovingMonster.getStatusCondition().isCanMove() == true) {
                    for(ElementType elementTypeMonster1 : FirstMovingMonster.getelementTypes()) {
                        effectivityTemp2 = effectivityTemp2 * effectivityCalculation(SecondMove, elementTypeMonster1, effectivityList);
                    }
                    damage2 = damage2 * effectivityTemp2;
                    if (FirstMovingMonster.getHP() - damage2 <= 0){
                        FirstMovingMonster.setHP(0.0);
                    } else {
                        FirstMovingMonster.modifHP(-damage2);
                    }
                    System.out.println(SecondMovingMonster.getname()+" menggunakan "+SecondMove.getName()+"!");
                    System.out.println(FirstMovingMonster.getname()+" terkena damage sebesar "+damage2);
                }
                SecondMovingMonster.getStatusCondition().checkStatusCondition(SecondMovingMonster);
            }
        }
    }

    public static void NOTSTATUSandNOTSTATUSbyRANDOM(Player FirstMovingPlayer, Monster FirstMovingMonster, Move FirstMove, Player SecondMovingPlayer, Monster SecondMovingMonster, Move SecondMove, List<String[]> effectivityList){
        double damage1 = 0;
        double damage2 = 0;
        int x = (int)Math.floor(Math.random()*(2)+1);

        if(x==1) {
            damage1 = damageNonElmntCalculation(FirstMove, FirstMovingMonster, SecondMovingMonster, FirstMovingMonster.getStatusCondition().isBurned());
            double effectivityTemp1 = 1;
            //MONSTER1 MOVE BUKAN STATUS
            if(FirstMovingMonster.getStatusCondition().isCanMove() == true) {
                for(ElementType elementTypeMonster2 : SecondMovingMonster.getelementTypes()) {
                    effectivityTemp1 = effectivityTemp1 * effectivityCalculation(FirstMove, elementTypeMonster2, effectivityList);
                }
                damage1 = damage1 * effectivityTemp1;
                if (SecondMovingMonster.getHP() - damage1 <= 0){
                    SecondMovingMonster.setHP(0.0);
                } else {
                    SecondMovingMonster.modifHP(-damage1);
                }
                System.out.println(FirstMovingMonster.getname()+" menggunakan "+FirstMove.getName()+"!");
                System.out.println(SecondMovingMonster.getname()+" terkena damage sebesar "+damage1);
            }
            FirstMovingMonster.getStatusCondition().checkStatusCondition(FirstMovingMonster);

            //MONSTER2 MOVE BUKAN STATUS
            if(SecondMovingMonster.isPlayable()) {
                damage2 = damageNonElmntCalculation(SecondMove, SecondMovingMonster, FirstMovingMonster, SecondMovingMonster.getStatusCondition().isBurned());
                double effectivityTemp2 = 1;
                if(SecondMovingMonster.getStatusCondition().isCanMove() == true) {
                    for(ElementType elementTypeMonster1 : FirstMovingMonster.getelementTypes()) {
                        effectivityTemp2 = effectivityTemp2 * effectivityCalculation(SecondMove, elementTypeMonster1, effectivityList);
                    }
                    damage2 = damage2 * effectivityTemp2;
                    if (FirstMovingMonster.getHP() - damage2 <= 0){
                        FirstMovingMonster.setHP(0.0);
                    } else {
                        FirstMovingMonster.modifHP(-damage2);
                    }
                    System.out.println(SecondMovingMonster.getname()+" menggunakan "+SecondMove.getName()+"!");
                    System.out.println(FirstMovingMonster.getname()+" terkena damage sebesar "+damage2);
                }
                SecondMovingMonster.getStatusCondition().checkStatusCondition(SecondMovingMonster);
            }
            
        } else if(x==2) {
            damage2 = damageNonElmntCalculation(SecondMove, SecondMovingMonster, FirstMovingMonster, SecondMovingMonster.getStatusCondition().isBurned());
            double effectivityTemp2 = 1;

            //MONSTER2 MOVE BUKAN STATUS
            if(SecondMovingMonster.getStatusCondition().isCanMove() == true) {
                for(ElementType elementTypeMonster1 : FirstMovingMonster.getelementTypes()) {
                    effectivityTemp2 = effectivityTemp2 * effectivityCalculation(SecondMove, elementTypeMonster1, effectivityList);
                }
                damage2 = damage2 * effectivityTemp2;
                if (FirstMovingMonster.getHP() - damage2 <= 0){
                    FirstMovingMonster.setHP(0.0);
                } else {
                    FirstMovingMonster.modifHP(-damage2);
                }
                System.out.println(SecondMovingMonster.getname()+" menggunakan "+SecondMove.getName()+"!");
                System.out.println(FirstMovingMonster.getname()+" terkena damage sebesar "+damage2);
            }
            SecondMovingMonster.getStatusCondition().checkStatusCondition(SecondMovingMonster);

            //MONSTER1 MOVE BUKAN STATUS
            if(FirstMovingMonster.isPlayable()) {
                damage1 = damageNonElmntCalculation(FirstMove, FirstMovingMonster, SecondMovingMonster, FirstMovingMonster.getStatusCondition().isBurned());
                double effectivityTemp1 = 1;
                if(FirstMovingMonster.getStatusCondition().isCanMove() == true) {
                    for(ElementType elementTypeMonster2 : SecondMovingMonster.getelementTypes()) {
                        effectivityTemp1 = effectivityTemp1 * effectivityCalculation(FirstMove, elementTypeMonster2, effectivityList);
                    }
                    damage1 = damage1 * effectivityTemp1;
                    if (SecondMovingMonster.getHP() - damage1<= 0){
                        SecondMovingMonster.setHP(0.0);
                    } else {
                        SecondMovingMonster.modifHP(-damage1);
                    }
                    System.out.println(FirstMovingMonster.getname()+" menggunakan "+FirstMove.getName()+"!");
                    System.out.println(SecondMovingMonster.getname()+" terkena damage sebesar "+damage1);
                }
                FirstMovingMonster.getStatusCondition().checkStatusCondition(FirstMovingMonster);
            }
        }    
    }
    
    public static void STATUSandNOTSTATUSbyPRIORITY(Player PlayerDOStatusMove, Monster MonsterDOStatusMove, Move theStatusMove, Player PlayerDONOTStatusMove, Monster MonsterDONOTStatusMove, Move nonStatusMove, List<String[]> effectivityList){
        double damage2 = 0;
        double damage1 = 0;
        damage2 = damageNonElmntCalculation(nonStatusMove, MonsterDONOTStatusMove, MonsterDOStatusMove, MonsterDONOTStatusMove.getStatusCondition().isBurned());

        if(theStatusMove.getPriority()>nonStatusMove.getPriority()) {
            //MONSTER MOVE STATUS
            if(MonsterDOStatusMove.getStatusCondition().isCanMove() == true) {
                if(theStatusMove.getTargetMove()==TargetMove.OWN) {
                    damage1 = MonsterDOStatusMove.getFullHP() * theStatusMove.getEffHP();
                    if((MonsterDOStatusMove.getHP()+damage1)>MonsterDOStatusMove.getFullHP()) {
                        MonsterDOStatusMove.setHP(MonsterDOStatusMove.getFullHP());
                    } else {
                        MonsterDOStatusMove.modifHP(damage1);
                    }
                    System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                } else {
             
                    System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                    System.out.println(MonsterDONOTStatusMove.getname()+" terkena "+theStatusMove.getMoveStatusType()+"!");
                    MonsterDONOTStatusMove.getStatusCondition().setStatus(MonsterDONOTStatusMove, theStatusMove.getMoveStatusType());
                }
            }
            MonsterDOStatusMove.getStatusCondition().checkStatusCondition(MonsterDOStatusMove);
            
            //MONSTER MOVE BUKAN STATUS
            if(MonsterDONOTStatusMove.isPlayable()) {
                if(MonsterDONOTStatusMove.getStatusCondition().isCanMove() == true) {
                    double effectivityTemp2 = 1;
                    for(ElementType elementTypeMonster1 : MonsterDOStatusMove.getelementTypes()) {
                        effectivityTemp2 = effectivityTemp2 * effectivityCalculation(nonStatusMove, elementTypeMonster1, effectivityList);
                    }
                    damage2 = damage2 * effectivityTemp2;
                    if (MonsterDOStatusMove.getHP() - damage2 <= 0) {
                        MonsterDOStatusMove.setHP(0.0);
                    } else {
                        MonsterDOStatusMove.modifHP(-damage2);
                    }
                    System.out.println(MonsterDONOTStatusMove.getname()+" menggunakan "+nonStatusMove.getName()+"!");
                    System.out.println(MonsterDOStatusMove.getname()+" terkena damage sebesar "+damage2);
                }
                MonsterDONOTStatusMove.getStatusCondition().checkStatusCondition(MonsterDONOTStatusMove);
            }
            
        } else if(nonStatusMove.getPriority()>theStatusMove.getPriority()) {
            double effectivityTemp2 = 1;

            //MONSTER MOVE BUKAN STATUS
            if(MonsterDONOTStatusMove.getStatusCondition().isCanMove() == true) {
                for(ElementType elementTypeMonster1 : MonsterDOStatusMove.getelementTypes()) {
                    effectivityTemp2 = effectivityTemp2 * effectivityCalculation(nonStatusMove, elementTypeMonster1, effectivityList);
                }
                damage2 = damage2 * effectivityTemp2;
                if (MonsterDOStatusMove.getHP() - damage2 <= 0){
                    MonsterDOStatusMove.setHP(0.0);
                } else {
                    MonsterDOStatusMove.modifHP(-damage2);
                }
                System.out.println(MonsterDONOTStatusMove.getname()+" menggunakan "+nonStatusMove.getName()+"!");
                System.out.println(MonsterDOStatusMove.getname()+" terkena damage sebesar "+damage2);
            }
            MonsterDONOTStatusMove.getStatusCondition().checkStatusCondition(MonsterDONOTStatusMove);

            //MONSTER MOVE STATUS
            if(MonsterDOStatusMove.isPlayable()) {
                if(MonsterDOStatusMove.getStatusCondition().isCanMove() == true) {
                    if(theStatusMove.getTargetMove()==TargetMove.OWN) {
                        damage1 = MonsterDOStatusMove.getFullHP() * theStatusMove.getEffHP();
                        if((MonsterDOStatusMove.getHP()+damage1)>MonsterDOStatusMove.getFullHP()) {
                            MonsterDOStatusMove.setHP(MonsterDOStatusMove.getFullHP());
                        } else {
                            MonsterDOStatusMove.modifHP(damage1);
                        }
                        System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                    } else {
                        System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                        System.out.println(MonsterDONOTStatusMove.getname()+" terkena "+theStatusMove.getMoveStatusType()+"!");
                        MonsterDONOTStatusMove.getStatusCondition().setStatus(MonsterDONOTStatusMove, theStatusMove.getMoveStatusType());
                    } 
                }
                MonsterDOStatusMove.getStatusCondition().checkStatusCondition(MonsterDOStatusMove);
            }
            
        }
    }

    public static void STATUSandNOTSTATUSbySPEED(Player PlayerDOStatusMove, Monster MonsterDOStatusMove, Move theStatusMove, Player PlayerDONOTStatusMove, Monster MonsterDONOTStatusMove, Move nonStatusMove, List<String[]> effectivityList){
        double damage2 = 0;
        double damage1 = 0;
        damage2 = damageNonElmntCalculation(nonStatusMove, MonsterDONOTStatusMove, MonsterDOStatusMove, MonsterDONOTStatusMove.getStatusCondition().isBurned());
        
        if(MonsterDOStatusMove.getSpeed()>MonsterDONOTStatusMove.getSpeed()) {
            //MONSTER MOVE STATUS
            if(MonsterDOStatusMove.getStatusCondition().isCanMove() == true) {
                if(theStatusMove.getTargetMove()==TargetMove.OWN) {
                    damage1 = MonsterDOStatusMove.getFullHP() * theStatusMove.getEffHP();
                    if((MonsterDOStatusMove.getHP()+damage1)>MonsterDOStatusMove.getFullHP()) {
                        MonsterDOStatusMove.setHP(MonsterDOStatusMove.getFullHP());
                    } else {
                        MonsterDOStatusMove.modifHP(damage1);
                    }
                    System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                } else {
                    System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                    System.out.println(MonsterDONOTStatusMove.getname()+" terkena "+theStatusMove.getMoveStatusType()+"!");
                    MonsterDONOTStatusMove.getStatusCondition().setStatus(MonsterDONOTStatusMove, theStatusMove.getMoveStatusType());
                }
            }
            MonsterDOStatusMove.getStatusCondition().checkStatusCondition(MonsterDOStatusMove);

            //MONSTER MOVE BUKAN STATUS
            if(MonsterDONOTStatusMove.isPlayable()) {
                if(MonsterDONOTStatusMove.getStatusCondition().isCanMove() == true) {
                    double effectivityTemp2 = 1;
                    for(ElementType elementTypeMonster1 : MonsterDOStatusMove.getelementTypes()) {
                        effectivityTemp2 = effectivityTemp2 * effectivityCalculation(nonStatusMove, elementTypeMonster1, effectivityList);
                    }
                    damage2 = damage2 * effectivityTemp2;
                    if (MonsterDOStatusMove.getHP() - damage2 <= 0){
                        MonsterDOStatusMove.setHP(0.0);
                    } else {
                        MonsterDOStatusMove.modifHP(-damage2);
                    }
                    System.out.println(MonsterDONOTStatusMove.getname()+" menggunakan "+nonStatusMove.getName()+"!");
                    System.out.println(MonsterDOStatusMove.getname()+" terkena damage sebesar "+damage2);
                }
                MonsterDONOTStatusMove.getStatusCondition().checkStatusCondition(MonsterDONOTStatusMove);
            }
        } else if(MonsterDONOTStatusMove.getSpeed()>MonsterDOStatusMove.getSpeed()) {
            double effectivityTemp2 = 1;
            //MONSTER MOVE BUKAN STATUS
            if(MonsterDONOTStatusMove.getStatusCondition().isCanMove() == true) {
                for(ElementType elementTypeMonster1 : MonsterDOStatusMove.getelementTypes()) {
                    effectivityTemp2 = effectivityTemp2 * effectivityCalculation(nonStatusMove, elementTypeMonster1, effectivityList);
                }
                damage2 = damage2 * effectivityTemp2;
                if (MonsterDOStatusMove.getHP() - damage2 <= 0) {
                    MonsterDOStatusMove.setHP(0.0);
                } else {
                    MonsterDOStatusMove.modifHP(-damage2);
                }
                System.out.println(MonsterDONOTStatusMove.getname()+" menggunakan "+nonStatusMove.getName()+"!");
                System.out.println(MonsterDOStatusMove.getname()+" terkena damage sebesar "+damage2);
            }
            MonsterDONOTStatusMove.getStatusCondition().checkStatusCondition(MonsterDONOTStatusMove);

            //MONSTER MOVE STATUS
            if(MonsterDOStatusMove.isPlayable()) {
                if(MonsterDOStatusMove.getStatusCondition().isCanMove() == true) {
                    if(theStatusMove.getTargetMove()==TargetMove.OWN) {
                        damage1 = MonsterDOStatusMove.getFullHP() * theStatusMove.getEffHP();
                        if((MonsterDOStatusMove.getHP()+damage1)>MonsterDOStatusMove.getFullHP()) {
                            MonsterDOStatusMove.setHP(MonsterDOStatusMove.getFullHP());
                        } else {
                            MonsterDOStatusMove.modifHP(damage1);
                        }
                        System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                    } else {
                       
                        System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                        System.out.println(MonsterDONOTStatusMove.getname()+" terkena "+theStatusMove.getMoveStatusType()+"!");
                        MonsterDONOTStatusMove.getStatusCondition().setStatus(MonsterDONOTStatusMove, theStatusMove.getMoveStatusType());
                    }    
                }
                MonsterDOStatusMove.getStatusCondition().checkStatusCondition(MonsterDOStatusMove);
            }
        }
    }

    public static void STATUSandNOTSTATUSbyRANDOM(Player PlayerDOStatusMove, Monster MonsterDOStatusMove, Move theStatusMove, Player PlayerDONOTStatusMove, Monster MonsterDONOTStatusMove, Move nonStatusMove, List<String[]> effectivityList){
        double damage2 = 0;
        double damage1 = 0;
        damage2 = damageNonElmntCalculation(nonStatusMove, MonsterDONOTStatusMove, MonsterDOStatusMove, MonsterDONOTStatusMove.getStatusCondition().isBurned());
        int x = (int)Math.floor(Math.random()*(2)+1);
        
        if(x==1) {
            //MONSTER MOVE STATUS
            if(MonsterDOStatusMove.getStatusCondition().isCanMove() == true) {
                if(theStatusMove.getTargetMove()==TargetMove.OWN) {
                    damage1 = MonsterDOStatusMove.getFullHP() * theStatusMove.getEffHP();
                    if((MonsterDOStatusMove.getHP()+damage1)>MonsterDOStatusMove.getFullHP()) {
                        MonsterDOStatusMove.setHP(MonsterDOStatusMove.getFullHP());
                    } else {
                        MonsterDOStatusMove.modifHP(damage1);
                    }
                    System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                } else {
                    System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                    System.out.println(MonsterDONOTStatusMove.getname()+" terkena "+theStatusMove.getMoveStatusType()+"!");
                    MonsterDONOTStatusMove.getStatusCondition().setStatus(MonsterDONOTStatusMove, theStatusMove.getMoveStatusType());
                }
            }
            MonsterDOStatusMove.getStatusCondition().checkStatusCondition(MonsterDOStatusMove);

            //MONSTER MOVE BUKAN STATUS
            if(MonsterDONOTStatusMove.isPlayable()) {
                if(MonsterDONOTStatusMove.getStatusCondition().isCanMove() == true) {
                    double effectivityTemp2 = 1;
                    for(ElementType elementTypeMonster1 : MonsterDOStatusMove.getelementTypes()) {
                        effectivityTemp2 = effectivityTemp2 * effectivityCalculation(nonStatusMove, elementTypeMonster1, effectivityList);
                    }
                    damage2 = damage2 * effectivityTemp2;
                    if (MonsterDOStatusMove.getHP() - damage2 <= 0){
                        MonsterDOStatusMove.setHP(0.0);
                    } else {
                        MonsterDOStatusMove.modifHP(-damage2);
                    }
                    System.out.println(MonsterDONOTStatusMove.getname()+" menggunakan "+nonStatusMove.getName()+"!");
                    System.out.println(MonsterDOStatusMove.getname()+" terkena damage sebesar "+damage2);
                }
                MonsterDONOTStatusMove.getStatusCondition().checkStatusCondition(MonsterDONOTStatusMove);
            }
        } else if(x==2) {
            double effectivityTemp2 = 1;

            //MONSTER MOVE BUKAN STATUS
            if(MonsterDONOTStatusMove.getStatusCondition().isCanMove() == true) {
                for(ElementType elementTypeMonster1 : MonsterDOStatusMove.getelementTypes()) {
                    effectivityTemp2 = effectivityTemp2 * effectivityCalculation(nonStatusMove, elementTypeMonster1, effectivityList);
                }
                damage2 = damage2 * effectivityTemp2;
                if (MonsterDOStatusMove.getHP() - damage2 <= 0){
                    MonsterDOStatusMove.setHP(0.0);
                }
                else{
                    MonsterDOStatusMove.modifHP(-damage2);
                }
                System.out.println(MonsterDONOTStatusMove.getname()+" menggunakan "+nonStatusMove.getName()+"!");
                System.out.println(MonsterDOStatusMove.getname()+" terkena damage sebesar "+damage2);
            }
            MonsterDONOTStatusMove.getStatusCondition().checkStatusCondition(MonsterDONOTStatusMove);

            //MONSTER MOVE STATUS
            if(MonsterDOStatusMove.isPlayable()) {
                if(MonsterDOStatusMove.getStatusCondition().isCanMove() == true) {
                    if(theStatusMove.getTargetMove()==TargetMove.OWN) {
                        damage1 = MonsterDOStatusMove.getFullHP() * theStatusMove.getEffHP();
                        if((MonsterDOStatusMove.getHP()+damage1)>MonsterDOStatusMove.getFullHP()) {
                            MonsterDOStatusMove.setHP(MonsterDOStatusMove.getFullHP());
                        } else {
                            MonsterDOStatusMove.modifHP(damage1);
                        }
                        System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                    } else {
                       
                        System.out.println(MonsterDOStatusMove.getname()+" melakukan "+theStatusMove.getName()+"!");
                        System.out.println(MonsterDONOTStatusMove.getname()+" terkena "+theStatusMove.getMoveStatusType()+"!");
                        MonsterDONOTStatusMove.getStatusCondition().setStatus(MonsterDONOTStatusMove, theStatusMove.getMoveStatusType());
                    }    
                }
                MonsterDOStatusMove.getStatusCondition().checkStatusCondition(MonsterDOStatusMove);
            }
        }
    }

    public static void STATUSandSTATUSbyPRIORITY(Player PlayerStatus1, Monster monsterStatus1, Move moveStatus1, Player PlayerStatus2, Monster monsterStatus2, Move moveStatus2, List<String[]> effectivityList){
        double damage1 = 0;
        double damage2 = 0;
        
        //MONSTER1 MOVE STATUS
        if(monsterStatus1.getStatusCondition().isCanMove()== true) {
            if(moveStatus1.getTargetMove()==TargetMove.OWN) {
                damage1 = monsterStatus1.getFullHP() * moveStatus1.getEffHP();
                if((monsterStatus1.getHP()+damage1)>monsterStatus1.getFullHP()) {
                    monsterStatus1.setHP(monsterStatus1.getFullHP());
                } else {
                    monsterStatus1.modifHP(damage1);
                }
                System.out.println(monsterStatus1.getname()+" melakukan "+moveStatus1.getName()+"!");
            } else {
                System.out.println(monsterStatus1.getname()+" melakukan "+moveStatus1.getName()+"!");
                System.out.println(monsterStatus2.getname()+" terkena "+moveStatus1.getMoveStatusType()+"!");
                monsterStatus2.getStatusCondition().setStatus(monsterStatus2, moveStatus1.getMoveStatusType());
            }
        }
        monsterStatus1.getStatusCondition().checkStatusCondition(monsterStatus1);

        //MONSTER2 MOVE STATUS
        if(monsterStatus2.isPlayable()) {
            if(monsterStatus1.getStatusCondition().isCanMove()== true) {
                if(moveStatus2.getTargetMove()==TargetMove.OWN) {
                    damage2 = monsterStatus2.getFullHP() * moveStatus2.getEffHP();
                    if((monsterStatus2.getHP()+damage2)>monsterStatus2.getFullHP()) {
                        monsterStatus2.setHP(monsterStatus2.getFullHP());
                    } else {
                        monsterStatus2.modifHP(damage2);
                    }
                    System.out.println(monsterStatus2.getname()+" melakukan "+moveStatus2.getName()+"!");
                } else {
                    System.out.println(monsterStatus2.getname()+" melakukan "+moveStatus2.getName()+"!");
                    System.out.println(monsterStatus1.getname()+" terkena "+moveStatus2.getMoveStatusType()+"!");
                    monsterStatus1.getStatusCondition().setStatus(monsterStatus1, moveStatus2.getMoveStatusType());
                }
            }
            monsterStatus2.getStatusCondition().checkStatusCondition(monsterStatus2);
        }
    }

    public static void STATUSandSTATUSbySPEED(Player PlayerStatus1, Monster monsterStatus1, Move moveStatus1, Player PlayerStatus2, Monster monsterStatus2, Move moveStatus2, List<String[]> effectivityList){
        double damage1 = 0;
        double damage2 = 0;
        //MONSTER1 MOVE STATUS
        if(monsterStatus1.getStatusCondition().isCanMove()== true) {
            if(moveStatus1.getTargetMove()==TargetMove.OWN) {
                damage1 = monsterStatus1.getFullHP() * moveStatus1.getEffHP();
                if((monsterStatus1.getHP()+damage1)>monsterStatus1.getFullHP()) {
                    monsterStatus1.setHP(monsterStatus1.getFullHP());
                } else {
                    monsterStatus1.modifHP(damage1);
                }
                System.out.println(monsterStatus1.getname()+" melakukan "+moveStatus1.getName()+"!");
            } else {
                System.out.println(monsterStatus1.getname()+" melakukan "+moveStatus1.getName()+"!");
                System.out.println(monsterStatus2.getname()+" terkena "+moveStatus1.getMoveStatusType()+"!");
                monsterStatus2.getStatusCondition().setStatus(monsterStatus2, moveStatus1.getMoveStatusType());
            }
        }
        monsterStatus1.getStatusCondition().checkStatusCondition(monsterStatus1);
        
        //MONSTER2 MOVE STATUS
        if(monsterStatus2.isPlayable()) {
            if(monsterStatus2.getStatusCondition().isCanMove()== true) {
                if(moveStatus2.getTargetMove()==TargetMove.OWN) {
                    damage2 = monsterStatus2.getFullHP() * moveStatus2.getEffHP();
                    if((monsterStatus2.getHP()+damage2)>monsterStatus2.getFullHP()) {
                        monsterStatus2.setHP(monsterStatus2.getFullHP());
                    } else {
                        monsterStatus2.modifHP(damage2);
                    }
                    System.out.println(monsterStatus2.getname()+" melakukan "+moveStatus2.getName()+"!");
                } else {
                    System.out.println(monsterStatus2.getname()+" melakukan "+moveStatus2.getName()+"!");
                    System.out.println(monsterStatus1.getname()+" terkena "+moveStatus2.getMoveStatusType()+"!");
                    monsterStatus1.getStatusCondition().setStatus(monsterStatus1, moveStatus2.getMoveStatusType());
                }
            }
            monsterStatus2.getStatusCondition().checkStatusCondition(monsterStatus2);
        }
    }

    public static void STATUSandSTATUSbyRANDOM(Player PlayerStatus1, Monster monsterStatus1, Move moveStatus1, Player PlayerStatus2, Monster monsterStatus2, Move moveStatus2, List<String[]> effectivityList){
        double damage1 = 0;
        double damage2 = 0;
        
        int x = (int)Math.floor(Math.random()*(2)+1);
        // MONSTER1 MOVE STATUS
        if(x==1) {
            if(monsterStatus1.getStatusCondition().isCanMove()== true) {
                if(moveStatus1.getTargetMove()==TargetMove.OWN) {
                    damage1 = monsterStatus1.getFullHP() * moveStatus1.getEffHP();
                    if((monsterStatus1.getHP()+damage1)>monsterStatus1.getFullHP()) {
                        monsterStatus1.setHP(monsterStatus1.getFullHP());
                    } else {
                        monsterStatus1.modifHP(damage1);
                    }
                    System.out.println(monsterStatus1.getname()+" melakukan "+moveStatus1.getName()+"!");
                } else {
                    System.out.println(monsterStatus1.getname()+" melakukan "+moveStatus1.getName()+"!");
                    System.out.println(monsterStatus2.getname()+" terkena "+moveStatus1.getMoveStatusType()+"!");
                    monsterStatus2.getStatusCondition().setStatus(monsterStatus2, moveStatus1.getMoveStatusType());
                }
            }
            monsterStatus1.getStatusCondition().checkStatusCondition(monsterStatus1);
            
            // MONSTER2 MOVE STATUS
            if(monsterStatus2.isPlayable()) {
                if(monsterStatus2.getStatusCondition().isCanMove()== true) {
                    if(moveStatus2.getTargetMove()==TargetMove.OWN) {
                        damage2 = monsterStatus2.getFullHP() * moveStatus2.getEffHP();
                        if((monsterStatus2.getHP()+damage2)>monsterStatus2.getFullHP()) {
                            monsterStatus2.setHP(monsterStatus2.getFullHP());
                        } else {
                            monsterStatus2.modifHP(damage2);
                        }
                        System.out.println(monsterStatus2.getname()+" melakukan "+moveStatus2.getName()+"!");
                    } else {
                        System.out.println(monsterStatus2.getname()+" melakukan "+moveStatus2.getName()+"!");
                        System.out.println(monsterStatus1.getname()+" terkena "+moveStatus2.getMoveStatusType()+"!");
                        monsterStatus1.getStatusCondition().setStatus(monsterStatus1, moveStatus2.getMoveStatusType());
                    }
                }
                monsterStatus2.getStatusCondition().checkStatusCondition(monsterStatus2);
            }
        } else if(x==2) {
            //MONSTER2 MOVE STATUS
            if(monsterStatus2.getStatusCondition().isCanMove()== true) {
                if(moveStatus2.getTargetMove()==TargetMove.OWN) {
                    damage2 = monsterStatus2.getFullHP() * moveStatus2.getEffHP();
                    if((monsterStatus2.getHP()+damage2)>monsterStatus2.getFullHP()) {
                        monsterStatus2.setHP(monsterStatus2.getFullHP());
                    } else {
                        monsterStatus2.modifHP(damage2);
                    }
                    System.out.println(monsterStatus2.getname()+" melakukan "+moveStatus2.getName()+"!");
                } else {
                    System.out.println(monsterStatus2.getname()+" melakukan "+moveStatus2.getName()+"!");
                    System.out.println(monsterStatus1.getname()+" terkena "+moveStatus2.getMoveStatusType()+"!");
                    monsterStatus1.getStatusCondition().setStatus(monsterStatus1, moveStatus2.getMoveStatusType());
                }
            }
            monsterStatus2.getStatusCondition().checkStatusCondition(monsterStatus2);

            //MONSTER1 MOVE STATUS
            if(monsterStatus1.isPlayable()) {
                if(monsterStatus1.getStatusCondition().isCanMove() == true) {
                    if(moveStatus1.getTargetMove()==TargetMove.OWN) {
                        damage1 = monsterStatus1.getFullHP() * moveStatus1.getEffHP();
                        if((monsterStatus1.getHP()+damage1)>monsterStatus1.getFullHP()) {
                            monsterStatus1.setHP(monsterStatus1.getFullHP());
                        } else {
                            monsterStatus1.modifHP(damage1);
                        }
                        System.out.println(monsterStatus1.getname()+" melakukan "+moveStatus1.getName()+"!");
                    } else {
                        System.out.println(monsterStatus1.getname()+" melakukan "+moveStatus1.getName()+"!");
                        System.out.println(monsterStatus2.getname()+" terkena "+moveStatus1.getMoveStatusType()+"!");
                        monsterStatus2.getStatusCondition().setStatus(monsterStatus2, moveStatus1.getMoveStatusType());
                    }    
                }
                monsterStatus1.getStatusCondition().checkStatusCondition(monsterStatus1);
            }
        }
    }

    public static void damageCalculation(Player player1, Monster monster1, Move move1, Player player2, Monster monster2, Move move2, List<String[]> effectivityList){
        Scanner sc = new Scanner(System.in);
        if(move1.getTypeMove()!=TypeMove.STATUS && move2.getTypeMove()!=TypeMove.STATUS) {
            // perhitungan + effectivity + prioritas
            if(move1.getPriority()>move2.getPriority() && monster1.getStatusCondition().isCanMove() == true) {
                Main.NOTSTATUSandNOTSTATUSbyPRIORITY(player1, monster1, move1, player2, monster2, move2, effectivityList);
            } else if(move2.getPriority()>move1.getPriority() && monster2.getStatusCondition().isCanMove() == true) {
                Main.NOTSTATUSandNOTSTATUSbyPRIORITY(player2, monster2, move2, player1, monster1, move1, effectivityList);
            } else {
                if(monster1.getSpeed()>monster2.getSpeed() && monster1.getStatusCondition().isCanMove() == true) {
                    Main.NOTSTATUSandNOTSTATUSbySPEED(player1, monster1, move1,player2, monster2, move2, effectivityList);
                } else if(monster2.getSpeed()>monster1.getSpeed() && monster2.getStatusCondition().isCanMove() == true) {
                    Main.NOTSTATUSandNOTSTATUSbySPEED(player2, monster2, move2, player1, monster1, move1, effectivityList);
                } else {
                    Main.NOTSTATUSandNOTSTATUSbyRANDOM(player1, monster1, move1,player2, monster2, move2, effectivityList);
                }
            }

        } else if(move1.getTypeMove()==TypeMove.STATUS && move2.getTypeMove()!=TypeMove.STATUS) {
            if (monster1.getMoveWithID(move1.getMoveID()).getPriority() !=monster2.getMoveWithID(move2.getMoveID()).getPriority()){
                Main.STATUSandNOTSTATUSbyPRIORITY(player1, monster1, move1, player2, monster2, move2, effectivityList);
            }
            else {
                if (monster1.getSpeed() != monster2.getSpeed()){
                    Main.STATUSandNOTSTATUSbySPEED(player1, monster1, move1, player2, monster2, move2, effectivityList);
                }
                else {
                    Main.STATUSandNOTSTATUSbyRANDOM(player1, monster1, move1, player2, monster2, move2, effectivityList);
                }
            } 
        }
         else if(move1.getTypeMove()!=TypeMove.STATUS && move2.getTypeMove()==TypeMove.STATUS) {
            if (monster1.getMoveWithID(move1.getMoveID()).getPriority() !=monster2.getMoveWithID(move2.getMoveID()).getPriority()){
                Main.STATUSandNOTSTATUSbyPRIORITY(player2, monster2, move2, player1, monster1, move1, effectivityList);
            }
            else {
                if (monster1.getSpeed() != monster2.getSpeed()){
                    Main.STATUSandNOTSTATUSbySPEED(player2, monster2, move2, player1, monster1, move1, effectivityList);
                }
                else {
                    Main.STATUSandNOTSTATUSbyRANDOM(player2, monster2, move2, player1, monster1, move1, effectivityList);
                }
            }
        }else {
            if(move1.getPriority()>move2.getPriority()) {
               Main.STATUSandSTATUSbyPRIORITY(player1, monster1, move1, player2, monster2, move2, effectivityList);
            } else if(move2.getPriority()>move1.getPriority()) {
                Main.STATUSandSTATUSbyPRIORITY(player2, monster2, move2, player1, monster1, move1, effectivityList);
                }
            else {
                if(monster1.getSpeed()>monster2.getSpeed()) {
                   Main.STATUSandSTATUSbySPEED(player1, monster1, move1, player2, monster2, move2, effectivityList);
                }
                else if(monster2.getSpeed()>monster1.getSpeed()) {
                    Main.STATUSandSTATUSbySPEED(player2, monster2, move2, player1, monster1, move1, effectivityList);
                }
                else {
                    Main.STATUSandSTATUSbyRANDOM(player1, monster1, move1, player2, monster2, move2, effectivityList);
                }
            }
        }
        player1.setMonsterAlive(player1.CountMonsterAlive());
        player2.setMonsterAlive(player2.CountMonsterAlive());
        SwitchFainted(player1, monster1);
        SwitchFainted(player2, monster2);

    }

    public static void StopGame(boolean canWeStop){
        canWeStop = true;
    }

    public static Player getWinner(Player player1, Player player2){
        //mengembalikan null jika belum ada pemenang
        Player winner = null;
        if (player1.getMonsterAlive() ==0){
            winner = player2;

        } 
        else if (player2.getMonsterAlive() ==0){
            winner = player1;
        }
        return winner;
    }

    public static void checkTheWinner(Player player1, Player player2){
        if (!isStillBattle(player1, player2)){
            System.out.println("Tut.... Tut... Tut....");
            System.out.println("Kita sudah punya pemenangnya!");
            // System.out.println(player1.getMonsterAlive());
            // System.out.println(player2.getMonsterAlive());
            printWinner(player1, player2);
        }
    }

    public static void printWinner(Player player1, Player player2){
        Player champion = getWinner(player1, player2);
        System.out.println("         *********** ");
        System.out.println("      **             **");
        System.out.println("    **                 **");
        System.out.println("  **          *           **");
        System.out.println(" **      |THE WINNER|      **");
        System.out.println(" **           *            **");
        System.out.println("  **                      **");
        System.out.println("    **                  **");
        System.out.println("       *************** ");
        System.out.println(" ");
        System.out.println("       CONGRATULATIONS");
        System.out.println(" ");
        System.out.println("Selamat! "+ champion.getPlayerName() + " berhasil menjadi pemenang dalam game ini");
    }

    public static void printHelp(){
        System.out.println("---HELP---");
        System.out.println("Permainan dimainkan oleh 2 orang player");
        System.out.println("Setiap player memiliki 6 monster yang dipilih secara acak");
        System.out.println("Pada setiap gilirannya, seorang player dapat melakukan : ");
        System.out.println("    1. Move");
        System.out.println("        Move adalah cara monster untuk menyerang monster lawan. Jenis move terdiri dari default move,  normal move, special move, dan status move ");
        System.out.println("           Tipe status move akan memberikan efek pada target ");
        System.out.println("           Tipe default move, normal move, dan special move akan memberikan efek damage pada target ");
        System.out.println("    2. Switch");
        System.out.println("        Switch adalah cara dari seorang player untuk mengganti monster yang digunakan bertarung. ");
        System.out.println("    3. Monster List Information ");
        System.out.println("        Jika seorang player memilih input ini maka akan ditampilkan informasi mengenai Turn, Monster yang sedang digunakan bertarung, serta List Monster yang dimiliki player. ");
        System.out.println("    4. Exit Game ");
        System.out.println("        Game akan diakhiri dan program akan berhenti. ");
        System.out.println(" ");
        System.out.println("Game akan selesai ketika salah satu Player sudah tidak memiliki monster lagi untuk bertarung (seluruh monster telah FAINTED");
        System.out.println("Dengan begitu, Player yang masih memiliki monster yang tidak FAINTED (still alive) akan menjadi pemenangnya ");
        System.out.println(" ");

    }


    public static void openGame(){
        try{
            System.out.print(".");
            Thread.sleep(1000);
            System.out.print("..");
            Thread.sleep(1000);
            System.out.print("..");
            Thread.sleep(1000);
            System.out.print("...");
            Thread.sleep(1000);
            System.out.print("..");
            System.out.println(" ");
            System.out.println("");
            System.out.println("SONI PLAYSTEYSYEN 4");
            System.out.println("Opening MONSTER SAKU");
            Thread.sleep(1000);
            System.out.print("..");
            System.out.println(" ");
            System.out.println("developed by : Group 12");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("-------MONSTER SAKU-------");
        }catch (Exception e){
            // do nothing
        }
    }

    public static Move selectedMove(Player player, int idMove){
        Move selected =player.getCurrentMonster().getMoveWithID(idMove);
        return selected; 
    }

    public static void Welcome(){

    }

    public static boolean isStillBattle(Player player1, Player player2){
        return ((player1.getMonsterAlive() != 0) && (player2.getMonsterAlive() != 0));
    }

    public static List<Integer> IDMonsterAlive1(Player player1, List<Monster> listMonster1){
        List <Integer> IDMonsterAlive = new ArrayList<>();
        for (Monster monster: listMonster1){
            if(monster.isPlayable()){
                IDMonsterAlive.add(monster.getMonsterID());
            }
        }
        return IDMonsterAlive;
    }

    public static List<Integer> IDMonsterAlive2(Player player2, List<Monster> listMonster2){
        List <Integer> IDMonsterAlive = new ArrayList<>();
        for (Monster monster: listMonster2){
            if(monster.isPlayable()){
                IDMonsterAlive.add(monster.getMonsterID());
            }
        }
        return IDMonsterAlive;
    }

    public static void Switch(Player player, int monsterID){
    System.out.println("------SWITCH MONSTER------");
    player.selectMonster(monsterID);
    System.out.println("Let's Go " + player.getMonsterWithID(monsterID).getname() +" !");
}

    public static void SwitchFainted(Player player, Monster monsterTookDamage){
        Scanner sc = new Scanner(System.in);
        if (player.getMonsterAlive() != 0){
            if ((monsterTookDamage.getHP() <= 0) ) {
                try{
                    Thread.sleep(3000);
                    System.out.println(" ");
                    System.out.println("OH NO!");
                    System.out.println(monsterTookDamage.getname() + " is FAINTED");
                    System.out.println(player.getPlayerName() + " melakukan Switch monster! Masukan ID Monster untuk bertarung");
                    player.printPlayerMonster();
                    int switchFainted = sc.nextInt();
                    while ((!IDMonsterAlive1(player, player.getPlayerMonster()).contains(switchFainted)) || (!player.getMonsterWithID(switchFainted).isPlayable()) ){
                        System.out.println("Monster tidak dapat digunakan, silahkan pilih monster yang Playable");
                        switchFainted = sc.nextInt();
                    }
                    Main.Switch(player, switchFainted);
                }
                catch (Exception e){
                     // do nothing
                }
            }
        }
        else if (player.getMonsterAlive() == 0){
            if ((monsterTookDamage.getHP() <= 0) ) {
                try{
                    Thread.sleep(3000);
                    // player.updateMonsterAlive();
                    System.out.println(" ");
                    System.out.println("OH NO!");
                    System.out.println(monsterTookDamage.getname() + " is FAINTED");
                    System.out.println(player.getPlayerName() + " tidak memiliki monster lagi untuk bertarung.");
                    System.out.println(player.getPlayerName() + " kaburrrrr......");
                    Thread.sleep(3000);
                }
                catch (Exception e){
                     // do nothing
                }
            }
        }
    }

    public static void Turn (Player player1, Player player2, List<String[]> effectivityList, int countTurn) throws ExitException
    {
        Scanner sc = new Scanner(System.in);
                
        /**
         * Player 1 Turn
         */
        System.out.println("--------CHANGE TURN--------");
        System.out.println("Giliran " + player1.getPlayerName());

        System.out.println("----SELECT COMMAND----");
        System.out.println("1. Move");
        System.out.println("2. Switch");
        System.out.println("3. Monster List Information");
        System.out.println("4. Exit Game");
        System.out.println(" ");
        System.out.println("Masukan angka yang sesuai untuk memberikan command");
        
        int tempInput = sc.nextInt();
        while (tempInput != 1 && tempInput != 2 && tempInput != 3 && tempInput!=4){
            System.out.println("Input command salah!");
            System.out.println("Masukan angka yang sesuai untuk memberikan command");
            tempInput = sc.nextInt();
        }
        if (tempInput == 4){
            // ExitGame(player1, player2, isExit);
            throw new ExitException("Berhasil keluar dari GAME");
            
        }
        while (tempInput == 3){
            System.out.println("---TURN INFORMATION--");
            System.out.println("Game sedang menjalankan turn ke- " + countTurn);
            System.out.println("---BATTLE MONSTER INFORMATION---");
            player1.printSelectedMonster();
            System.out.println(" ");
            System.out.println("---MONSTER LIST INFORMATION---");
            player1.printPlayerMonster();
            System.out.println(" ");
            System.out.println("Masukkan Command Lainnya");
            System.out.println("----SELECT COMMAND----");
            System.out.println("1. Move");
            System.out.println("2. Switch");
            System.out.println("3. Monster List Information");
            System.out.println("4. Exit Game");
            System.out.println(" ");
            System.out.println("Masukan angka yang sesuai untuk memberikan command");
            tempInput = sc.nextInt();
        }
        Move selectMove1 = new Move("skip");
        if (tempInput == 1){
            player1.printMovesSelectedMonster();
            System.out.println("Masukkan input ID Move yang dipilih");
            int tempSelect = sc.nextInt();
            selectMove1 = player1.getCurrentMonster().getMoveWithID(tempSelect);
            
            while (!player1.getCurrentMonster().checkAvailableMove(selectMove1)){
                System.out.println("Move tidak ditemukan!");
                System.out.println("Pilih move lainnya");
                tempSelect = sc.nextInt();
                selectMove1 = player1.getCurrentMonster().getMoveWithID(tempSelect);
            }
            
            while (selectMove1.getAmmunition() == 0){
                System.out.println("Move tidak dapat digunakan karena Ammunition 0");
                System.out.println("Pilih move lainnya");
                tempSelect = sc.nextInt();
                selectMove1 = player1.getCurrentMonster().getMoveWithID(tempSelect);
            }
            if (selectMove1.getAmmunition()>0){
                player1.getCurrentMonster().getMoveWithID(tempSelect).reduceAmmunition();
            }
        
        }

        else if (tempInput == 2){
            if (player1.getMonsterAlive() > 1){
                int tempIDNewMonster;
                player1.printPlayerMonster();
                System.out.println(" ");
                System.out.println("Switch! masukan ID monster baru yang akan digunakan untuk bertarung");
                tempIDNewMonster = sc.nextInt();
                while (player1.getMonsterWithID(tempIDNewMonster) == player1.getCurrentMonster()){
                    System.out.println("Monster ini sedang digunakan untuk bertarung, pilih monster lainnya");
                    tempIDNewMonster = sc.nextInt();
                }
                while ((!IDMonsterAlive1(player1, player1.getPlayerMonster()).contains(tempIDNewMonster)) || (!player1.getMonsterWithID(tempIDNewMonster).isPlayable()) ){
                    System.out.println("Monster tidak dapat digunakan, silahkan pilih monster yang Playable");
                    tempIDNewMonster = sc.nextInt();
                }
                Main.Switch(player1, tempIDNewMonster);
            }
        }

        /**
         * Player 2 Turn
         */

        System.out.println("--------CHANGE TURN--------");
        System.out.println("Giliran " + player2.getPlayerName());

        System.out.println("----SELECT COMMAND----");
        System.out.println("1. Move");
        System.out.println("2. Switch");
        System.out.println("3. Monster List Information");
        System.out.println("4. Exit Game");
        System.out.println(" ");
        System.out.println("Masukan angka yang sesuai untuk memberikan command");
        
        int tempInput2 = sc.nextInt();
        while (tempInput2 != 1 && tempInput2 != 2 && tempInput2 != 3 && tempInput2 !=4){
            System.out.println("Input command salah!");
            System.out.println("Masukan angka yang sesuai untuk memberikan command");
            tempInput2 = sc.nextInt();
        }

        if (tempInput2 == 4){
            // ExitGame(player1, player2, isExit);
            throw new ExitException("Berhasil keluar dari game");
        }

        while (tempInput2 == 3){
            System.out.println("---TURN INFORMATION--");
            System.out.println("Game sedang menjalankan turn ke- " + countTurn);
            System.out.println("---BATTLE MONSTER INFORMATION---");
            player2.printSelectedMonster();
            System.out.println(" ");
            System.out.println("---MONSTER LIST INFORMATION---");
            player2.printPlayerMonster();
            System.out.println(" ");
            System.out.println("Masukkan Command Lainnya");
            System.out.println("----SELECT COMMAND----");
            System.out.println("1. Move");
            System.out.println("2. Switch");
            System.out.println("3. Monster List Information");
            System.out.println("4. Exit Game");
            System.out.println(" ");
            System.out.println("Masukan angka yang sesuai untuk memberikan command");
            tempInput2 = sc.nextInt();
        }
        Move selectMove2 = new Move("skip");

        if (tempInput2 == 1){
            player2.printMovesSelectedMonster();
            System.out.println("Masukkan input ID Move yang dipilih");
            int tempSelect2 = sc.nextInt();
            selectMove2 = player2.getCurrentMonster().getMoveWithID(tempSelect2);

            while (!player2.getCurrentMonster().checkAvailableMove(selectMove2)){
                System.out.println("Move tidak ditemukan!");
                System.out.println("Pilih move lainnya");
                tempSelect2 = sc.nextInt();
                selectMove2 = player2.getCurrentMonster().getMoveWithID(tempSelect2);
            }
            while (selectMove2.getAmmunition() == 0){
                System.out.println("Move tidak dapat digunakan karena Ammunition 0");
                System.out.println("Pilih move lainnya");
                tempSelect2 = sc.nextInt();
                selectMove2 = player2.getCurrentMonster().getMoveWithID(tempSelect2);
            }
            if (selectMove2.getAmmunition()>0){
                player2.getCurrentMonster().getMoveWithID(tempSelect2).reduceAmmunition();
            }

        }
        else if (tempInput2 == 2){
            int tempIDNewMonster2;
            player2.printPlayerMonster();
            System.out.println(" ");
            System.out.println("Switch! masukan ID monster baru yang akan digunakan untuk bertarung");
            tempIDNewMonster2 = sc.nextInt();
            while (player2.getMonsterWithID(tempIDNewMonster2) == player2.getCurrentMonster()){
                System.out.println("Monster ini sedang digunakan untuk bertarung, pilih monster lainnya");
                tempIDNewMonster2 = sc.nextInt();
            }
            while ((!IDMonsterAlive1(player2, player2.getPlayerMonster()).contains(tempIDNewMonster2)) || (!player2.getMonsterWithID(tempIDNewMonster2).isPlayable()) ){
                System.out.println("Monster tidak dapat digunakan, silahkan pilih monster yang Playable");
                tempIDNewMonster2 = sc.nextInt();
            }
            Main.Switch(player2, tempIDNewMonster2);
        }
        damageCalculation(player1, player1.getCurrentMonster(), selectMove1, player2, player2.getCurrentMonster(), selectMove2, effectivityList);
    }

    public static void onBoardingGame(Player player1, List<Monster> listMonster1, Player player2, List<Monster> listMonster2){
        Scanner sc = new Scanner(System.in);

        player1.setMonsterAlive(player1.CountMonsterAlive());
        player2.setMonsterAlive(player2.CountMonsterAlive());

        int inputCurrentMonster1;
        int inputCurrentMonster2;
        
            System.out.println("Pilih monster untuk bertarung dengan menuliskan ID monster");
            player1.printPlayerMonster();
            System.out.println("Untuk " + player1.getPlayerName() + " ,tuliskan ID monster yang dipilih");
            inputCurrentMonster1 = sc.nextInt();
            
            while ((!IDMonsterAlive1(player1, listMonster1).contains(inputCurrentMonster1)) || (!player1.getMonsterWithID(inputCurrentMonster1).isPlayable())){
                System.out.println("Monster tidak dapat digunakan, coba untuk menggunakan monster yang Playable");
                System.out.println("Ulangi input ID Monster");
                inputCurrentMonster1 = sc.nextInt();
            }
            player1.selectMonster(inputCurrentMonster1);

            System.out.println("Pilih monster untuk bertarung dengan menuliskan ID monster");
            player2.printPlayerMonster();
            System.out.println("Untuk " + player2.getPlayerName() + " ,tuliskan ID monster yang dipilih");
            inputCurrentMonster2 = sc.nextInt();
            
            while ((!IDMonsterAlive1(player2, listMonster2).contains(inputCurrentMonster2)) || !player2.getMonsterWithID(inputCurrentMonster2).isPlayable()){
                System.out.println("Monster tidak dapat digunakan, coba untuk menggunakan monster yang Playable");
                System.out.println("Ulangi input ID Monster");
                inputCurrentMonster2 = sc.nextInt();
            }
            player2.selectMonster(inputCurrentMonster2);
      
    }

    public static void main(String[] args){
        // Read Configuration
        Scanner sc = new Scanner(System.in);
        ReadConfig bacaConfig = new ReadConfig();
        List<Monster> monsterPool = new ArrayList<>();
        monsterPool = bacaConfig.getAllMonster();
        int countTurn = 1;

        Main.openGame();
        System.out.println("----MENU SELECTION----");
        System.out.println("1. START GAME");
        System.out.println("2. HELP");
        System.out.println("3. EXIT GAME");

        int begin = sc.nextInt();
        while (begin != 1 && begin != 2 && begin != 3){
            System.out.println("Masukkan input yang valid");
            System.out.println("----MENU SELECTION----");
            System.out.println("1. START GAME");
            System.out.println("2. HELP");
            System.out.println("3. EXIT GAME");
        }

        if (begin == 3){
            try{
                throw new ExitException("Berhasil keluar dari game");
            }catch (Exception e){
                // do nothing
            }
        }
        else if (begin == 2){
            Main.printHelp();
            System.out.println("----MENU SELECTION----");
            System.out.println("1. START GAME");
            System.out.println("2. HELP");
            System.out.println("3. EXIT GAME");
            begin = sc.nextInt();
        }
        else if (begin == 1){
            System.out.println("----GAME DIMULAI----");
            List<String[]> effectivityList = new ArrayList<>();
            effectivityList = bacaConfig.getEffectivity();
    
            // Bikin Monster
            List<Monster> monster1 = new ArrayList<>();
            List<Monster> monster2 = new ArrayList<>();
    
            while(monster1.size() != 6) {
                int x = (int)Math.floor(Math.random()*(monsterPool.size()-1)+0);
                Monster tempMonster1 = monsterPool.get(x);
                monster1.add(tempMonster1);
                
            }
    
            int y = 1;
            for (int i = 0; i<monster1.size(); i++){
                Monster theMonster = monster1.get(i);
                List<Move> tempListMove1 = new ArrayList<>();
                for(Move tempMove1 : theMonster.getListMove()) {
                    Move tempMoveMonster1 = new Move(tempMove1);
                    tempListMove1.add(tempMoveMonster1);
                }
                Monster momo = new Monster(y, theMonster.getname() , theMonster.getelementTypes(), tempListMove1, theMonster.getHP(), theMonster.getATK(), theMonster.getDEF(), theMonster.getSpATK(), theMonster.getSpDEF(), theMonster.getSpeed());
                monster1.set(i, momo);
                y++;
            }
    
            while(monster2.size() != 6) {
                int x = (int)Math.floor(Math.random()*(monsterPool.size()-1)+0);
                Monster tempMonster2 = monsterPool.get(x);
                monster2.add(tempMonster2);
            }
    
            int w = 1;
            for (int i = 0; i<monster2.size(); i++){
                Monster theMonster = monster2.get(i);
                List<Move> tempListMove2 = new ArrayList<>();
                for(Move tempMove2 : theMonster.getListMove()) {
                    Move tempMoveMonster2 = new Move(tempMove2);
                    tempListMove2.add(tempMoveMonster2);
                }
                Monster momo = new Monster(w, theMonster.getname() , theMonster.getelementTypes(), tempListMove2, theMonster.getHP(), theMonster.getATK(), theMonster.getDEF(), theMonster.getSpATK(), theMonster.getSpDEF(), theMonster.getSpeed());
                monster2.set(i, momo);
                w++;
            }
            System.out.print("Masukkan nama Player 1: ");
            String namePlayer1 = sc.next();
            System.out.print("Masukkan nama Player 2: ");
            String namePlayer2 = sc.next();

            Player player1 = new Player(1, namePlayer1, monster1);
            Player player2 = new Player(2, namePlayer2, monster2);
    
         
            Main.onBoardingGame(player1, monster1, player2, monster2);
            
            try{
                while (isStillBattle(player1, player2)){
                    Main.Turn(player1, player2, effectivityList, countTurn);
                    countTurn++;
                }
                checkTheWinner(player1, player2);
            }
            catch (Exception e){
                e.printStackTrace();
    
            }
        }
        
    }
}
