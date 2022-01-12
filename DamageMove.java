import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

/** A class for Moves that do damage */
public class DamageMove extends Move {

    /** A number
    // that helps the calculation in effect method */
    private static final int DENOMINATOR_MULTIPLIER = 4; 
    
    /** This variable shows how much damage this move does */
    private final int damage; 

    /** Constructor */ 
    public DamageMove(int energy, String name, int damage, String messageFileName, URL sound) {
        super(energy, name, messageFileName, sound);
        this.damage = damage;
    }
 /** @return the damage of the move */ 
    public int getDamage() {
        return damage;
    }
    
/** The effect method that is overriden from the Move class */
    @Override
    public void effect(Character hero1, Character hero2, double modifier) {

        Scanner myReader = new Scanner(new LineNumberReader(
                new InputStreamReader(Objects.requireNonNull(
                        this.getClass().getResourceAsStream(
                                this.getMessageFileName())), StandardCharsets.UTF_8)));

        System.out.printf("%s used %s.%n", hero1.getName(), this.getName());
        Game.graph.moveImage(hero1);
        makeSound(getSound());
        Game.graph.modifyMes(myReader.nextLine() + hero1.getName() + myReader.nextLine() + this.getName() + ".");

        double tempHP = modifier * (getDamage() * hero1.getTempAttack())
                / (DENOMINATOR_MULTIPLIER * hero2.getTempArmour());
        //Removing HP from player taking damage
        hero2.setTempHP(hero2.getTempHP() - (int) Math.round(tempHP));
        //Removing Energy from player using the move
        hero1.setTempEnergy(hero1.getTempEnergy() - this.getEnergy());
        System.out.printf("%s's HP is now %d. The damage was %d HP.%n%n",
                hero2.getName(), hero2.getTempHP(), (int) Math.round(tempHP));
        Graph.modifyHpLabels(hero2, hero2.getTempHP(),hero2.getHp());

        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Calculating damage

    }

    @Override
    public String toString() {
        return String.format("%s and it's damage is %d",
                super.toString(), getDamage());
    }
}
