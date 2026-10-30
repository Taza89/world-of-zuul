import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application.
 *  "World of Zuul" is a very simple, text based adventure game.
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game. It also evaluates and
 *  executes the commands that the parser returns.
 *
 *  @author  Michael Kölling and David J. Barnes
 *  @version 2016.02.29
 */
public class Game
{
    private Parser parser;
    private Player player; // Erstatter den gamle currentRoom variabel
    private Stack<Room> roomHistory; // Holder styr på tidligere rum til 'back'-kommandoen

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
        roomHistory = new Stack<>();
    }

    /**
     * Create all the rooms and link their exits together.
     * Opretter også rummenes ting (items).
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;

        // Opret rummene
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        // Tilføj items til rummene vha. addItem-metoden
        outside.addItem("rock", new Item("a heavy rock", 10));
        theater.addItem("notebook", new Item("a notebook", 2));
        pub.addItem("soda", new Item("a bottle of soda", 1));
        lab.addItem("keyboard", new Item("a broken keyboard", 3));
        office.addItem("pen", new Item("a golden pen", 1));

        // Initialiser rummenes udgange
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        // Opret spilleren: starter 'outside' med en max-vægt på 15 kg
        player = new Player("Hero", outside, 15);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

       CommandWords.CommandWord commandWord = CommandWords.CommandWord.valueOf(command.getCommandWord());

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case help:
                printHelp();
                break;

            case go:
                goRoom(command);
                break;

            case back:
                back(command);
                break;

            case look:
                printLocationInfo();
                break;

            case take:
                take(command);
                break;

            case drop:
                drop(command);
                break;

            case items:
                System.out.println(player.getInventoryString());
                break;

            case quit:
                wantToQuit = quit(command);
                break;
        }

        return wantToQuit;
    }

    // ==========================================
    // USER COMMANDS
    // ==========================================

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to go in to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            roomHistory.push(player.getCurrentRoom()); // Gem det forrige rum på stakken
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }

    /**
     * Gå tilbage til det forrige rum ved hjælp af stakken.
     */
    private void back(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
            return;
        }

        if(roomHistory.isEmpty()) {
            System.out.println("You cannot go back any further!");
        }
        else {
            player.setCurrentRoom(roomHistory.pop());
            printLocationInfo();
        }
    }

    /**
     * Tag et item op fra det nuværende rum.
     */
    private void take(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = player.getCurrentRoom().removeItem(itemName);

        if(item == null) {
            System.out.println("That item is not here.");
        } else {
            boolean success = player.takeItem(itemName, item);
            if(success) {
                System.out.println("You pick up the " + itemName + ".");
            } else {
                // Hvis spilleren ikke kan bære det pga. vægt, lægges tingen tilbage i rummet
                player.getCurrentRoom().addItem(itemName, item);
                System.out.println("It is too heavy to carry.");
            }
        }
    }

    /**
     * Smid et item fra inventaret ud i det nuværende rum.
     */
    private void drop(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = player.dropItem(itemName);

        if(item == null) {
            System.out.println("You are not carrying that item.");
        } else {
            player.getCurrentRoom().addItem(itemName, item);
            System.out.println("You drop the " + itemName + ".");
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really want to quit.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // indicate that we want to quit
        }
    }

    /**
     * Udskriv information om det nuværende rum (inkl. udgange og ting).
     */
    private void printLocationInfo()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
}