package com.key;

import com.key.GpsInput.Command;
import com.key.GpsInput.Direction;

/*
 * @Author Keerthi Gadde
 * 
 * Date June 12/2020
 * Bike Simulation Challenge
 * BikeMovement class contains the logic for various commands issued in the input and corresponding logic
 */
public class BikeMovement {

	public static String getGridPosition(GpsInput input, String line, GpsInput output, int edge) {
		Command command = line.toUpperCase().contains("PLACE") ? Command.valueOf("PLACE") : Command.valueOf(line.trim().toUpperCase());
		if (command == Command.PLACE) { //PLACE command will set the initial values of x,y coordinates  and the direction of input 
			try {
				String[] tmp = line.substring("PLACE".length() + 1).split(",");
				int x = Integer.valueOf(tmp[0].trim());
				int y = Integer.valueOf(tmp[1].trim());
				Direction direction = Direction.valueOf(tmp[2].toUpperCase());
				input.setX(x);
				input.setY(y);
				input.setDirection(direction);
				if ((x < 0 || x > edge - 1) || (y < 0 || y > edge - 1)) { //check if input values are out of the grid return INVALID msg
					return "INVALID_PLACE_IP"; 
				}
				output.setX(x);
				output.setY(y);
				output.setDirection(direction);
			} catch (Exception e) { //parsing input issue return invalid msg
				return "INVALID_PLACE_IP";
			}
		} else if (command == Command.FORWARD) { // FORWARD cmd will move bike 1 postion in current direction check 
			//if fwd cmd will move bike out of grid in any direction ignore fwd cmd else move the bike 1 position in current direction
			switch (output.getDirection()) {
			case NORTH:
				if (output.getY() >= 0 && output.getY() < edge - 1)  
					output.setY(output.getY() + 1); 
				break;
			case SOUTH:
				if (output.getY() > 0 && output.getY() <= edge - 1)  
					output.setY(output.getY() - 1); 
				break;
			case EAST:
				if (output.getX() >= 0 && output.getX() < edge - 1) 
					output.setX(output.getX() + 1);  
				break;
			case WEST:
				if (output.getX() > 0 && output.getX() <= edge - 1) 
					output.setX(output.getX() - 1);  
				break;
			}

		} else if (command == Command.TURN_RIGHT || command == Command.TURN_LEFT) { 
			// TURN_RIGHT , TURN_LEFT cmds will change the bike direction right or left in current position without any movement
			output.setDirection(output.getDirection().getTurnDirection(command));
		} else if (command == Command.GPS_REPORT) {
		  // GPS_REPORT will output the bike's current position on the grid and direction in calling function
			return "GPS_REPORT";
		} else {
			return "FAILURE"; //Invalid command will return failure
		}
		return "SUCCESS";  
	}

}
