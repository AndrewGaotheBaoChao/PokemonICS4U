/**
 * PokemonArena.java
 * Andrew Gao
 * Started on 2017-11-24 –– Finished on 2018-1-8
 *
 * Pokemon Arena is a battle game in which the user chooses six Pokemon from 151 different Pokemon
 * and has to use them to defeat the 6 Pokemon in which your rival picks.
 * If they do so, they win and are featured in the Hall of Fame.
 * The game follows a round based system, in which the user's pokemon and opponent's pokemon take
 * turns to either attack, retreat or pass until either the user or opponent's pokemon faints.
 */

import java.util.*;
import java.io.*;

public class PokemonArena {

    private static ArrayList<Pokemon> allPokemon = new ArrayList<Pokemon>(); // list for all available pokemon
    private static ArrayList<Pokemon> playerPokemon = new ArrayList<Pokemon>(); // list for player's current pokemon
    private static ArrayList<Pokemon> enemyPokemon = new ArrayList<Pokemon>(); // list for opponent's pokemon
    private static ArrayList<Pokemon> faintedPokemon = new ArrayList<Pokemon>(); // list for fainted pokemon

    /**
     * Main method
     * Displays Pokemon Logo
     * Initial Sequence
     * Handles all battles
     *
     * @param args
     */
    public static void main(String[] args){

        Graphics.startTitle(); // Pokemon logo
        //Misc.intro(); // Professor Meme's long speech
        //Misc.difficulty(); // Select difficulty
        load(); // Load the pokemon.txt file
        pickPokemon(); // Select your 6 Pokemon

        int roundNum = 1; // Round counter variable

        // Battle Handler
        while (playerPokemon.size() != 0 && enemyPokemon.size() !=0){
            if(round(roundNum++)){
                break;
            }
            reset();
        }
        if (playerPokemon.size() == 0){ // When all of user's pokemon faints
            Graphics.lose(); // Display you lose ASCII
        }
        else if (enemyPokemon.size() == 0){ // When all of opponent's pokemon faints
            Graphics.win(); // Display you win ASCII
        }
        else { // You Quit
            System.out.println("\nYou fled the battle dropping all of your money...");
        }
    }

    /**
     * Loads the pokemon.txt file which contains all the Pokemon and their attributes
     */
    private static void load(){

        try { // Try loading the file
            Scanner pokemonFile = new Scanner(new BufferedReader(new FileReader("src/pokemon.txt")));

            int numPokemon = Integer.parseInt(pokemonFile.nextLine()); // Read the data

            for (int i = 0; i < numPokemon; ++i){ // Add the Pokemons to the allPokemon array
                Pokemon tmp = new Pokemon(pokemonFile.nextLine()+" ,");
                allPokemon.add(tmp);
            }
        }
        catch (IOException e) { // Couldn't load pokemon.txt file
            Misc.delayPrint("Pokemon.txt not found!",20);
        }
    }

    /**
     * Loads all the ascii art files for all the Pokemon and allows me to print their art whenever I call this method
     *
     * @param name
     */
    public static void printPokemon(String name) {

        Scanner inFile = null; // Scanner variable

        try { // Try to load ascii image files
            inFile = new Scanner(new BufferedReader(new FileReader(String.format("src/images/%s.txt", name))));
        }
        catch (IOException e) { // Couldn't load the art files
            Misc.delayPrint(String.format("Image for %s not found!", name), 20);
        }

        while (inFile.hasNextLine()) {
            String art = inFile.nextLine(); // Read the data
            Misc.asciiPrint(art, 0); // Store it in the art string
        }
        inFile.close(); // Close file
    }

    /**
     * Unstuns and gives 10 energy to Pokemon at the end of each round
     */
    private static void recharge(){

        playerPokemon.get(0).unstun();

        for (Pokemon alive : playerPokemon) {
            alive.recharge(10);
        }
        for (Pokemon alive : enemyPokemon) {
            alive.recharge(10);
        }
    }

    /**
     * Heals 20HP to Pokemon at the end of each round
     */
    private static void heal(){

        for (Pokemon alive : playerPokemon) {
            alive.heal(20);
        }
        for (Pokemon alive : enemyPokemon) {
            alive.heal(20);
        }
    }

