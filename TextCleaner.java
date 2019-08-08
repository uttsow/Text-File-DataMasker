/**
 *
 */
//package parser;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Uttsow
 *
 */
public class TextCleaner {

	//Default constructor
	public TextCleaner() {

	}

	public void processFile(String filename, String outputFile, List<Integer> colms) {
		try {
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

			while((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if(invalidArgumentChecker(colms, data.length)) {
					System.err.println("Modifying columns cant be larger than the file");
					throw new IllegalArgumentException();
				}
				System.out.println(Arrays.toString(data));
				for(int i= 0; i < colms.size(); i++) {
					int argument = colms.get(i);
					if(isNumber(data[argument])) {
						data[argument] = numberCleaner(data[argument]);
//						System.out.println(data[argument]);
					}else if(isCharacters(data[argument])) {
						data[argument] = characterClean(data[argument]);
					}else {
						continue;
					}
//					System.out.println(Arrays.toString(data));
				}


				String writingInfo = String.join(",", data);
				writer.write(writingInfo);
			}
			reader.close();
			writer.flush();
			writer.close();


		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	//Checks if all the values in the colm modifers are less than the
	public boolean invalidArgumentChecker(List<Integer> cloms, int rowSize) {
		for(Integer x : cloms) {
			if(x > rowSize) {
				return true;
			}
		}
		return false;
	}


	//To check if string is a number
	public boolean isNumber(String number) {
		for(char c : number.toCharArray()) {
			if(Character.isDigit(c)) {
				return true;
			}
		}

		return false;
	}

	//To check if string is a character
	public boolean isCharacters(String chars) {
		for(char c : chars.toCharArray()) {
			if(Character.isAlphabetic(c)) {
				return true;
			}
		}
		return false;
	}

	//clean the character strings
	public String characterClean(String character) {
		StringBuilder newName = new StringBuilder(character);
		newName.toString().trim();

		int length = character.length();
		for(int i = 0; i < length; i++) {

			//Takes into account spaces
			if(Character.isSpaceChar(newName.charAt(i))) {
				continue;
			}

			//Takes into account if uppercase or lowercase
			Random rnd = new Random();
			char c = (char) (rnd.nextInt(26) + 'a');
			if(Character.isUpperCase(newName.charAt(i))){
				newName.setCharAt(i, Character.toUpperCase(c));
			}else if(Character.isLowerCase(newName.charAt(i))) {
				newName.setCharAt(i, c);
			}

		}


		//
		return new String(newName);



	}

	//Clean the number strings
	public String numberCleaner(String number) {
		//Removes all unnecesary stuff if there is any
		number = number.replaceAll("[^a-zA-Z0-9]", "");
		int numberLength = number.length();
//		System.out.println(numberLength);
		long min = (long) Math.pow(10, numberLength - 1);
		int ret = (int) ThreadLocalRandom.current().nextLong(min, min * 10);

		return new String(Integer.toString(ret));

	}


	/**
	 * @param args
	 * Args include: File name, Output File name, custom colms to modify
	 */

	public static void main(String[] args) {
		TextCleaner cleaner = new TextCleaner();
		


		if(args.length < 3) {
			System.err.println("Arguments cannot be less than 3");
			throw new IllegalArgumentException();
		}

		int argsSize = args.length;

		//Check if all the values in args are numbers
		try {
			for(int i = 2; i < argsSize; i++) {
				Integer.parseInt(args[i]);
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			System.out.println("Colm list is not a number");
			e.printStackTrace();

		}


		//Parsing all modification colms to the list
		List<Integer> colms = new ArrayList<Integer>();
		for(int i = 2; i < argsSize; i++) {
			colms.add(Integer.parseInt(args[i]));
		}

		//put it all in the ascending order
		Collections.sort(colms);

		cleaner.processFile(args[0], args[1], colms);




	}



}
