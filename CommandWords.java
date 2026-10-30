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
        validCommands.put("go", CommandWord.GO);
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("help", CommandWord.HELP);
        validCommands.put("eat", CommandWord.EAT); // <--- Vigtigt: "eat" er med her!
        validCommands.put("look", CommandWord.LOOK); // Hvis du også har "look"
    }

    /**
     * Given a command word, return and give its command word enum.
     * @param aString A string.
     * @return The CommandWord corresponding to aString, or UNKNOWN if it's not a valid command.
     */
    public CommandWord getCommandWord(String aString)
    {
        CommandWord command = validCommands.get(aString);
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
    public enum CommandWord
    {
        GO("go"), QUIT("quit"), HELP("help"), EAT("eat"), LOOK("look"), UNKNOWN("?");

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