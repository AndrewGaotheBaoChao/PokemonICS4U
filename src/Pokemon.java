/**
 * Pokemon.java
 * Stats and Special effects of Pokemon,
 * such as name, type, resistance, weakness, HP, energy and attacks
 * Attack method
 */

import java.util.*;

public class Pokemon extends PokemonArena {
    // Data storing variables
    private String name, type, weakness, resistance;
    private int oenergy = 50, ohp = 0, HP = 0, energy = 50, numMoves = 0;
    private boolean stunned = false, disabled = false, missed = false;

    ArrayList<Attack> attacks = new ArrayList <Attack>(); // Attack array

    /**
     * Split pokemon.txt file contents separated by comma
     *
     * @param pokemonString
     */
    public Pokemon(String pokemonString){

        String[] pokemonStats = pokemonString.split(",");
        name = pokemonStats[0];
        HP = Integer.parseInt(pokemonStats[1]);
        ohp += HP;

        // Get Pokemon stats
        for (int i = 2; i < 5; ++i) {
            if (!pokemonStats[i].equals(" ")){
                pokemonStats[i] = (char)(pokemonStats[i].charAt(0)-32) + pokemonStats[i].substring(1);
            }
        }

        type = pokemonStats[2];
        resistance = pokemonStats[3];
        weakness = pokemonStats[4];
        numMoves = Integer.parseInt(pokemonStats[5]);

        // Change all the types so that they have capitals
        for (int i = 0; i < numMoves; ++i) {
            int spot = 6+4*i;
            Attack newAttack = new Attack(pokemonStats[spot],Integer.parseInt(pokemonStats[spot+1]),Integer.parseInt(pokemonStats[spot+2]),pokemonStats[spot+3]);
            attacks.add(newAttack);
        }
    }

    /**
     * If the pokemon has enough energy to use the selected move
     *
     * @param moveNum
     * @return
     */
    public boolean canUseMove(int moveNum){

        if (energy < attacks.get(moveNum).cost) {
            return false; // Not enough energy
        }
        return true; // Enough energy
    }

    /**
     * Uses struggle on defender Pokemon
     *
     * @param attacker
     * @return
     */
    public String struggle(Pokemon attacker){
        Misc.delayPrintln(String.format("\n%s used Struggle!",attacker.getName()));
        Misc.delayPrintln("\nBut nothing happened!");
        Misc.sleep(1000);
        return "";
    }

    /**
     * Main attack method
     * Calculates all specials with effectiveness based on type and total damage
     *
     * @param defender
     * @param moveNum
     * @return
     */
    public String attack(Pokemon defender,int moveNum){ // Uses an attack on defender Pokemon

        Attack move = attacks.get(moveNum); // Get the attack selected

        Misc.delayPrintln("\n"+name+" attacks "+defender.getName()+" using "+move.getName()+"!");
        Misc.sleep(1000);

        // Not enough energy to attack
        if (move.cost > energy) {
            Misc.delayPrintln("\n"+name+" didn't have enough energy to attack!");
            Misc.sleep(1000);
        }

        int baseAttack = move.damage - (disabled? 10:0); // Disabled special effect variable

        // Calculating special effects
        if (move.effects[0]) { // Stun - Pokemon cannot do anything for 1 round
            move.stun(defender);
        }

        int wc = move.wildCard();

        if (move.effects[1]) { // Wildcard - 50/50 chance of attack missing

            if (wc == 0) {
                Misc.delayPrintln("\n"+move.getName()+" missed!");
                missed = true;
                Misc.sleep(1000);
            }
            baseAttack *= wc; // damage times wild card damage
        }

        int ws = move.wildStorm();

        if (move.effects[2]) { // Wildstorm - Pokemon's move is able to attack up to 5 times

            baseAttack *= ws;

            if (ws == 0) {
                Misc.delayPrintln("\n"+move.getName()+" missed!");
                Misc.sleep(1000);
                missed = true;
            }
            else if (ws == 1){
                Misc.delayPrintln("\n"+move.getName()+" hit 1 time!");
                Misc.sleep(1000);
                missed = false;
            }
            else {
                Misc.delayPrintln("\n"+move.getName()+" hit "+ws+" times!");
                Misc.sleep(1000);
                missed = false;
            }
        }

        if (move.effects[3]) { // Disable - Pokemon is unable to do anything and attack power is reduced by 10
            move.disable(defender);
            Misc.delayPrintln("\n"+defender.getName()+" was disabled!");
            Misc.sleep(1000);
        }
        if (move.effects[4]) { // Recharge - Pokemon recovers 20 energy
            recharge(20);
            Misc.delayPrintln("\n"+name+" recovered 20 energy!");
            Misc.sleep(1000);
        }

        double effective = 1; // Super effective and not effective variable

        if (type.equals(defender.resistance) && !missed){ // If defender Pokemon is resistant against the attacker Pokemon
            effective = effective/2;
            Misc.delayPrintln("\nIt's not very effective...");
            missed = false;
            Misc.sleep(1000);
        }

        if (type.equals(defender.weakness) && !missed) { // If defender Pokemon is weak against the attacker Pokemon
            effective = effective*2;
            Misc.delayPrintln("\nIt's super effective!");
            missed = false;
            Misc.sleep(1000);
        }

        if (stunned && !missed){ // Defender Pokemon was stunned
            Misc.delayPrintln("\n"+defender.getName()+" was stunned!");
            Misc.sleep(1000);
            stunned = true;
            missed = false;
        }

        float damage = Math.round(baseAttack*effective); // Calculates total move damage
        int dmg = (int)damage;

        energy -= move.cost; // Reduce attacker Pokemon's energy
        defender.HP -= dmg; // Reduce defender Pokemon's HP

        Misc.delayPrintln("\n"+name+" dealt "+dmg+" damage");
        Misc.sleep(1000);
        return "";
    }

