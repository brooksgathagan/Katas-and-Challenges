package com.techelevator;

import java.util.Arrays;

public class KataPotter 
{
	public final static int SINGLE_BOOK_COST = 8;
	public final static double TWO_BOOK_RATE = 0.95;
	public final static double THREE_BOOK_RATE = 0.90;
	public final static double FOUR_BOOK_RATE = 0.8;
	public final static double FIVE_BOOK_RATE = 0.75;
	
	public double getCost(int[] books)
	{
		if (books == null)
			return 0;
		
		// Checking the maximum number purchased of any single book
		// This will determine the size of our 2D array.
		
		int maxNumber = 0;
		for (int i = 0; i < books.length; i++)
			if (books[i] > maxNumber)
				maxNumber = books[i];
				
		// Declaring our 2D array using the aforementioned maxNumber, setting each index of the array to an integer array equal to the length of the "books" array (5)
		
		int[][] uniqueCases = new int[maxNumber][books.length];
		
		// The first set of nested loops below obviously sets up our location in the 2D array.
		// Within the second loop, we're checking if the value > 0 to find the number of unique books purchased.
		// If we have a unique book, we set the corresponding place in our 2D array to 1 and subtract 1 from that index in the "books" array.  If no unique book, we set it to 0.
		// As we loop through, we end up with a giant list representing the number of unique books at each pass (e.g. for {2, 2, 2, 1, 1}, we set our 2D array's first index to {1, 1, 1, 1, 1} and subtract 1 from the books array, leaving us with {1, 1, 1, 0, 0}. The second value in our 2D array will thusly be the same -- {1, 1, 1, 0, 0}
		
		for (int i = 0; i < uniqueCases.length; i++)
		{
			for (int j = 0; j < uniqueCases[i].length; j++)
			{
				if (books[j] > 0)
				{
					uniqueCases[i][j] = 1;
					books[j] -= 1;
				}
				else
					uniqueCases[i][j] = 0;
			}
		}
		
		// The goal of this loop & its contents is to check for the case of a unique set of 5 followed by a unique set of 3 -- as is the case with {2, 2, 2, 1, 1} or {1, 2, 3, 4, 5}. Set of 3 can come anywhere after the set of 5, which I didn't originally account for (only checked i and i+1 before discovering bugs w/ other tests)
		// If we encounter that sequence, we change each to set of 4.  The order of the #'s doesn't matter.
		for (int i = 0; i < uniqueCases.length - 1; i++)
		{
			int uniqueNumsCurrent = 0;
			
			for (int book : uniqueCases[i])
				if (book == 1)
					uniqueNumsCurrent++;
			
			if (uniqueNumsCurrent == 5)
			{
				for (int j = i + 1; j < uniqueCases.length; j++)
				{
					int uniqueNumsNext = 0;
					
					for (int book : uniqueCases[j])
						if (book == 1)
							uniqueNumsNext++;
					
					if (uniqueNumsNext == 3)
					{
						uniqueCases[i] = new int[] {1, 1, 1, 1, 0};
						uniqueCases[j] = new int[] {1, 1, 1, 1, 0};
						break;
					}
				}
			}
		}
		
		return calculateCost(uniqueCases);
	}
	
	// Here, we're passing our 2D array (which arrays of 1's and 0's representing unique book combinations) into a calculateCost() method.
	// calculateCost() determines the cost of each binary set and adds it to the total.
	
	public static double calculateCost(int[][] uniqueBookArray)
	{
		double totalCost = 0;
		
		for (int[] bookArrays : uniqueBookArray)
		{
			int uniqueCount = 0;
			
			for (int unique : bookArrays)
				uniqueCount += unique;
			
			if (uniqueCount == 5)
				totalCost += (uniqueCount * SINGLE_BOOK_COST) * FIVE_BOOK_RATE;
			else if (uniqueCount == 4)
				totalCost += (uniqueCount * SINGLE_BOOK_COST) * FOUR_BOOK_RATE;
			else if (uniqueCount == 3)
				totalCost += (uniqueCount * SINGLE_BOOK_COST) * THREE_BOOK_RATE;
			else if (uniqueCount == 2)
				totalCost += (uniqueCount * SINGLE_BOOK_COST) * TWO_BOOK_RATE;
			else
				totalCost += (uniqueCount * SINGLE_BOOK_COST);
		}
		
		System.out.println("Your total is: $" + String.format("%.2f", totalCost));

		return totalCost;
	}
	
}