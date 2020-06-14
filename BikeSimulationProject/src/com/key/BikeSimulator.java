package com.key;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.key.GpsInput.Command;

/*
 * @Author Keerthi Gadde
 * 
 * Date June 12/2020
 * Bike Simulation Challenge
 * This program will allow a bike to move in 7*7 grid, valid commands are PLACE(to set the bike initial place on grid)
 * FORWARD,TURN_LEFT,TURN_RIGHT,GPS_REPORT(To display the bike position on the grid)
 * bike will not leave the grid for any command including PLACE and such command will be ignored
 */
public class BikeSimulator {
// Check if user wants to input a file or through STDIN
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String filepath = "";
		System.out.println("Please type FILE for input through file or type STDIN for command line input ");
		String cmdline = in.nextLine();
		if (cmdline.equalsIgnoreCase("STDIN")) {
			CommandLineInputs();
		} else if (cmdline.equalsIgnoreCase("FILE")) {  
			System.out.println("Please enter valid filepath");
			filepath = in.nextLine();	
			ProcessFile(filepath);
		}else {
			return;
		}
	}
	//logic for processing input from file	
	public static  void ProcessFile(String filepath) {			
				GpsInput input = new GpsInput(), output = new GpsInput();
				List<Command> commandList = new ArrayList<>();

				// filepath ="./src/gps_input.txt";
				List<String> lines = new ArrayList<>();
				try (Stream<String> stream = Files.lines(Paths.get(filepath))) {
					lines = stream.collect(Collectors.toList());

				} catch (IOException e) {
					// e.printStackTrace();
					System.out.println("Invalid file path..");
				}

				int edge = 7;
				boolean validPlace = false;
				for (String line : lines) {
					try {
						String temp = line.contains("PLACE") ? "PLACE" : line.trim();
						validPlace = temp.equals("PLACE") ? true : validPlace;
						Command command = null;
						String gpsoutput = "";
						if (Command.contains(temp) && validPlace) {
							command = Command.valueOf(temp);
							commandList.add(command);
							gpsoutput = BikeMovement.getGridPosition(input, line, output, edge);
						}
						if (gpsoutput == "INVALID_PLACE_IP") {
							validPlace = false;
							System.out.println("------------------");
							System.out.println("input " + input);
							System.out.println("commands " + commandList);
							System.out.println("Invalid PLACE values");
							commandList = new ArrayList<>();
							input = new GpsInput();
							output = new GpsInput();
						}
						if (gpsoutput == "GPS_REPORT") {
							System.out.println("------------------");
							System.out.println("input " + input);
							System.out.println("commands " + commandList);
							System.out.println("output " + output);
							System.out.println("------------------");
							System.out.println("------------------");
							commandList = new ArrayList<>();
							input = new GpsInput();
							output = new GpsInput();
						}
					} catch (Exception e) {
						// e.printStackTrace();
						validPlace = false;
					}
				}


	}
	
//STDIN logic for processing user input
	public static void CommandLineInputs() {
		GpsInput input = new GpsInput(), output = new GpsInput();
		Scanner in = new Scanner(System.in);
		System.out.println(" PLEASE ENTER PLACE COMMAND in the format Ex: PLACE 0,1,NORTH AND PRESS ENTER");

		while (true) {
			try {
				String line = in.nextLine();

				String gpsoutput = line.trim().equals("") ? "" : BikeMovement.getGridPosition(input, line, output, 7);
				if (gpsoutput == "GPS_REPORT") {
					System.out.println("input: " + input + " output:" + output);
				}
				if (gpsoutput == "INVALID_PLACE_IP") {
					System.out.println("Invalid Input.. " + input);
				}
				System.out.println(
						"Please enter next valid command and press enter ex: PLACE,FORWARD,TURN_LEFT,TURN_RIGHT,GPS_REPORT.. To get output enter GPS_REPORT");
			} catch (Exception e) {
				System.out.println("invalid command ");
			    System.out.println("PLEASE ENTER PLACE COMMAND in the format Ex: PLACE 0,1,NORTH and enter");
				//e.printStackTrace();
			}

		}

	}

}
