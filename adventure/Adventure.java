package adventure;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adventure {
    public static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        int maxX = 10;
        int maxY = 10;
        int maxItems = 20;
        int maxPlayers = 20;

        String mapfile = "map.csv";
        String itemfile = "items.csv";
        String playerfile = "npcs.csv";

        boolean playing = true;
        MapBlock[][] map = new MapBlock[maxX][maxY];
        Item[] items = new Item[maxItems];
        Player[] npcs = new Player[maxPlayers];

        int itemCount = init(itemfile, items, map);
        int playerCount = init(playerfile, npcs);
        Player p = npcs[0];

        while (playing) {
            move(map, items, p, itemCount);
            moveNpcs(map, npcs, playerCount);
            showNpcs(map);
        }
        
        System.out.println("You are dead. Enjoy the afterlife");
    }

    private static void showNpcs(MapBlock[][] map) {
		// TODO Auto-generated method stub
		
	}

	private static void moveNpcs(MapBlock[][] map, Player[] npcs, int playerCount) {
        Random rand = new Random();

        for (int i = 1; i < playerCount; i++) {
            if (npcs[i].moveable == 1) {
                int direction = getRand(1, 4);
                switch (direction) {
                    case 1: // Move east
                        if (!map[npcs[i].xpos][npcs[i].ypos].isWall('e')) npcs[i].xpos++;
                        break;
                    case 2: // Move west
                        if (!map[npcs[i].xpos][npcs[i].ypos].isWall('w')) npcs[i].xpos--;
                        break;
                    case 3: // Move north
                        if (!map[npcs[i].xpos][npcs[i].ypos].isWall('n')) npcs[i].ypos--;
                        break;
                    case 4: // Move south
                        if (!map[npcs[i].xpos][npcs[i].ypos].isWall('s')) npcs[i].ypos++;
                        break;
                }
            }
        }
    }

    public static int getRand(int min, int max) {
        if (min >= max) {
            System.out.println("Range Error: min greater than max");
            return -1;
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    private static int init(String playerfile, Player[] npcs) {
        String splitBy = ",";
        String line;
        int playerCount = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(playerfile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                npcs[playerCount] = new Player();
                playerCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return playerCount;
    }

    public static void move(MapBlock[][] map, Item[] items, Player p, int itemCount) {
        System.out.println(map[p.xpos][p.ypos].getTitle() + "\n" + map[p.xpos][p.ypos].getDesc() + "\nCommand>");
        showItems(map[p.xpos][p.ypos], items);

        System.out.print("I'm waiting... >>");
        String line = s.nextLine();
        String[] data = line.split(" ", 2);
        String choice = data[0];

        switch (choice.toLowerCase()) {
            case "e":
            case "east":
                if (map[p.xpos][p.ypos].isWall('e')) System.out.println("There is a wall there, dude. Try Again");
                else p.xpos++;
                break;
            case "w":
            case "west":
                if (map[p.xpos][p.ypos].isWall('w')) System.out.println("There is a wall there, dude. Try Again");
                else p.xpos--;
                break;
            case "n":
            case "north":
                if (map[p.xpos][p.ypos].isWall('n')) System.out.println("There is a wall there, dude. Try Again");
                else p.ypos--;
                break;
            case "s":
            case "south":
                if (map[p.xpos][p.ypos].isWall('s')) System.out.println("There is a wall there, dude. Try Again");
                else p.ypos++;
                break;
            case "get":
                if (map[p.xpos][p.ypos].itemsCount == 0) System.out.println("I see nothing...");
                else {
                    String stuff = data[1];
                    int itemIdx = find(items, itemCount, stuff);
                    int itemCheck = findByIndex(itemIdx, p.xpos, p.ypos);
                    if (itemCheck >= 0 && itemIdx >= 0) {
                        pickup(itemIdx, p);
                        System.out.println("You now have the " + stuff + ".");
                        remove(itemIdx, map[p.xpos][p.ypos]);
                    } else {
                        System.out.println("Umm... There is no " + stuff + " here.");
                    }
                }
                break;
            default:
                System.out.println("Invalid Command");
                break;
        }
    }

    private static void remove(int itemIdx, MapBlock map) {
        map.removeItem(itemIdx);
    }

    private static void pickup(int itemIdx, Player p) {
        p.addItemToInventory(itemIdx);
    }

    private static int findByIndex(int itemIdx, int xpos, int ypos) {
        return itemIdx;  // Assuming all checks are handled
    }

    private static int find(Item[] items, int itemCount, String stuff) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].title.equalsIgnoreCase(stuff)) return i;
        }
        return -1;
    }

    private static void showItems(MapBlock mapBlock, Item[] items) {
        for (int i = 0; i < mapBlock.itemsCount; i++) {
            System.out.printf("There is a/an %s here.\n", items[mapBlock.itemsHere[i]].title);
        }
    }

    public static int init(String itemfile, Item[] items, MapBlock[][] map) {
        String splitBy = ",";
        String line;
        int itemCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(itemfile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                items[itemCount] = new Item();
                map[items[itemCount].xpos][items[itemCount].ypos].addItem(itemCount);
                itemCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return itemCount;
    }
}
