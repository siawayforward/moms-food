/* Program: Polling System
 * Purpose: This application is a simple polling program that allows users to rate the presidential candidates from 1 (least favorable) to 7
			(most favorable). When the program starts up, a user can specify the numbers of candidates in their poll and then enter the names 
			of the candidates. The user can also specify how many respondents they plan to get before having friends and family to take the 
			poll survey. Once the poll is closed, a summary of the result is generated and written to a file, including: 
			(1) a tabular report with the candidates down the left side and the 7 ratings across the top, listing in each column the number of
			 	ratings received for each candidate. 
		 	(2) To the right of each row, show the average of the ratings for that candidate. 
		 	(3) Which candidate(s) received the highest point total 
		 	(4) Which candidate(s) received the lowest point total
 * Programmer: Sia Mbatia
 * Date: February 11th, 2019
 */

package edu.unlv.MIS768.Assignment_2;

import java.util.*; //Arrays and Scanner object
import java.io.*; //writing to a file

public class Q1_PollingSystem {
	
	/**
	 * Method for getting all the candidates ratings metrics and reading them to a file 
	 * to display ratings, average ratings, and highest and lowest point getters
	 * @param names array that contains the names of all candidates entered
	 * @param metrics array that contains the votes for each candidate including 
	 * 		   rating count, average rating, and total rating points for each candidate
	 * @throws IOException 
	 */
	public static void saveMetrics(String[] names, double[][] metrics, int voters) throws IOException {
		//ask user to enter file name
		System.out.println("Please enter the file name, including the path of where you would like to save this list: ");
		String fileName; //input variable
		String outputLine;
		//define scanner for keyboard input
		Scanner input = new Scanner(System.in);
		fileName =input.nextLine()+".csv"; //get filename
		
		//Create an output file
		FileWriter fw = new FileWriter(fileName, true);
		PrintWriter presCandidates = new PrintWriter(fw);
		
		//add header to file
		String header = "Candidates, Rating 1, Rating 2, Rating 3, Rating 4, Rating 5, Rating 6, Rating 7, Average Rating";
		presCandidates.println(header);
		
		//Add each candidate's data
		//variables for identifying max/minimum values
		double maxTotal = 0, minTotal = 100000;
		double[] totals = new double[names.length]; //array to store totals
		String maxPerson = "", minPerson = ""; //for identifying the candidate with bounds
		for(int c = 0; c < names.length; c++) {
			totals[c] = metrics[c + 1][8];
			//concatenate the name and values
			outputLine = names[c] + "," + metrics[c + 1][1] + "," + metrics[c + 1][2] + "," + + metrics[c + 1][3] + "," + metrics[c + 1][4] 
					+ "," + metrics[c + 1][5] + ","+ metrics[c + 1][6] + ","+ metrics[c + 1][7] + "," + metrics[c + 1][9];
			presCandidates.println(outputLine);
		}
		//give voter info
		presCandidates.println(); //extra space
		presCandidates.println("Total poll respondents today: "+ voters);
		//get maximum and minimums by traversing the column
		for(int v = 0; v < totals.length; v++) {
			//maximum
			//maxTotal = totals[0];
			//maxPerson = names[0];
			if(totals[v] > maxTotal) {
				maxTotal = totals[v];
				maxPerson = names[v];
			}
			else if (totals[v] == maxTotal )
				maxPerson += " and " + names[v];
				
			//minimum
			//minTotal = totals[0];
			//minPerson = names[0];
			if(totals[v] < minTotal ) {
				minTotal = totals[v];
				minPerson = names[v];
			}
			else if (totals[v] == minTotal)
				minPerson += " and " + names[v];
		}
		//add the highest and lowest scorers:
		presCandidates.println(); //extra space
		presCandidates.println(maxPerson + " received the higest point total: " + (int) maxTotal + " points");
		presCandidates.println(minPerson + " received the lowest point total: " + (int) minTotal + " points");
	
		presCandidates.close(); //save and close file
		input.close(); //close keyboard scanner
	}	 
			
	public static void main(String[] args) throws IOException {
		//define variables
		int candidateCt = 0; //number of candidates
		int voting = 0, rating; //voting indicator, each voter rating
		//new scanner object for inputs
		Scanner input = new Scanner(System.in);	
		//get the number of candidates
		System.out.print("How many candidates are running for president? ");
		candidateCt = input.nextInt();
		
		//validate candidate count
		while(candidateCt <= 0) {
			System.out.print("Please provide a valid number of presidential candidates: ");
			candidateCt = input.nextInt();
		}		
		//get the names of the candidates and store in an array
		String [] candidates = new String[candidateCt];
		//define array for metrics
		double [][] candidateScores = new double[candidateCt + 1][10]; //headers, candidates, 7 rating counts, total points, average rating
		//headers
		for(int i = 0; i < candidateCt; i++)
		{
			System.out.print("Please enter the name of candidate "+ Integer.toString(i + 1) + ": ");		
			candidates[i] = input.next(); //add candidates' names to array
			candidateScores[i + 1][0] = i + 1; //add an indicator for the candidate in the metrics array in the first column
		}
		//add rating indicator headers to array
		for(int r = 1; r <= 7; r++)
			candidateScores[0][r] = r;
		
		//ask raters to rate each candidate
		System.out.print("Please enter the number of voters you expect today: ");
		voting = input.nextInt();
		
		while(voting <= 0) { //verify number of voters entered
			System.out.print("Please enter at least one voter to start the polling: ");
			voting = input.nextInt();
		}
		//set final number of voters and a check for how many people have been polled
		final int VOTERS = voting;
		while(voting > 0) {
			for(String candidate: candidates) {
				System.out.print("Please rate " + candidate + " as a presidential candidate on a scale of 1 (least favorable) to 7 (most favorable): ");
				//get the rating provided for the candidate
				rating = input.nextInt();
				
				while(rating < 1 || rating > 7) {//validate rating entered
					System.out.print("Invalid: Please rate " + candidate + " as a presidential candidate on a scale of 1 (least favorable) to 7 (most favorable): ");
					input.nextLine();
					rating = input.nextInt(); //get the rating provided for the candidate
				}
				//get index of the candidate (what number candidate is it?) 
				int candIndex = Arrays.asList(candidates).indexOf(candidate);
				//add rating to candidate's count
				//add to the candidate rating count based on rating given
				for(int r = 1; r <= 7; r++) {   //ratings
					for(int j = 0; j < candidates.length; j++) {   //number of candidates
						if(rating == r && candIndex == j)
							candidateScores[j + 1][r] ++; //add to candidate's rating count
					}
				}					
			}
			voting -= 1;  //count as "voted"
			
			//Call next voter (if any)				
			if(voting == 0) {
				System.out.println("Thank you for voting today!");
				 //get each voters results
				//When polling is done, compute total points and averages
				//sum and average all the candidates score counts
				for(int j = 1; j <= candidates.length; j++){  //candidate rows
					for(int rate = 1; rate <= 7; rate++) {
						candidateScores[j][8] += candidateScores[j][rate] * rate; //[score count * rating] = total points
						candidateScores[j][9] = (candidateScores[j][8])*1.0/VOTERS; //average candidate rating
					}
				}
			}
			else
				System.out.println("Ready for poll voter "+ Integer.toString(VOTERS - voting + 1));
		}
					
		//Write metrics to file with method
		saveMetrics(candidates, candidateScores, VOTERS);
		//Notify user that file is saved
		System.out.println("Your candidate data has been saved.");
		input.close(); //close keyboard scanner object
	}	
}