    // Get methods
    public String getName(){
        return name;
    }

    public String toString(){
        return name;
    }

    public int getHp(){
        return HP;
    }

    public void setHp(int amount){
        HP = 0;
    }

    public String getType(){
        return type;
    }

    public String getWeakness(){
        return weakness;
    }

    public String getResistance(){
        return resistance;
    }

    public ArrayList<Attack> getAttacks(){
        return attacks;
    }

    public int getNumMoves(){
        return numMoves;
    }

    public int getEnergy(){
        return energy;
    }

    public String getStats(){
        return ""+HP + " " + energy + " " + (stunned? 1:0) +" "+ (disabled? 1:0);
    }

    public void recharge(int amount){
        energy = energy + amount;

        if (energy > oenergy){
            energy = oenergy;
        }
    }

    public void heal(int amount){

        HP = HP + amount;

        if (HP > ohp) {
            HP = ohp;
        }
    }

    public void resetEnergy(){
        energy = 50;
    }

    public void unstun(){
        stunned = false;
    }

    public void undisable(){
        disabled = false;
    }

    /**
     * Class used for attacks with special effects
     */
    class Attack {

        String name = "";
        int cost = 0;
        int damage = 0;
        //stun, wild card, wild storm, disable, recharge
        boolean [] effects = new boolean[5];

        /**
         * Main special attack method
         *
         * @param name
         * @param cost
         * @param damage
         * @param effect
         */
        public Attack(String name, int cost, int damage, String effect){

            this.name = name;
            this.cost = cost;
            this.damage = damage;

            if (effect.equals("stun")) { // Just in case newer moves can contain multiple effects or more effects, it's easier to change.
                this.effects[0] = true;
            }
            else if (effect.contains("wild card")) {
                this.effects[1] = true;
            }
            else if (effect.contains("wild storm")){
                this.effects[2] = true;
            }
            else if (effect.contains("disable")) {
                this.effects[3] = true;
            }
            else if (effect.contains("recharge")){
                this.effects[4] = true;
            }
        }

        // Get methods
        public String getName(){
            return name;
        }

        public void stun(Pokemon defender){
            defender.stunned = true;
        }

        public int wildCard(){
            Random rand = new Random();
            return rand.nextInt(2);
        }

        public int wildStorm(){
            Random rand = new Random();
            int myOpt = rand.nextInt(2);
            return myOpt == 1? myOpt + wildStorm():0;
        }

        public void disable(Pokemon defender){
            defender.disabled = true;
        }
    }
}