    /**
     * Attack sequence for player
     * Displays a menu where all possible attacks can be selected
     * Checks if Pokemon has enough energy to use that attack
     *
     * @param attacker
     * @param defender
     */
    private static void attack(Pokemon attacker,Pokemon defender){ // Attack System

        PokemonTools.displayBorderedUpText("Select an attack:");
        PokemonTools.displayAttacks(attacker); // Display attacks
        PokemonTools.displayBorderedDownText(String.format("%d. Back",attacker.getNumMoves()+1));

        int myAttack = PokemonTools.takeInt(1,attacker.getNumMoves()+1); // Attack selection

        // If selection if valid
        if (myAttack == attacker.getNumMoves()+1) {
            pickAction();
        }
        // Checks if Pokemon has enough energy to use attack
        else {
            if (attacker.canUseMove(myAttack-1)){
                System.out.println(attacker.attack(defender,myAttack-1));
            }
            else {
                System.out.println("Not Enough Energy!");
                attack(attacker,defender); // Allows them to reselect a move
            }
        }
    }

    /**
     * Swapping Pokemon
     * Allows player to switch to another Pokemon in party whenever they want
     * Checks if Pokemon has enough energy to use that attack
     */
    private static void retreat() { // Swap Pokemon

        PokemonTools.displayBorderedUpText("Select a Pokemon to switch to");

        for (int i = 1; i < playerPokemon.size(); ++i) { // Display user's Pokemon Stats
            PokemonTools.displayPokemonStats(i,playerPokemon.get(i));
        }
        for (int i = 0; i < faintedPokemon.size(); ++i) { // Display user's fainted Pokemon
            System.out.print("-. ");
            PokemonTools.displayPokemonStats(faintedPokemon.get(i));
        }

        PokemonTools.displayText(String.format("%d. Back",playerPokemon.size()));
        PokemonTools.displayBorderedDownText("");

        int myOpt = PokemonTools.takeInt(1,playerPokemon.size()); // Get the number of Pokemon

        // Go back to selection menu
        if (myOpt == playerPokemon.size()) {
            pickAction();
        }
        // Switch Pokemon based on user input
        else {
            Pokemon selected = playerPokemon.get(myOpt);
            PokemonTools.displayText(String.format(Misc.getPlayerName()+" : %s, I Choose You!",selected.getName()));
            Misc.sleep(1000);
            printPokemon(selected.getName());

            Pokemon swapOut = playerPokemon.get(0);

            playerPokemon.remove(myOpt);
            playerPokemon.remove(0);
            playerPokemon.add(0,selected);
            playerPokemon.add(myOpt,swapOut);
        }
    }

    /**
     * Pass option
     * Does nothing except print to console
     */
    private static void pass(){ // Pass turn and do nothing
        PokemonTools.displayText(String.format("%s Passed Their Turn!",playerPokemon.get(0)));
    }

    /**
     * Computer's turn to attack or pass based on energy
     * Random attacks
     * If they don't have enough energy, struggle
     */
    private static void enemyTurn(){

        ArrayList<Integer> enemyOptions = new ArrayList<Integer>(); // List of attacks in enemy Pokemon's array

        for (int i = 0; i < enemyPokemon.get(0).getNumMoves(); i++) { // Gets the attack of Pokemon
            enemyOptions.add(i);
        }

        Collections.shuffle(enemyOptions); // Randomize the attacks

        // Check to see if they can use that attack
        for (Integer move : enemyOptions) {
            if (enemyPokemon.get(0).canUseMove(move)) {
                Misc.delayPrintln(enemyPokemon.get(0).attack(playerPokemon.get(0),move));
                return;
            }
        }
        playerPokemon.get(0).struggle(enemyPokemon.get(0)); // Struggle
    }

    /**
     * Determines if player or rival gets to go first each round based of round method
     *
     * @param first
     */
    private static int battle(int first){

        PokemonTools.displayBattle(playerPokemon.get(0),enemyPokemon.get(0)); // Display Pokemon currently active in battle
        Misc.pause();

        if (first == 1){ // Rival goes first
            enemyTurn();
            checkPlayerTurn();
        }

        if (pickAction()){
            return 0; // Quit
        }
        if (checkEnemyTurn()){
            return 1; // Switch pokemon for enemy
        }
        if (first == 0){ // Player goes first
            enemyTurn();
            checkPlayerTurn(); // Nothing is done since the enemyPokemon doesn't switch
        }
        recharge();
        heal();
        return 2; // Nothing happens
    }

