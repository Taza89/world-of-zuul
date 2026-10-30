import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class CommandWords
{
    // A mapping between a command string and the corresponding CommandWord
    private HashMap<String, CommandWord> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        validCommands.put("go", CommandWord.go);
        validCommands.put("quit", CommandWord.quit);
        validCommands.put("help", CommandWord.help);
        validCommands.put("eat", CommandWord.eat);
        validCommands.put("look", CommandWord.look);
        validCommands.put("back", CommandWord.back);
        validCommands.put("take", CommandWord.take);
        validCommands.put("drop", CommandWord.drop);
        validCommands.put("items", CommandWord.items);
    }

    /**
     * Given a command word, return and give its command word enum.

     * @return The CommandWord corresponding to aString, or UNKNOWN if it's not a valid command.
     */
    public CommandWord getCommandWord(String aWord)
    {
        CommandWord command = validCommands.get(aWord);
        if(command != null) {
            return command;
        }
        return CommandWord.UNKNOWN;
    }

    /**
     * Check whether a given String is a valid command word.
     * @param aString The string to check.
     * @return true if a given string is a valid command, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    /**
     * Print all valid commands to the console.
     */
    public void showAll()
    {
        for(String command : validCommands.keySet()) {
            System.out.print(command + " ");
        }
        System.out.println();
    }

    public enum CommandWord
    {
        go("go"), quit("quit"), help("help"),
        eat("eat"), look("look"),back("back"),
        UNKNOWN("?"),take("take"),
        drop("drop"), items("items"),;

        private final String commandString;

        CommandWord(String commandString)
        {

            this.commandString = commandString;
        }

        public String toString()
        {

            return commandString;
        }
    }
}