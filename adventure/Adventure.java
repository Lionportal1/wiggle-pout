package adventure; // Defines the package for the Adventure class

import java.util.Scanner; // Imports Scanner class for user input
import java.io.BufferedReader; // Imports BufferedReader for reading files
import java.io.FileReader; // Imports FileReader to read files
import java.io.IOException; // Imports IOException for handling input/output exceptions

public class Adventure 
{ // Defines the main class Adventure

    public static Scanner s = new Scanner(System.in); // Creates a Scanner object for reading user input
    
    public static void main(String[] args) 
    {
        int maxX = 10; // Maximum X dimension of the map
        int maxY = 10; // Maximum Y dimension of the map
        int maxItems = 20; // Maximum number of items
        int maxPlayers = 20; // Maximum number of players

        String mapfile = "map.csv"; // File name for the map data
        String itemfile = "items.csv"; // File name for item data
        String playerfile = "npcs.csv"; // File name for NPC data
        String winfile = "win.csv"; // File name for win condition

        boolean playing = true;
        boolean alive = true;

        MapBlock[][] map = new MapBlock[maxX][maxY]; // Initializes a 2D array of MapBlock
        Item[] items = new Item[maxItems]; // Array to store items in the game
        Player[] npcs = new Player[maxPlayers]; // Array to store NPCs in the game

        init(mapfile, map); // Initialize the map
        int playerCount = init(playerfile, npcs); // Initialize NPCs
        int itemCount = init(itemfile, items, map, npcs); // Initialize items
        initWin(winfile); // Initialize the win condition

        Player p = npcs[0]; // Sets the first player in the NPC array as the main player

        // Main game loop
        while (playing) {
            if (show(map[p.xpos][p.ypos], items, npcs, playerCount) > 0) {
                combat(playerCount, npcs, p);
            }
            alive = checkAlive(p);
            if (alive) {
                move(map, items, p, itemCount, npcs, playerCount); // Handle player actions
                moveNpcs(map, npcs, playerCount); // Handle NPC actions
            } else {
                playing = false;
                System.out.println("You are dead. Enjoy the afterlife.");
            }
        }

        System.out.println("Goodbye");
    }
    
    
    
    
    public static String[] winCondition = new String[4];

