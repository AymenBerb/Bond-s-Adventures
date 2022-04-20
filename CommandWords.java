import java.util.HashMap;
/**
 * This class is part of the James Bond application.
 *
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Aymen Berbache, Michael Kölling and David J. Barnes
 * @version 2021.11.28
 */

public class CommandWords {

    // a constant Map that holds all valid command words and their description
    private static final HashMap<String,String> commandWords = new HashMap<>();

    static {
        commandWords.put("go","Allows you to navigate through the rooms.");
        commandWords.put("quit","Allows you to leave the game.");
        commandWords.put("help","Displays some help statements.");
        commandWords.put("explore","Displays all items available in the room you want to explore.");
        commandWords.put("grab","Allows you to grab an item and put it in your inventory.");
        commandWords.put("throw","Allows you to get rid of an item in your inventory.");
        commandWords.put("show","Displays the content of your inventory.");
        commandWords.put("back","Takes you in the room you were in previously.");
        commandWords.put("weight","Displays the weight of an item.");
        commandWords.put("open","Try to open a locked door.");
        commandWords.put("kill","Kills the character you are interacting with.");
        commandWords.put("give","Give item to a character. (give 'character' 'item')");
    }

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
        //Nothing...
    }


    /**
     * Check whether a given String is a valid command word.
     *
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString) {
        for (int i = 0; i < commandWords.size(); i++) {
            if (commandWords.keySet().toArray()[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() {
        System.out.printf("%-20s%s","Command","Description");
        System.out.println();
        for (String command : commandWords.keySet()) {
            System.out.printf("%-20s%s\n",command, commandWords.get(command));
        }
        System.out.println();
    }
}
