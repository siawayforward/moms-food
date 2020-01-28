/* Program: Coin Toss Game
 * Purpose: This program allows a user to play a game with the computer. They can toss four coins (quarter, dime, nickel, penny)
 * 			simultaneously, taking turns with the computer. The player always tosses first, and can stop or keep going with the
 * 			objective of getting as close to 1 as possible. Should either the player or computer go above 1, the automatically
 * 			lose the game.
 * Programmer: Sia Mbatia
 * Date: February 23, 2019
 */

package edu.unlv.MIS768.Assignment_3;
import assignment.Coin;

import java.text.NumberFormat;
import java.util.*;

public class CoinTossGame {

	public static void main(String[] args) {
		//coin value constants
		final double QUARTER = 0.25, DIME = 0.10, NICKEL = 0.05, PENNY = 0.01;		
		
		//Array list to store coin values
		ArrayList<Coin> coins = new ArrayList<Coin>();
		coins.add(new Coin(QUARTER));
		coins.add(new Coin(DIME));
		coins.add(new Coin(NICKEL));
		coins.add(new Coin(PENNY));		
		
		//formatting
		Locale locale = new Locale("en", "US"); 
		NumberFormat formats = NumberFormat.getCurrencyInstance(locale);		
				
		//variables for player and computer
		double playerTotal = 0, compTotal = 0;	
		
		//new keyboard object
		Scanner keyboard = new Scanner(System.in);
		//start the game	
		System.out.print("Let's start playing! Enter \"Y\" to toss the coins: ");
		String choice = keyboard.next() ;
		while(choice.equalsIgnoreCase("Y")) {
			//toss the coins for both player and computer and get values
			playerTotal += getCoinValue(coins.get(0)) + getCoinValue(coins.get(1))+ getCoinValue(coins.get(2)) + getCoinValue(coins.get(3)); 
			compTotal += getCoinValue(coins.get(0)) + getCoinValue(coins.get(1))+ getCoinValue(coins.get(2)) + getCoinValue(coins.get(3)); 
			while(playerTotal <= 1 && compTotal <= 1) {
				//show player choice and check for next toss
				System.out.print("Your score: " + formats.format(playerTotal) + ". Toss again? Enter \"Y\" for Yes, \"N\" for No: ");
				choice = keyboard.next();
				//keep playing
				if(choice.equalsIgnoreCase("Y"))	{
					//show computer score
					System.out.print("Computer Score: " + formats.format(compTotal) + "\n");
					//toss the coins again for both player and computer and get values
					playerTotal += getCoinValue(coins.get(0)) + getCoinValue(coins.get(1))+ getCoinValue(coins.get(2)) + getCoinValue(coins.get(3));
					compTotal += getCoinValue(coins.get(0)) + getCoinValue(coins.get(1))+ getCoinValue(coins.get(2)) + getCoinValue(coins.get(3));
				}				
				else {  //if player says no, one more computer toss and assign winner
					compTotal += getCoinValue(coins.get(0)) + getCoinValue(coins.get(1))+ getCoinValue(coins.get(2)) + getCoinValue(coins.get(3));
					if(playerTotal > compTotal )
						System.out.println("You won the game " + formats.format(playerTotal) + " to " +
								formats.format(compTotal) + "! Thanks for playing.");
					else if(playerTotal < compTotal)
						System.out.println("Sorry, you lost the game. Better luck next time. The computer won " + 
								formats.format(compTotal) + " to " + formats.format(playerTotal));
					else
						System.out.println("It's a tie! You both scored " + formats.format(compTotal));
					break; 
				}
			}
			break;
		} 	
		//check if one player is over max
		while(compTotal > 1|| playerTotal >1) {
			if(compTotal > 1 )
				System.out.println("You won the game " + formats.format(playerTotal) + " to " +
						formats.format(compTotal) + "! Thanks for playing.");
			else if(playerTotal > 1)
				System.out.println("Sorry, you lost the game. Better luck next time. The computer won " + 
						formats.format(compTotal) + " to " + formats.format(playerTotal));
			break;
		}
		System.out.println("Game over!"); //end game otherwise
		keyboard.close(); //close scanner object
	}
	//check how the coin landed after the toss
	public static double getCoinValue(Coin coin) {
		double coinValue; //return variable
		coin.toss(); //toss the coin
		if(coin.getSideUp() == "heads")
			coinValue = coin.getFaceValue(); //add value
		else
			coinValue = 0;
		return coinValue;	
	}
}
