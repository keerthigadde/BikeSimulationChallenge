package com.key.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.key.BikeMovement;
import com.key.GpsInput;

public class BikeMovementTest {	
	GpsInput input,output ;
	int gridSize =7;
	
@Before  
 public  void setUpBeforeClass() throws Exception {         
         input = new GpsInput();
         output =new GpsInput();	
 } 
//test valid place inputs
@Test
public void ShouldReturnValidPlaceValues(){
	String placeCommand = "PLACE 2,3,NORTH";
	BikeMovement.getGridPosition(input, placeCommand, output, gridSize);	
	assertEquals(2, input.getX());
	assertEquals(3, input.getY());
	assertEquals("NORTH", input.getDirection().name());
    placeCommand = "PLACE -2,-3,NORTH";
	assertEquals("INVALID_PLACE_IP",BikeMovement.getGridPosition(input, placeCommand, output, gridSize));
}
//test for placing bike out of grid and ignore commands which are given before valid place commands	
@Test
public void IgnoreAndInvalidPlaceCmd() {
	String testip = "TURN_RIGHT\nFORWARD\nPLACE -2,-2,SOUTH\nFORWARD\nPLACE 4,3,NORTH\nFORWARD\nTURN_RIGHT\nGPS_REPORT";	
	for(String ip:testip.split("\n")) {
		try {
		BikeMovement.getGridPosition(input, ip, output, gridSize);
		}catch(Exception e) {
			//ignore invalid inputs
		}
	}
	assertEquals(4, output.getX());
	assertEquals(4, output.getY());
	assertEquals("EAST", output.getDirection().name());
}
//test to make sure bike will not leave the grid but allowing other valid movements
@Test
public void MovementOutsideGridRestricted() {
	String testip = "PLACE 6,6,EAST\nFORWARD\nFORWARD\nTURN_RIGHT\nFORWARD\nGPS_REPORT";	
	for(String ip:testip.split("\n")) {
		BikeMovement.getGridPosition(input, ip, output, gridSize);
	}
	assertEquals(6, output.getX());
	assertEquals(5, output.getY());
	assertEquals("SOUTH", output.getDirection().name());
}

@Test
public void OutsideGridMovementRestricted() {
	String testip = "PLACE 0,2,SOUTH\nTURN_RIGHT\nFORWARD\nFORWARD\nTURN_RIGHT\nFORWARD\nGPS_REPORT";	
	for(String ip:testip.split("\n")) {
		BikeMovement.getGridPosition(input, ip, output, gridSize);
	}
	assertEquals(0, output.getX());
	assertEquals(3, output.getY());
	assertEquals("NORTH", output.getDirection().name());
}
//challenge document test case
@Test
public void ValidInputTest() {
	String testip = "PLACE 0,0,NORTH\nTURN_LEFT\nGPS_REPORT";	
	for(String ip:testip.split("\n")) {
		BikeMovement.getGridPosition(input, ip, output, gridSize);
	}
	assertEquals(0, output.getX());
	assertEquals(0, output.getY());
	assertEquals("WEST", output.getDirection().name());
}
//Multiple valid place commands
@Test
public void ValidInputTest2() {
	String testip = "PLACE 1,2,NORTH\nTURN_LEFT\nPLACE 1,2,SOUTH\nFORWARD\nGPS_REPORT";	
	for(String ip:testip.split("\n")) {
		BikeMovement.getGridPosition(input, ip, output, gridSize);
	}
	assertEquals(1, output.getX());
	assertEquals(1, output.getY());
	assertEquals("SOUTH", output.getDirection().name());
}
}
