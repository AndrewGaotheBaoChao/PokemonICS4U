/**
 * PokemonTools.java
 * Pokemon Selection Table Formatting
 * Battle strings formatting
 * Special prints
 */

import java.util.*;

public class PokemonTools {

    private static int boxsize = 144; // Limit the amount of characters printed
    private static String border = new String(new char[boxsize]).replace('\0', '═');
    private static String errorMessage = "Please enter a valid number!"; // Error Message

    /**
     * Static class with static methods to be used in PokemonArena
     *
     * @param lower
     * @param higher
     * @return
     */
    public static int takeInt(int lower, int higher){
        // Lower/higher values are inclusive, i.e. 1,3 -> 1,2,3 are all valid convert string to int, so it doesn't crash each time
        Scanner input = new Scanner(System.in);
        String toInt = input.nextLine().trim();

        try {
            int myOut = Integer.parseInt(toInt);

            if (lower <= myOut && myOut <= higher) {
                return myOut;
            }

            System.out.println(errorMessage);
            return takeInt(lower,higher);
        }
        catch (NumberFormatException e){
            displayText(errorMessage);
            return takeInt(lower,higher); // Recursively calls until a valid integer is given
        }
    }

    /**
     * Draw the pickPokemon() table
     * @param pokeToSee
     */
    public static void displayPokemonStats(Pokemon pokeToSee){
        String [] pokeStats = pokeToSee.getStats().split("\\s+");
        String toDisplay = String.format("HP: %s   ---   Energy: %s",pokeStats[0],pokeStats[1]);
        displayText(String.format("%-12s   ---   ",pokeToSee.getName()) + toDisplay);
    }

    /**
     * Separate stats by .
     *
     * @param counter
     * @param pokeToSee
     */
    public static void displayPokemonStats(int counter, Pokemon pokeToSee){
        System.out.print(counter + ". ");
        displayPokemonStats(pokeToSee);
    }

    /**
     * Display Pokemon Stats
     *
     * @param pokeToSee
     */
    public static void displayPokemon(ArrayList<Pokemon> pokeToSee){
        String toDisplay = "";
        toDisplay += border + "\n";
        // Draw table with stats
        for (int i = 1; i < pokeToSee.size()+1; i++) {
            Pokemon currPoke = pokeToSee.get(i-1);
            toDisplay = toDisplay + String.format("%3d. %-12s   ---  HP: %-4d   ---   Type: %-9s   ---   Weakness: %-9s   ---   Resistance: %-9s",i,currPoke.getName(),currPoke.getHp(),currPoke.getType(),currPoke.getWeakness(),currPoke.getResistance())+"\n";
        }
        toDisplay+= border;
        displayText(toDisplay);
    }

    /**
     *  Display player and rival Pokemon currently active in battle
     *
     * @param myPoke
     * @param opPoke
     */
    public static void displayBattle(Pokemon myPoke, Pokemon opPoke){
        System.out.println(border);
        displayPokemonStats(myPoke);
        displayCenteredText("vs");
        displayPokemonStats(opPoke);
        System.out.println(border);
    }

    /**
     * Display text with ═ on top as a border
     *
     * @param toDisplay
     */
    public static void displayBorderedUpText(String toDisplay){ //
        System.out.println(border); // Displays the top border

        if (!toDisplay.equals("")) {
            // Allows me to pass in something so that it doesn't give a new line
            displayText(toDisplay);
        }
    }

    /**
     * Display text with ═ below as a border
     *
     * @param toDisplay
     */
    public static void displayBorderedDownText(String toDisplay){

        if (!toDisplay.equals("")) {
            // Allows me to pass in something so that it doesn't give a new line
            displayText(toDisplay);
        }

        System.out.println(border); // Displaying the bottom border
    }

    /**
     * Display the available attacks for a specific Pokemon
     *
     * @param attacker
     */
    public static void displayAttacks(Pokemon attacker){

        ArrayList<Pokemon.Attack> attacks = attacker.getAttacks(); // Get attacks from attack class

        // Display attacks
        for (int i = 0; i < attacks.size(); ++i) {
            System.out.printf("%d. %s\n",i+1,attacks.get(i).getName());
        }
    }

    /**
     * println statement that differentiates test printing and actual game printing
     *
     * @param toDisplay
     */
    public static void displayText(String toDisplay){
        System.out.println(toDisplay);
    }

    /**
     * Display centered text based on boxsize (114)
     *
     * @param toDisplay
     */
    public static void displayCenteredText(String toDisplay){

        if (toDisplay.length() <= boxsize) { // Padding is spaces on each side to add to center the text
            String padding = new String(new char[(boxsize-toDisplay.length())/2]).replace('\0', ' ');
            System.out.println(padding + toDisplay + padding);
            return;
        }
        String myL = "";
        System.out.println(myL);
    }
}



