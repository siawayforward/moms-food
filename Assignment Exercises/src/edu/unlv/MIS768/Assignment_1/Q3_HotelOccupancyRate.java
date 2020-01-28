/* Program: Hotel Occupancy Rate
 * Programmer: Sia Mbatia
 * Purpose: This program computes the occupancy rate at a hotel by checking the number of rooms occupied against the
 * 			total availability. The program gets the number of rooms on each floor, and checks if they are occupied.
 * 			The total amount from this iteration allows the program to calculate the occupancy rate at the end.
 * Date: January 31, 2019
 */

package edu.unlv.MIS768.Assignment_1;

import java.text.DecimalFormat; //for formatting
import java.util.*;

public class Q3_HotelOccupancyRate {

	public static void main(String[] args) {
		//input variables
		int floors = 0, floorRooms = 0, vacantFloor = 0, occupiedFloor = 0;
		
		//instantiate a hotel to calculate occupancy for
		Hotel_Q3 hotel = new Hotel_Q3();
		
		//Ask user for number of floors
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("How many floors are in this hotel?");
		floors = keyboard.nextInt(); //get number of floors
		//check validity
		while(floors <= 0) {
			//when wrong floor value is provided
			System.out.println("There should be at least one floor on your hotel. Please provide the valid number of floors.");
		
			//get number of floors
			floors = keyboard.nextInt();
		}
		//check number of rooms for each floor
		for(int i = 1; i <= floors; i++)
		{
			System.out.println("How many rooms are on floor "+ i +"?");
			floorRooms = keyboard.nextInt(); //get number of floor rooms
			//when insufficient rooms are entered
			while(floorRooms < 10) {
				System.out.println("Please enter a valid number of rooms for floor "+ i +".");
				//get number of rooms 
				floorRooms = keyboard.nextInt();
			}
			//add the rooms to total hotel tally
			hotel.addHotelRooms(floorRooms);
			
			//check number of occupied rooms on the floor
			System.out.println("Of the "+ floorRooms + " rooms on this floor, how many are occupied?");
			occupiedFloor = keyboard.nextInt();	//get number of floor rooms occupied
			while(occupiedFloor < 0 || occupiedFloor > floorRooms) {
				//when insufficient occupied rooms are entered
				System.out.println("Please enter a valid number of occupied rooms for this floor.");
				occupiedFloor = keyboard.nextInt();	//get occupied rooms
			}	
			//add to the occupied rooms total tally
			hotel.addOccupiedRooms(occupiedFloor);				
		}
		//get vacant rooms
		vacantFloor = hotel.getTotalRooms() - hotel.getOccupiedRooms();
		
		//calculate and display the occupancy rate
		DecimalFormat perc = new DecimalFormat("##.##%"); //formatting
		System.out.println("Total Number of Rooms (Floors- " + floors + "): " + hotel.getTotalRooms());
		System.out.println("Total Occupied Rooms: " + hotel.getOccupiedRooms());
		System.out.println("Total Vacant Rooms: " + vacantFloor);
		System.out.println("The occupancy rate for this hotel is "+ perc.format(hotel.getOccupancyRate()));
		
		//close keyboard scanner
		keyboard.close();
	}	
}
