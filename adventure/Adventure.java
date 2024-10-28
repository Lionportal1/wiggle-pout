package adventure;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Adventure {

	public static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int maxX = 10;
		int maxY = 10;
		int maxItems = 10;
		//My Start
		int xstart = 0;
		int ystart = 0;
		
		
		int xpos = xstart;
		int ypos = ystart;
		
		String mapfile = "map.csv";
		String itemfile = "items.csv";
		
		MapBlock[][] map = new MapBlock[maxX][maxY];
		Item[] items = new Item[maxItems];
		
		init(mapfile, map);
		init(itemfile, items, map);
		
		String choice = "";
		
		System.out.println(map[xpos][ypos].getTitle() + "\n" + map[xpos][ypos].getDesc() + "\nCommand>");
		showItems(items, map, xpos, ypos);
		
		
		choice = s.nextLine();
		
		while(!choice.equals("x"))
		{
			switch(choice)
			{
			case "e":
			case "E":
			case "east":
			case "East":
				if(map[xpos][ypos].isWall('e'))
				{
					System.out.println("There is a wall there, dude. Try Again");
				}
				else
				{
					xpos++;
				}
				break;
			case "w":
			case "W":
			case "west":
			case "West":
				if(map[xpos][ypos].isWall('w'))
				{
					System.out.println("There is a wall there, dude. Try Again");
				}
				else
				{
					xpos--;
				}
				break;
			case "n":
			case "N":
			case "north":
			case "North":
				if(map[xpos][ypos].isWall('n'))
				{
					System.out.println("There is a wall there, dude. Try Again");
				}
				else
				{
					ypos--;
				}
				break;
			case "s":
			case "S":
			case "south":
			case "South":
				if(map[xpos][ypos].isWall('s'))
				{
					System.out.println("There is a wall there, dude. Try Again");
				}
				else
				{
					ypos++;
				}
				break;
			default:
				System.out.println("Invalid Command");
				break;
			}
			
			System.out.println(xpos + ":" + ypos + "  -   " + map[xpos][ypos].getTitle() + "\n" + map[xpos][ypos].getDesc() + "\nCommand> ");
			
			choice = s.nextLine();
		};
		System.out.println("You are dead. Enjoy the afterlife");
	}
	
	private static void showItems(Item[] items, MapBlock[][] map, int xpos, int ypos) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i< map[xpos][ypos].itemCount; i++)
		
		System.out.printf("There is a/an %s here\n", items[map[xpos][ypos].itemsHere[i]].title);
		
	}

	public static void init(String mapFile, MapBlock[][] map)
	{
		
		String splitBy = ",";
		String line;
		
		

		try 
		{
			FileReader fr = new FileReader(mapFile);
			BufferedReader br = new BufferedReader(fr);
		
			
			while((line = br.readLine()) != null)
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
			
//System.out.println("Block Added: " + map[xpos][ypos].getTitle());
			
			
			}
			//System.out.println("File Read");
			
			br.close();
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File not Found:" + mapFile);
			e.printStackTrace();
		}
		
		
	}

	
	
	public static void init(String itemFile, Item[] items, MapBlock[][] map)
	{
		String splitBy = ",";
		String line;
		int itemCount = 0;
		
		try
		{
			FileReader fr = new FileReader(itemFile);
			BufferedReader br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null);
			{
				String[] data = line.split(splitBy);
				int xpos = Integer.parseInt(data[0]);
				int ypos = Integer.parseInt(data[1]);
						
				items[itemCount] = new Item();
				items[itemCount].title = data[2];
				items[itemCount].desc = data[3];
				map[xpos][ypos].itemsHere[map[xpos][ypos].itemCount++] = itemCount;
				
						
System.out.println("Item Added: " + items[itemCount].title);
				
				itemCount++;
				
			}
			//System.out.println("File Read");
			
			br.close();
			
			
			
			
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File not Found:" + itemFile);
			e.printStackTrace();
		}
		
		
		
		
		
	}
}
