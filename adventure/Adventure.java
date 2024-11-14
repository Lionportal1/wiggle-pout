package adventure; // Defines the package for the Adventure class

import java.util.Scanner; // Imports Scanner class for user input
import java.io.BufferedReader; // Imports BufferedReader for reading files
import java.io.FileReader; // Imports FileReader to read files
import java.io.IOException; // Imports IOException for handling input/output exceptions

public class Adventure 
{ // Defines the main class Adventure

    public static Scanner s = new Scanner(System.in); // Creates a Scanner object for reading user input
    
    public static void main(String[] args) 
    { // Main method that starts the game
        int maxX = 10; // Maximum X dimension of the map
        int maxY = 10; // Maximum Y dimension of the map
        int maxItems = 20; // Maximum number of items
        int maxPlayers = 20; // Maximum number of players

        String mapfile = "map.csv"; // File name for the map data
        String itemfile = "items.csv"; // File name for item data
        String playerfile = "npcs.csv"; // File name for NPC data

        boolean playing = true; // Game state flag, true means game is running
        boolean alive = true; //Are you Alive?
        MapBlock[][] map = new MapBlock[maxX][maxY]; // Initializes a 2D array of MapBlock for the game map
        Item[] items = new Item[maxItems]; // Array to store items in the game
        Player[] npcs = new Player[maxPlayers]; // Array to store NPCs in the game

        init(mapfile, map); // Initializes the map with data from mapfile
        int itemCount = init(itemfile, items, map); // Initializes items and returns item count
        int playerCount = init(playerfile, npcs); // Initializes NPCs and returns player count

        Player p = npcs[0]; // Sets the first player in the NPC array as the main player

        while(playing) 
        { // Main game loop
           if(show(map[p.xpos][p.ypos], items, npcs, playerCount) > 0) 
           {
        	   combat(playerCount, npcs, p);
        	   
        	   
        	   
        	   
        	   
        	   
           }
        	alive = checkAlive(p);
        	
        	
        	
        	
        	
        	if(alive)
            {
            	move(map, items, p, itemCount, npcs, playerCount); // Handles player's movement and actions
            	moveNpcs(map, npcs, playerCount); // Handles NPC movements
                 //showNpcs(map); // Displays NPCs present on the map
            }
        	else
        	{
        		playing = false;
        		System.out.println("You are dead. Enjoy the afterlife"); // Message displayed when the game ends
        		
        		
        		
        	}
        }
        
        System.out.println("Goodbye"); // Message displayed when the game quits
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
                        pickup(itemIdx, p); // Picks up the item
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
                        drop(itemIdx, p); // Removes item from player
                    } 
                    else 
                    {
                        System.out.println("Umm... There is no " + stuff + " here.");
                    }
              
                }
                break;
            	case "attack": case "Attack": case "a": case "A": // attack
                    attack(npcs, playerCount, p);
                    break;
            	case "inventory": case "Inventory": case "i": case "I": // Inventory
                    System.out.println("You are carrying: ");
            		for(int i = 0; i < p.itemCount; i++)
                    {
                    	System.out.println(items[p.items[i]].title);
                    }
                    break;
            default:
                System.out.println("Invalid Command"); // Handles invalid commands
                break;
        }
    }
    
    private static void attack(Player[] npcs, int playerCount, Player p) 
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
							kill(npcs[i]);
						}
						
					}
					else
					{
						System.out.println("A swing and a miss");
						
					}
				}
				
				
			}}
    	
    	
    	
    	
    	
	}

	private static void kill(Player player) 
	{
		// TODO Auto-generated method stub
		System.out.println(player.title + " is dead");
		player.title = "Body of " + player.title;
		player.combative = 0;
		player.moveable = 0;
	}

	private static String prompt() {
		// TODO Auto-generated method stub
		
    	System.out.print("I'm Waiting... >> ");
    	String line = s.nextLine();
    	
    	
    	return line;
	}

	private static void drop(int itemIdx, Player p) 
	{
		// TODO Auto-generated method stub
		int index = findByIndex(itemIdx, p);
		
		if(index >= 0)
		{
			p.items[index] = p.items[p.itemCount - 1];
			p.items[p.itemCount - 1] = -1;
			p.itemCount--;
			
			
		}
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

    private static void pickup(int itemIdx, Player p) 
    {
        // Adds an item to the player's inventory
    	p.items[p.itemCount++] = itemIdx;
    
    
    
    
    
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

	public static int init(String itemfile, Item[] items, MapBlock[][] map)
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
                
                int xpos = Integer.parseInt(data[0]); // Sets X position of item
                int ypos = Integer.parseInt(data[1]); // Sets Y position of item
                
                items[itemCount].title = data[2]; // Sets title of item
                items[itemCount].desc = data[3]; // Sets description of item
                map[xpos][ypos].itemsHere[map[xpos][ypos].itemsCount++] = itemCount; // Adds item to map position

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

