/**
 * Stats
 */
import java.util.*;
public class Stats <T> {
    private T healthPoint;
    private T fullHP;
    private T attack;
    private T defense;
    private T specialAttack;
    private T specialDefense;
    private T speed;

    /**
     * Konstruktor
     * @param healthPoint
     * @param attack
     * @param defense
     * @param specialAttack
     * @param specialDefense
     * @param speed
     */

    public Stats(T healthPoint, T attack, T defense, T specialAttack, T specialDefense, T speed) {
        //super(nama, elementTypes);
        this.healthPoint= this.fullHP =healthPoint;
        this.attack =attack;
        this.defense =defense;
        this.specialAttack =specialAttack;
        this.specialDefense =specialDefense;
        this.speed =speed;
    }
    

    /**
     * Getter attribute
     * @return attribute
     */

    public T getHP() {
        return this.healthPoint;
    }

    public T getATK() {
        return this.attack;
    }

    public T getDEF() {
        return this.defense;
    }

    public T getSpATK() {
        return this.specialAttack;
    }

    public T getSpDEF() {
        return this.specialDefense;
    }

    public T getSpeed() {
        return this.speed;
    }

    public T getFullHP(){
        return this.fullHP;
    }

    /**
     * Setter nilai stat
     *
     */

    public void setHP(T healthPoint) {
        this.healthPoint = healthPoint;
    }

    public void setATK(T attack) {
        this.attack = attack;
    }

    public void setDEF(T defense) {
        this.defense = defense;
    }

    public void setSpATK(T specialAttack) {
        this.specialAttack = specialAttack;
    }

    public void setSpDEF(T specialDefense) {
        this.specialDefense = specialDefense;
    }

    public void setSpeed(T speed) {
        this.speed = speed;
    }

    public void printStats(){
        System.out.println("Health Point " + getHP());
        System.out.println("Attack " + getATK());
        System.out.println("Defense " + getDEF());
        System.out.println("Special Attack " + getSpATK());
        System.out.println("Special Defense " + getSpDEF());
        System.out.println("Speed " + getSpeed());
    }


}