    public static void initWin(String winFile) 
    {
        String splitBy = ",";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(winFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                winCondition[0] = data[0]; // xpos
                winCondition[1] = data[1]; // ypos
                winCondition[2] = data[2]; // itemIndex
                winCondition[3] = data[3]; // winMessage
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static boolean checkAlive(Player p) 
    {
		// TODO Auto-generated method stub
		boolean alive = true;
		
		if(p.health <= 0)
		{
			alive = false;
		}
    	
    	
    	
    	return alive;
	}

	private static void combat(int playerCount, Player[] npcs, Player p) 
	{
		// TODO Auto-generated method stub
		int rfi = roll(20);
		if(rfi <= 10)
		{
			for(int i = 1; i < playerCount; i++)
			{
				if(npcs[i].combative == 1)
				{
					if ((npcs[i].xpos == p.xpos) && (npcs[i].ypos == p.ypos))
					{
						int hit = roll(20);
						if (hit > p.defense)
						{
							//hits
							int damage = roll(npcs[i].strength);
							p.health -= damage;
							System.out.println(npcs[i].title + " hits with " + damage + " damage. Health " + p.health);
							
							
						}
						else
						{
							System.out.println(npcs[i].title + " swings and misses");
							
						}
					}
					
					
				}
				
			}
			
			
			
		}
		
		
		
		
		
	}

	private static int roll(int sides) 
	{
		// TODO Auto-generated method stub
		return getRand(1,sides);
	}

	private static void moveNpcs(MapBlock[][] map, Player[] npcs, int playerCount) 
    {
        // Handles NPC movement on the map
        
        for(int i = 1; i < playerCount; i++) { // Loops through all NPCs
            if(npcs[i].moveable == 1) 
            { // Checks if the NPC can move
                switch(getRand(1, 4)) 
                { // Randomly chooses a direction for the NPC
                    case 1: // Move east if there’s no wall
                        if(!map[npcs[i].xpos][npcs[i].ypos].isWall('e')) 
                        {
                            npcs[i].xpos++;
                        }
                        break;
                    case 2: // Move west if there’s no wall
                        if(!map[npcs[i].xpos][npcs[i].ypos].isWall('w')) 
                        {
                            npcs[i].xpos--;
                        }
                        break;
                    case 3: // Move north if there’s no wall
                        if(!map[npcs[i].xpos][npcs[i].ypos].isWall('n')) 
                        {
                            npcs[i].ypos--;
                        }
                        break;
                    case 4: // Move south if there’s no wall
                        if(!map[npcs[i].xpos][npcs[i].ypos].isWall('s')) 
                        {
                            npcs[i].ypos++;
                        }
                        break;
                }
            }
        }
    }

    public static int getRand(int min, int max) 
    { // Generates a random number between min and max
        if (min >= max) 
        { // Checks if min is greater than or equal to max
            System.out.println("Range Error: min greater than max"); // Error message
            return -1; // Returns -1 to indicate an error
        }
        int r = (int)(Math.random() * ((max - min) + 1)) + min; // Generates a random integer in range
        return r; // Returns the random integer
    }

    private static int init(String playerfile, Player[] npcs) 
    {
        // Initializes players (NPCs) from the playerfile
        String splitBy = ","; // Specifies CSV separator
        String line; // Variable for each line in file
        int playerCount = 0; // Counter for number of players initialized
        
        try 
        {
            FileReader fr = new FileReader(playerfile); // Opens playerfile for reading
            BufferedReader br = new BufferedReader(fr); // Buffers the file reader
            
            while((line = br.readLine()) != null) 
            { // Reads each line from playerfile
                String[] data = line.split(splitBy); // Splits line by comma
                
                npcs[playerCount] = new Player(); // Creates a new Player object for each line
                
                npcs[playerCount].xpos = Integer.parseInt(data[0]); // Sets X position
                npcs[playerCount].ypos = Integer.parseInt(data[1]); // Sets Y position
                npcs[playerCount].title = data[2]; // Sets title of NPC
                npcs[playerCount].desc = data[3]; // Sets description of NPC
                npcs[playerCount].moveable = Integer.parseInt(data[4]); // Sets if NPC is moveable
                npcs[playerCount].combative = Integer.parseInt(data[5]);
                npcs[playerCount].defense = Integer.parseInt(data[6]);
                npcs[playerCount].health = Integer.parseInt(data[7]);
                npcs[playerCount].strength = Integer.parseInt(data[8]);
                npcs[playerCount].dexterity = Integer.parseInt(data[9]);
                npcs[playerCount].intelligence = Integer.parseInt(data[10]);
                
                
                playerCount++; // Increments player count
            }
            br.close(); // Closes the buffered reader
        } catch (IOException e) { // Catches and handles file errors
            e.printStackTrace(); // Prints error details
        }
        
        return playerCount; // Returns total number of players loaded
    }

    public static void move(MapBlock[][] map, Item[] items, Player p, int itemCount, Player[] npcs, int playerCount) 
    {
        // Handles player movement and actions
        String choice = ""; // Stores the player's movement choice
        String line = prompt();
        
       
        String[] data = line.split(" ", 2); // Splits input line by first space
        choice = data[0]; // Sets choice to first word
        
        // Switch to handle different movement or action choices
        switch(choice) 
        {
            case "e": case "E": case "east": case "East": // Move east
                if(map[p.xpos][p.ypos].isWall('e')) {
                    System.out.println("There is a wall there, dude. Try Again");
                } 
                else 
                {
                    p.xpos++;
                }
                break;
            case "w": case "W": case "west": case "West": // Move west
                if(map[p.xpos][p.ypos].isWall('w')) {
                    System.out.println("There is a wall there, dude. Try Again");
                } 
                else 
                {
                    p.xpos--;
                }
                break;
            case "n": case "N": case "north": case "North": // Move north
                if(map[p.xpos][p.ypos].isWall('n')) {
                    System.out.println("There is a wall there, dude. Try Again");
                } 
                else 
                {
                    p.ypos--;
                }
                break;
            case "s": case "S": case "south": case "South": // Move south
                if(map[p.xpos][p.ypos].isWall('s')) {
                    System.out.println("There is a wall there, dude. Try Again");
                } 
                else 
                {
                    p.ypos++;
                }
                break;
            case "get": case "Get": case "g": case "G": // Get item
                if(map[p.xpos][p.ypos].itemsCount == 0) 
                {
                    System.out.println("I see nothing...");
                } 
                else 
                {
                    String stuff = data[1];
                    int itemIdx = find(items, itemCount, stuff); // Finds item index by name
   System.out.println("itemIdx: " + itemIdx);                 
                    int itemCheck = findByIndex(itemIdx, map[p.xpos][p.ypos]); // Checks if item is in current position
   System.out.println("itemCheck: " + itemCheck);                 
                                     
                    if((itemCheck >= 0) && (itemIdx >= 0)) 
                    {
                        pickup(itemIdx, p, items); // Picks up the item
                        System.out.println("You now have the " + stuff + ".");
                        remove(itemIdx, map[p.xpos][p.ypos]); // Removes item from map
                    } 
                    else 
                    {
                        System.out.println("Umm... There is no " + stuff + " here.");
                    }
              
                }
                break;
            	case "drop": case "Drop": case "d": case "D": // drop item
                if(p.itemCount == 0) 
                {
                    System.out.println("I have nothing...");
                } 
                else 
                {
                    String stuff = data[1];
                    
                    int itemIdx = find(items, itemCount, stuff); // Finds item index by name
                    int itemCheck = findByIndex(itemIdx, p); // Checks if item is in inventory 
                    
                    if((itemCheck >= 0) && (itemIdx >= 0)) 
                    {
                        place(itemIdx, map[p.xpos][p.ypos]); // drops up the item
                        System.out.println("You dropped the " + stuff + ".");
                        drop(itemIdx, p, items); // Removes item from player
                    } 
                    else 
                    {
                        System.out.println("Umm... There is no " + stuff + " here.");
                    }
              
                }
                break;
            	case "attack": case "Attack": case "a": case "A": // attack
                    attack(npcs, playerCount, p, items, map[p.xpos][p.ypos]);
                    break;
            	case "inventory": case "Inventory": case "i": case "I": // Inventory
                    System.out.println("You are carrying: ");
            		for(int i = 0; i < p.itemCount; i++)
                    {
                    	System.out.println(items[p.items[i]].title);
                    }
                    break;
            	case "?": case "help": case "h": case "H": case "Help":
            	    System.out.println("Available Commands:");
            	    System.out.println("e/w/n/s - Move east/west/north/south");
            	    System.out.println("g [item] - Get an item");
            	    System.out.println("d [item] - Drop an item");
            	    System.out.println("a - Attack");
            	    System.out.println("i - Show inventory");
            	    System.out.println("steal - Attempt to steal an item from an NPC");
            	    System.out.println("?/h/help - Show this list");
            	    break;
            	case "steal": case "Steal":
            	    boolean stolen = false;
            	    for (int i = 1; i < playerCount; i++) {
            	        if (npcs[i].xpos == p.xpos && npcs[i].ypos == p.ypos) {
            	            int rollResult = roll(10) + p.dexterity;
            	            if (rollResult > npcs[i].intelligence && npcs[i].itemCount > 0) {
            	                int lastItemIdx = npcs[i].items[npcs[i].itemCount - 1];
            	                pickup(lastItemIdx, p, items);
            	                drop(lastItemIdx, npcs[i], items);
            	                System.out.println("You successfully stole " + items[lastItemIdx].title + " from " + npcs[i].title);
            	                stolen = true;
            	                break;
            	            } else {
            	                System.out.println("Steal attempt failed! " + npcs[i].title + " noticed you.");
            	            }
            	        }
            	    }
            	    if (!stolen) {
            	        System.out.println("No one to steal from here.");
            	    }
            	    break;
            default:
                System.out.println("Invalid Command"); // Handles invalid commands
                break;
        }
        
        if (Integer.parseInt(winCondition[0]) == p.xpos && 
        	    Integer.parseInt(winCondition[1]) == p.ypos) {
        	    int requiredItem = Integer.parseInt(winCondition[2]);
        	    boolean hasItem = findByIndex(requiredItem, p) >= 0;

        	    if (hasItem) {
        	        System.out.println(winCondition[3]); // Win message
        	        System.exit(0); // End the game
        	    }
        	}

        
    }
    
    private static void attack(Player[] npcs, int playerCount, Player p, Item[] items, MapBlock m) 
    {
		// TODO Auto-generated method stub
    	for(int i = 1; i < playerCount; i++)
		{
			if(npcs[i].combative == 1)
			{
				if ((npcs[i].xpos == p.xpos) && (npcs[i].ypos == p.ypos))
				{
					int hit = roll(20);
					if (hit > npcs[i].defense)
					{
						//hits
						int damage = roll(p.strength);
						npcs[i].health -= damage;
						System.out.println(p.title + " hits with " + damage + " damage. Health " + npcs[i].health);
						if(npcs[i].health <= 0)
						{
							kill(npcs[i], items, m);
						}
						
					}
					else
					{
						System.out.println("A swing and a miss");
						
					}
				}
				
				
			}}
    	
    	
    	
    	
    	
	}

	private static void kill(Player player, Item[] items, MapBlock m) 
	{
		// TODO Auto-generated method stub
		System.out.println(player.title + " is dead");
		player.title = "Body of " + player.title;
		player.combative = 0;
		player.moveable = 0;
		
		for(int i = 0; i < player.itemCount; i++)
		{
			place(player.items[i],m);
			drop(player.items[i],player,items);
			
			
		}
		
	}

	private static String prompt() {
		// TODO Auto-generated method stub
		
    	System.out.print("I'm Waiting... >> ");
    	String line = s.nextLine();
    	
    	
    	return line;
	}

	private static void drop(int itemIdx, Player p, Item[] items) 
	{
		// TODO Auto-generated method stub
		int index = findByIndex(itemIdx, p);
		
		if(index >= 0)
		{
			p.items[index] = p.items[p.itemCount - 1];
			p.items[p.itemCount - 1] = -1;
			p.itemCount--;
			p.defense -= items[itemIdx].Armour;
			p.strength -= items[itemIdx].Might;
			
		}
		
//System.out.println(" Defense = " + p.defense + " \n" + " Strength = " + p.strength);
	}

	private static void place(int itemIdx, MapBlock mapBlock) 
	{
		// TODO Auto-generated method stub
		mapBlock.itemsHere[mapBlock.itemsCount++] = itemIdx;
			
			
		}
	

	private static int findByIndex(int itemIdx, Player p) 
	{
		// TODO Auto-generated method stub
		int index = -1;
    	
    	for(int i = 0; i < p.itemCount; i++)
    	{
    		if(itemIdx == p.items[i])
    		{
    			index = i;
    			
    		}
    		
    		
    	}
		
		
		
		
		return index;
	}

	private static void remove(int itemIdx, MapBlock mapBlock) 
    {
        // Removes an item from the map block
		int index = findByIndex(itemIdx, mapBlock);
		
		if(index >= 0)
		{
			mapBlock.itemsHere[index] = mapBlock.itemsHere[mapBlock.itemsCount - 1];
			mapBlock.itemsHere[mapBlock.itemsCount - 1] = -1;
			mapBlock.itemsCount--;
			
			
		}
    
    
    }

    private static void pickup(int itemIdx, Player p, Item[] items) 
    {
    	
  //System.out.println(" Defense = " + p.defense + " \n" + " Strength = " + p.strength);
    	
        // Adds an item to the player's inventory
    	p.items[p.itemCount++] = itemIdx;
    	p.defense += items[itemIdx].Armour;
    	p.strength += items[itemIdx].Might;
    	
  //System.out.println(" Defense = " + p.defense + " \n" + " Strength = " + p.strength);
    
    
    
    }
    

    private static int findByIndex(int itemIdx, MapBlock mapBlock) 
    {
        // Finds an item by its index at a given map position
       
    	int index = -1;
    	
    	for(int i = 0; i < mapBlock.itemsCount; i++)
    	{
    		if(itemIdx == mapBlock.itemsHere[i])
    		{
    			index = i;
    			
    		}
    		
    		
    	}
    	
    	
    	
    	
    	return index;
    }

    private static int find(Item[] items, int itemCount, String stuff) 
    {
        // Finds an item index by its name
       int index = -1;
       
       for(int i = 0; i < itemCount; i++)
       {
    	if(stuff.equalsIgnoreCase(items[i].title))
    	{
    		index = i;
    	}
    	   
       }
    	
    	return index;
    }

    private static void showItems(MapBlock mapBlock, Item[] items) 
    {
        // Shows items in the specified map block
        for(int i = 0; i < mapBlock.itemsCount; i++) 
        {
            System.out.printf("There is a/an %s here.\n", items[mapBlock.itemsHere[i]].title);
        }
    }
    private static int show(MapBlock m, Item[] items, Player[] npcs, int playerCount)
    {
    	System.out.println("\n" + m.getTitle() + "\n" + m.getDesc()); //Print
    	showItems(m, items);
    	return showNpcs(m,npcs,playerCount); //returns the count of npcs in this block
    	
    	
    	
    	
    }
    
    
    
    
    

    private static int showNpcs(MapBlock m, Player[] npcs, int playerCount) 
    {
		// TODO Auto-generated method stub
    	int npcCount = 0;
    	
    	for(int i =1; i < playerCount ; i++)
    	{
    		if((npcs[i].xpos == npcs[0].xpos) && (npcs[i].ypos == npcs[0].ypos))
    		{
    			System.out.printf("%s is here.\n", npcs[i].title);
    			npcCount++;
    		}
    		
    	}
    	
		return npcCount;
	}

	public static int init(String itemfile, Item[] items, MapBlock[][] map, Player[] npcs)
    {
        // Initializes items from itemfile and places them on the map
        String splitBy = ",";
        String line;
        int itemCount = 0;

        try 
        {
            FileReader fr = new FileReader(itemfile); // Opens itemfile for reading
            BufferedReader br = new BufferedReader(fr); // Buffers the file reader

            while((line = br.readLine()) != null) 
            { // Reads each line
                String[] data = line.split(splitBy); // Splits line by comma
                
                items[itemCount] = new Item(); // Creates new item object
                
                int a = Integer.parseInt(data[0]); // Sets X position of item
                int b = Integer.parseInt(data[1]); // Sets Y position of item
                
                items[itemCount].title = data[2]; // Sets title of item
                items[itemCount].desc = data[3]; // Sets description of item
                items[itemCount].Armour = Integer.parseInt(data[4]);
                items[itemCount].Might = Integer.parseInt(data[5]);
                
                System.out.println("Adding " + items[itemCount].title);
            	
                
                if (a >= 0)
                {
                	map[a][b].itemsHere[map[a][b].itemsCount++] = itemCount; // Adds item to map position
                }
                else
                {
                	System.out.println("Adding " + items[itemCount].title + " to player " + b);
                	pickup(itemCount,npcs[b],items);
                }
                
                
                
  System.out.println(".." + items[itemCount].title + "..");              
                
                itemCount++; // Increments item count
            }
            br.close(); // Closes the buffered reader
        } 
        catch (IOException e) {
            e.printStackTrace(); // Prints error details
        } 
        finally 
        {
            return itemCount; // Returns total number of items loaded
        }
    }
    
    
    public static void init(String mapFile, MapBlock[][] map)
    {
    	String splitBy = ",";
    	String line;
    	
    	
    	try
    	{
    		FileReader fr = new FileReader(mapFile);
    		BufferedReader br = new BufferedReader(fr);
    		
    		
    		while((line = br.readLine()) !=null)
    		{
    			String[] data = line.split(splitBy);
    			int xpos = Integer.parseInt(data[0]);
    			int ypos = Integer.parseInt(data[1]);
    			
    			map[xpos][ypos] = new MapBlock();
    			
    			map[xpos][ypos].setTitle(data[2]);
    			map[xpos][ypos].setDesc(data[3]);
    			map[xpos][ypos].setN(Byte.parseByte(data[4]));
    			map[xpos][ypos].setS(Byte.parseByte(data[5]));
    			map[xpos][ypos].setE(Byte.parseByte(data[6]));
    			map[xpos][ypos].setW(Byte.parseByte(data[7]));
    			
    			
    		}
    		
    		br.close();
    		
    		
    		
    	}
    	catch (IOException e) 
    	{
            e.printStackTrace(); // Prints error details	
    	}
    	
    	
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}

