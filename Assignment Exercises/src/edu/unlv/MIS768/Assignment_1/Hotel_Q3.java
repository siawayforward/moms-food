/* Program: Hotel Occupancy Rate
 * Programmer: Sia Mbatia
 * Purpose: This program computes the occupancy rate at a hotel by checking the number of rooms occupied against the
 * 			total availability. The program gets the number of rooms on each floor, and checks if they are occupied.
 * 			The total amount from this iteration allows the program to calculate the occupancy rate at the end.
 * 			Section: Class to define attributes and methods for each hotel
 * Date: January 31, 2019
 */

package edu.unlv.MIS768.Assignment_1;

public class Hotel_Q3 {
	//output variables
	int totalRooms = 0, totalOccupiedRooms = 0; 
	double occupancy;

	//methods for calculation
	public void addOccupiedRooms(int floorOccupiedCt){
		//add to running total of occupied rooms for the whole hotel
		totalOccupiedRooms += floorOccupiedCt;
	}
	
	public int getTotalRooms() {
		return totalRooms;
	}
	
	public int getOccupiedRooms() {
		return totalOccupiedRooms;
	}
	
	public void addHotelRooms(int floorRoomCt){
		//add to running total of all floor rooms for the whole hotel
		totalRooms += floorRoomCt;
	}
	
	public double getOccupancyRate() {
		//calculate occupancy rate based on total rooms and those occupied
		occupancy = totalOccupiedRooms * 1.0/totalRooms;
		return occupancy;
	}
}
