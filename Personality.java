//Wilbert Lam
//3/1/2012
//CSE142
//TA: Connor Moore
//Assignment #7
//
//This assignment will ask the user for an input and output file name and then
//print out on the output file the name, B percentages, and final personality type in a String
//after reading data off a file

import java.io.*;
import java.util.*;

public class Personality {
	public static final int DIMENSIONS = 4;
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner console = new Scanner(System.in);
		intro();
		System.out.print("input file name? ");
		String inName = console.next();
		System.out.print("output file name? ");
		String outName = console.next();
		Scanner input = new Scanner(new File("src/" + inName));
		PrintStream output = new PrintStream(new File("src/" + outName));
		
		while (input.hasNextLine()) {
			String[] profile = getIndividualResults(input);
			printName(output, profile[0]);
			int[] bPercent = computeResult(profile[1]);
			printPercent(output,bPercent);
			String result = getResult(bPercent);
			output.println(result);
		}
	}
	
	//A void method that just prints out the intro for Keirsey Temperament Sorter
	public static void intro() {
		System.out.println("This program processes a file of answers to the");
		System.out.println("Keirsey Temperament Sorter.  It converts the");
		System.out.println("various A and B answers for each person into");
		System.out.println("a sequence of B-percentages and then into a");
		System.out.println("four-letter personality type.");
		System.out.println();
	}
	
	//takes in a Scanner and takes the name and results of the test for one individual and returns
	//an array that holds  name and results
	public static String[] getIndividualResults(Scanner input) {
		String name = input.nextLine();
		String result = input.nextLine();
		
		String[] profile = {name,result};
		return profile;
	}
	
	//takes in a PrintStream and name and outputs it to the external document
	public static void printName(PrintStream output, String name) {
		output.print(name + ": ");
	}
	
	//takes in a PrintStream and an array containing the percents of B
	//and outputs it to the external document
	public static void printPercent(PrintStream output, int[] bPercent) {
		output.print("[" + bPercent[0]);
		for (int i = 1; i < DIMENSIONS; i++) {
			output.print(", " + bPercent[i]);
		}
		output.print("] = ");
	}
	
	//takes in the String of the results and then finds how many percent of B there are
	// and returns it in an array for all of them
	public static int[] computeResult(String numResults) {
		numResults = numResults.toUpperCase();
		int first = getPercent(numResults,1,false);
		int second = getPercent(numResults,2,true);
		int third = getPercent(numResults,4,true);
		int fourth = getPercent(numResults,6,true);
		int[] results = {first,second,third,fourth};
		return results;
	}
	
	//takes in the string of the result, a starting question, and a boolean whether or not there are repeats
	//of each question type one after another. uses for loop to check whether A or B for each particular
	//question type and stores the calculated percentage in an array
	public static int getPercent(String numResults, int start, boolean repeats) {
		int a = 0;
		int b = 0;
		for (int i = start - 1; i < 70 - 1; i+=7) {
			if (numResults.charAt(i) == 'A') {
				a++;
			} else if (numResults.charAt(i) == 'B') {
				b++;
			}
			if(repeats) {
				if (numResults.charAt(i+1) == 'A') {
					a++;
				} else if (numResults.charAt(i+1) == 'B') {
					b++;
				}
			}
		}
		int percent = (int) Math.round (b * 1.0 / (a + b) * 100);
		return percent;
	}
	
	//takes in an int array and then determines if it is which particular trait and returns a String
	//containing the types
	public static String getResult(int[] bPercent) {
		String type = "";
		char[] left = {'E','S','T','J'};
		char[] right = {'I','N','F','P'};
		for (int i = 0; i < DIMENSIONS;i++) {
			if(bPercent[i] > 50)
				type += right[i];
			else if (bPercent[i] == 50)
				type += "X";
			else
				type += left[i];
		}
		return type;
	}
}
