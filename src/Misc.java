/**
 * Misc.java
 * Miscellaneous stuff that will be useful in other .java files
 */

import java.util.*;

public class Misc {

    private static String playerName; // Player's name
    private static String rivalName; // Rival's name
    private static String option; // Yes or No

    /**
     * Professor Oak's speech
     * Sets name for player and rival
     */
    public static void intro (){

        Scanner input = new Scanner(System.in);

        clearScreen();
        delayPrintln("Oak: Hello there! Welcome to the world of POKEMON! My name is OAK! People call me the POKEMON PROF!");
        pause();

        clearScreen();
        delayPrintln("Oak: This world is inhabited by creatures called POKEMON! For some people, POKEMON are pets. Others use them for fights. Myself... I study POKEMON as a profession.");
        pause();

        clearScreen();
        delayPrintln("Oak: First, what is your name?");
        delayPrint(">>> ");

        while (true){ // Prompts user to enter a username then check if it's what they want

            playerName = input.nextLine();

            if (playerName.length() < 1) {
                playerName = "Red"; // If no name is provided
            }

            clearScreen();
            delayPrintln("\nSo your name is " + playerName + "? [y/n]");
            delayPrint(">>> ");
            option = input.nextLine();

            if (option.equalsIgnoreCase("y")){
                break;
            }
            else if (option.equalsIgnoreCase("n")){
                clearScreen();
                delayPrintln("Oak: So, what is your name?");
                delayPrint(">>> ");
                continue;
            }
            else {
                System.out.println("Invalid Input!");
                sleep(1000);
                clearScreen();
                delayPrintln("Oak: So, what is your name?");
                delayPrint(">>> ");
                continue;
            }
        }

        clearScreen();
        delayPrintln("Oak: Right! So your name is " + playerName + "!");
        pause();

        clearScreen();
        delayPrintln("Oak: This is my grandson. He's been your rival since you were a baby. ...Erm, what is his name again?");
        delayPrint(">>> ");

        while (true){ // Prompts user to enter a username then check if it's what they want

            rivalName = input.nextLine();

            if (rivalName.length() < 1) {
                rivalName = "Blue"; // If no name is provided
            }

            clearScreen();
            delayPrintln("\nSo his name is " + rivalName + "? [y/n]");
            delayPrint(">>> ");
            option = input.nextLine();

            if (option.equalsIgnoreCase("y")){
                break;
            }
            else if (option.equalsIgnoreCase("n")){
                clearScreen();
                delayPrintln("Oak: So, what is his name?");
                delayPrint(">>> ");
                continue;
            }
            else {
                System.out.println("Invalid Input!");
                sleep(1000);
                clearScreen();
                delayPrintln("Oak: So, what is his name?");
                delayPrint(">>> ");
                continue;
            }
        }

        clearScreen();
        delayPrintln("Oak: That's right! I remember now! His name is " + rivalName + "!");
        pause();

        clearScreen();
        delayPrintln("Oak: " + playerName + "! Your very own POKEMON legend is about to unfold! A world of dreams and adventures with POKEMON awaits! Let's go!");
        pause();

        clearScreen();
    }

    /**
     * Difficulty Selection
     */
    public static void difficulty (){

        Scanner input2 = new Scanner(System.in);
        String difficultySelected;

        delayPrintln("══════════════════════════\n" +
                (" 1 "+          "EASY") + "\n" +
                (" 2 "+         "MEDIUM") + "\n" +
                (" 3 "+          "HARD") + "\n" +
                "══════════════════════════");

        while (true){
            delayPrintln("\nChoose a difficulty...");
            delayPrint(">>> ");
            option = input2.nextLine(); // Take in user input

            if (option.equals("1")){
                System.out.println("EASY difficulty selected.");
                difficultySelected = "Easy";
                sleep(2000);
                clearScreen();
                break;
            }
            else if (option.equals("2")){
                System.out.println("MEDIUM difficulty selected.");
                difficultySelected = "Medium";
                sleep(2000);
                clearScreen();
                break;
            }
            else if (option.equals("3")){
                System.out.println("HARD difficulty selected.");
                difficultySelected = "Hard";
                sleep(2000);
                clearScreen();
                break;
            }
            else {
                System.out.println("Invalid Input!");
                sleep(1000);
                continue;
            }

        }
    }

    /**
     * Pause the program for a given amount of time
     *
     * @param milliseconds
     */
    public static void sleep (long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints a bunch of text with delay
     *
     * @param multiline
     * @param milliseconds
     */
    public static void delayedLinePrint (String[] multiline, long milliseconds) {
        for (int i = 0; i < multiline.length; i++) {
            System.out.println(multiline[i]);
            sleep(milliseconds);
        }
    }

    /**
     * Prints a single line of text and new line with delay
     *
     * @param print
     */
    public static void delayPrintln(String print) {
        delayPrint(print + "\n", 5);
    }

    /**
     * Prints a single line with delay
     *
     * @param print
     */
    public static void delayPrint(String print) {
        delayPrint(print, 5);
    }

    /**
     * Prints a single line with delay
     *
     * @param print
     * @param micro
     */
    public static void delayPrint(String print, int micro) {
        for (char c : print.toCharArray()) {
            System.out.print(c);

            try {
                Thread.sleep(micro);
            }
            catch (Exception e) {
            }
        }
    }

    /**
     * Pauses the program until player presses enter
     *
     */
    public static void pause() {
        delayPrintln("\nPress enter to continue...");

        try {
            System.in.read();
        }
        catch (Exception e) {
        }
    }

    /**
     * Clears the screen with a bunch of whitespace
     */
    public static void clearScreen () {
        for (int i = 0; i < 100; i++)
            System.out.println();
    }

    /**
     * Prints out pokemon ascii art
     *
     * @param content
     * @param milliseconds
     */
    public static void asciiPrint (String content, long milliseconds) {

        for (int i = 0; i < content.length(); i++) {

            // Don't sleep for spaces
            if (content.charAt(i) == ' ') {
                System.out.print(content.charAt(i));
            } else {
                System.out.print(content.charAt(i));
                sleep(milliseconds);
            }
        }
        System.out.println();
    }

    /**
     * Allows me to add pauses in between events/prints
     *
     * @param time
     */
    public static void sleep (int time){
        try {
            Thread.sleep((long)time);
        }
        catch (InterruptedException error) {
            System.out.println(error);
        }
    }

    /**
     * Get methods for player name and rival name
     *
     * @return
     */
    public static String getPlayerName (){
        return playerName;
    }

    public static String getRivalName (){
        return rivalName;
    }
}
