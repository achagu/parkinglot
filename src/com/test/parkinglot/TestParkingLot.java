package com.test.parkinglot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.parkinglot.ParkingSpace;
import com.parkinglot.Ticket;

public class TestParkingLot {
	
	
	@Test
	public void testParkingSpaceTicketsAndColor(){
		ParkingSpace parkingSpace = new ParkingSpace(5);
		parkingSpace.generateTicket("KA­01­HH­1234", "White");
		parkingSpace.generateTicket("KA­01­HH­1245", "Red");
		parkingSpace.generateTicket("KA­01­HH­1267", "Blue");
		parkingSpace.generateTicket("KA­01­HH­1282", "White");
		parkingSpace.generateTicket("KA­01­HH­1305", "Yellow");
		List<Ticket> parkingTickets  = parkingSpace.getParkingTicketsBasedOnColor("Red");
		String registerNo = parkingTickets.get(0).getRegistrationNo();
		assertEquals(registerNo,"KA­01­HH­1245");
	}
	
	@Test
	public void testParkingSpaceRegisColorList(){
		ParkingSpace parkingSpace = new ParkingSpace(3);
		parkingSpace.generateTicket("KA­01­HH­1234", "White");
		parkingSpace.generateTicket("KA­01­HH­1245", "Red");
		parkingSpace.generateTicket("KA­01­HH­1267", "Blue");
		List<String> registrNoWithColor = parkingSpace.getRegistrationNosWithColor("Blue");
		assertEquals("KA­01­HH­1267",registrNoWithColor.get(0));
		
		Integer parkingSlot = parkingSpace.getParkingSlotBasedOnRegistrationNo("KA­01­HH­1245");
		assertNotNull(parkingSlot);
	}
	
	@Test
	public void testParkingSpaceTicketGen(){
		ParkingSpace parkingSpace = new ParkingSpace(3);
		parkingSpace.generateTicket("KA­01­HH­1234", "White");
		parkingSpace.generateTicket("KA­01­HH­1245", "Red");
		parkingSpace.generateTicket("KA­01­HH­1267", "Blue");
		Collection<Ticket> ticketList = parkingSpace.getTicketList();
		int size = ticketList.size();
		assertEquals(3,size);
	}
	
	@Test
	public void testReleaseTicket(){
		ParkingSpace parkingSpace = new ParkingSpace(3);
		parkingSpace.generateTicket("KA­01­HH­1234", "White");
		parkingSpace.generateTicket("KA­01­HH­1245", "Red");
		parkingSpace.generateTicket("KA­01­HH­1267", "Blue");
		
		Integer parkingSlot = parkingSpace.getParkingSlotBasedOnRegistrationNo("KA­01­HH­1245");
		assertNotNull(parkingSlot);
		
		parkingSpace.releaseTicket(parkingSlot);
		parkingSlot = parkingSpace.getParkingSlotBasedOnRegistrationNo("KA­01­HH­1245");
		assertNull(parkingSlot);
	}
	
	
	
	
	

}
