package adventure;

import java.util.Scanner;

public class Adventure {

	public static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int x = 0;
		int y = 0;
		String choice = "";
		
		System.out.print("Currently at: " + x + ":" + y + "\nCommand> ");
		
		choice = s.nextLine();
		
		while(!choice.equals("x"))
		{
			switch(choice)
			{
			case "e":
			case "E":
			case "east":
			case "East":
				x++;
				break;
			case "w":
			case "W":
			case "west":
			case "West":
				x--;
				break;
			case "n":
			case "N":
			case "north":
			case "North":
				y--;
				break;
			case "s":
			case "S":
			case "south":
			case "South":
				y++;
				break;
			default:
				System.out.println("Invalid Command");
				break;
			}
			
			System.out.println("Currently at: " + x + ":" + y + "\nCommand> ");
			
			choice = s.nextLine();
		};
		System.out.println("You are dead. Enjoy the afterlife");
	}

}