    /**
     * Checks if the player Pokemon is fainted
     * If so, goto swapPokemon method to choose next Pokemon to send out
     */
    private static boolean checkPlayerTurn(){

        if (playerPokemon.get(0).getHp() <= 0) {

            Pokemon fainted = playerPokemon.get(0);
            playerPokemon.remove(0); // Remove fainted Pokemon from party

            Misc.delayPrintln(fainted+" fainted!");
            Misc.sleep(1000);
            Misc.clearScreen();

            fainted.setHp(0); // Set HP to 0 so it doesn't have a negative value
            faintedPokemon.add(fainted); // Add fainted Pokemon to array

            // Allow user to choose Pokemon to send out
            PokemonTools.displayPokemon(playerPokemon);
            int myOpt = PokemonTools.takeInt(1,playerPokemon.size())-1;
            swapPokemon(myOpt);
            return true;
        }
        return false; // Pokemon is still alive
    }

    /**
     * Check if enemy Pokemon is fainted
     * If so, send out the next Pokemon in enemyPokemon array
     * If rival has no Pokemon left to battle, you win
     */
    private static boolean checkEnemyTurn(){ // If enemy's Pokemon faints

        if (enemyPokemon.get(0).getHp() <= 0) {

            Pokemon fainted = enemyPokemon.get(0);
            enemyPokemon.remove(0); // Remove fainted Pokemon from party

            Misc.delayPrintln(fainted+" fainted!");

            Misc.sleep(1000);
            Misc.clearScreen();

            // While rival still has Pokemon left
            if (enemyPokemon.size() != 0) {

                Misc.delayPrintln("\n" + Misc.getRivalName() + " sent out " + enemyPokemon.get(0) + "!");

                Misc.sleep(1000);
                printPokemon(enemyPokemon.get(0).getName());
                Misc.sleep(1500);
                Misc.clearScreen();
            }
            return true; // Rival has Pokemon left
        }
        return false; // Rival has no Pokemon left
    }

    /**
     * Reset stats for player Pokemon when they defeat one of rival's Pokemon
     */
    private static void reset(){

        for (Pokemon alive : playerPokemon) {
            alive.resetEnergy();
            alive.unstun();
            alive.undisable();
        }
    }

    /**
     * Takes in integer value to allow switching of Pokemon for player
     *
     * @param myOpt
     */
    private static void swapPokemon(int myOpt){

        // Starting Pokemon
        if (myOpt == 0) {
            Misc.clearScreen();
            Misc.delayPrintln(String.format(Misc.getPlayerName()+": %s, I Choose You!",playerPokemon.get(myOpt).getName()));
            Misc.sleep(1500);
            printPokemon(playerPokemon.get(myOpt).getName());
            return;
        }

        Pokemon selected = playerPokemon.get(myOpt); // Get user input
        Pokemon swapOut = playerPokemon.get(0); // Swap the Pokemon currently in battle

        // Put battle active Pokemon back into party, add Pokemon to swap out to battle
        playerPokemon.remove(myOpt);
        playerPokemon.remove(0);
        playerPokemon.add(0,selected);
        playerPokemon.add(myOpt,swapOut);

        Misc.clearScreen();
        Misc.delayPrintln(String.format(Misc.getPlayerName()+": %s, I Choose You!",selected.getName()));
        Misc.sleep(1500);
        printPokemon(selected.getName());
    }

    /**
     * Allows user to send out a Pokemon to start battling
     */
    private static void selectStarter(){

        PokemonTools.displayCenteredText("Choose who you want to start the battle with:");
        PokemonTools.displayPokemon(playerPokemon);
        int myOpt = PokemonTools.takeInt(1,playerPokemon.size())-1;
        swapPokemon(myOpt);
    }

    /**
     * Random number generator to determine who goes first in battle
     *
     * @param num
     */
    private static boolean round(int num){

        Random rand = new Random();

        Misc.sleep(1000);
        PokemonTools.displayCenteredText(String.format("Battle %d",num));
        selectStarter();

        int first = rand.nextInt(2); // If result = 0 then player goes first, rival goes first if result = 1

        while (true){

            int result = battle(first); // Result of randomly generated number

            if (result == 0){
                return true; // Player goes first
            }
            else if (result == 1){
                break; // Rival goes first
            }
        }
        return false;
    }

