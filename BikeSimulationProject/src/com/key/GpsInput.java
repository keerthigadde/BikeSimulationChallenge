package com.key;

import java.util.EnumSet;

/*
 * @Author Keerthi Gadde
 * 
 * Date June 12/2020
 * Bike Simulation Challenge
 * GpsInput is a POJO class that will take direction and x,y coordinates used for input and output variables 
 * 
 */

public class GpsInput {
// enum Direction is used to represent various directions in the program and getTurndDirection will return the new direction based on the TURN command
	public enum Direction {
		NORTH, EAST, SOUTH, WEST;
		public Direction getTurnDirection(Command cmd) {
			switch (this) {
			case NORTH:
				return (cmd == Command.TURN_LEFT) ? WEST : EAST;
			case SOUTH:
				return (cmd == Command.TURN_LEFT) ? EAST : WEST;
			case WEST:
				return (cmd == Command.TURN_LEFT) ? SOUTH : NORTH;
			case EAST:
				return (cmd == Command.TURN_LEFT) ? NORTH : SOUTH;
			default: // return null
			}
			return this;
		}

	}

// enum Command is used to represent various commands used to simulate bike 
	public enum Command {
		PLACE, FORWARD, TURN_LEFT, TURN_RIGHT, GPS_REPORT;
		public static <E extends Enum<E>> boolean contains(String value) {
			try {
				return EnumSet.allOf(Command.class).contains(Enum.valueOf(Command.class, value));
			} catch (Exception e) {
				return false;
			}
		}
	}

	private int x = 0;
	private int y = 0;
	private Direction direction;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return ("(" + x + "," + y + ")") + ", " + direction;
	}

}