    /**
     * Main menu to select action for player
     */
    private static boolean pickAction(){

        Misc.clearScreen();
        PokemonTools.displayBorderedUpText(String.format("%s is awaiting your directions...", playerPokemon.get(0)));
        PokemonTools.displayBorderedDownText(String.format("\n1. Attack\n2. Retreat\n3. Pass\n4. Quit"));

        int myOpt = PokemonTools.takeInt(1,6); // Take user input

        if (myOpt == 1){
            attack(playerPokemon.get(0),enemyPokemon.get(0)); // Goto attack menu
        }
        else if (myOpt == 2){
            retreat(); // Goto swap Pokemon menu
        }
        else if (myOpt == 3){
            pass(); // Does nothing
        }
        else if (myOpt == 4){
            return true; // Quit
        }
        return false;
    }

    /**
     * Allows user to select their 6 Pokemon
     * Prints Pokemon with their number, name, HP, type, weakness and weakness
     * Selects 6 random Pokemon for rival
     */
    private static void pickPokemon(){ // Select 6 Pokemon

        Scanner input = new Scanner(System.in);
        System.out.printf("Choose 6 Pokemon!\n", allPokemon.size());
        Misc.sleep(2000);

        //PokemonTools.displayPokemon(allPokemon);

        int pokeNum; // Pokemon's Number in Data Table
        int PokemonNum = 6; // Max pokemon allowed

        while (true) {

            PokemonTools.displayPokemon(allPokemon);

            System.out.printf("Enter the number corresponding to the Pokemon you want [%d Remaining...]\n", PokemonNum);
            Misc.delayPrint(">>> ");

            try {
                pokeNum = input.nextInt(); // User's input
            }
            catch (InputMismatchException var5) { // If user's input is not a number of a pokemon
                pokeNum = -1;
                input.next();
            }

            if (pokeNum <= allPokemon.size() && pokeNum >= 1) {

                if (!playerPokemon.contains(allPokemon.get(pokeNum - 1))) {
                    playerPokemon.add(allPokemon.get(pokeNum - 1)); // Add selected Pokemon to playerPokemon array
                    --PokemonNum; // Remove the option to select that Pokemon

                    Misc.clearScreen();
                    printPokemon(playerPokemon.get(6-PokemonNum-1).getName()); // Print Pokemon ASCII Art
                    Misc.sleep(500);

                    System.out.printf("You chose %s!\n\n\n", (playerPokemon.get(6 - PokemonNum - 1)).getName());
                    Misc.sleep(1000);
                }
                // User already selected that Pokemon
                else {
                    System.out.printf("You've already chosen %s. Please choose another Pokemon.\n\n\n", ((Pokemon)allPokemon.get(pokeNum - 1)).getName());
                    Misc.sleep(2000);
                    Misc.clearScreen();
                }
            }
            // Invalid option
            else {
                System.out.printf("That's not a valid Pokemon. Please enter a number between 1 and %d.\n\n\n", allPokemon.size());
                Misc.sleep(2000);
                Misc.clearScreen();
            }

            // User selected 6 Pokemon
            if (PokemonNum == 0) {
                Misc.clearScreen();
                break;
            }
        }

        // Display the 6 Pokemon that user selected
        PokemonTools.displayCenteredText("Your Team:");
        PokemonTools.displayPokemon(playerPokemon);

        Random rand = new Random(); // Random number variable

        // Add 6 random Pokemon for rival
        while (allPokemon.size() > 0 && enemyPokemon.size() < 6){

            int addPoke = rand.nextInt(allPokemon.size());

            enemyPokemon.add(allPokemon.get(addPoke)); // Add Pokemon to enemyPokemon array
            allPokemon.remove(addPoke); // Remove added Pokemon from allPokemon array
        }

        Collections.shuffle(enemyPokemon); // Randomize order of rival's Pokemon

        Misc.delayPrintln("\nLet the Battle Begin!");
        Misc.pause();
        Misc.clearScreen();

        Misc.delayPrintln("\n"+Misc.getRivalName()+" sent out "+enemyPokemon.get(0)+"!");
        Misc.sleep(1000);
        printPokemon(enemyPokemon.get(0).getName());
        Misc.sleep(1500);
        Misc.clearScreen();
    }
}