package com.parkinglot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParkingSpace {

	Integer MaxNoOfTickets;
	Map<String,Ticket> carRegistrationToTicketMap = new HashMap<String,Ticket>(); 
	Map<String,List<Ticket>> CarColorToTicketMap = new HashMap<String,List<Ticket>>();
	Map<Integer,Ticket> parkingSlotToTicketMap = new HashMap<Integer,Ticket>();
	MinHeap availableSpaces;
	public ParkingSpace(int n){
		MaxNoOfTickets = n;
		availableSpaces = new MinHeap(n);
		for(int i=1;i<=n;i++){
			availableSpaces.insertElement(i);
		}
		availableSpaces.minHeap();
	}
	
	public synchronized Ticket generateTicket(String registrationNo,String carColor){
		//Find an availble space which can allocated to this car
		Integer nearestAvailableSpace = availableSpaces.removeElement();
		if(nearestAvailableSpace == -1){
			return null;
		}
		
		//Create a new ticket with all the above values
		Ticket ticket = new Ticket(registrationNo,carColor,nearestAvailableSpace);
		//Add the ticket to the carRegistration and carColor Map in order for easy retrieval while querying
		carRegistrationToTicketMap.put(registrationNo, ticket);
		List<Ticket> ticketListWithGivenColor = CarColorToTicketMap.get(carColor);
		if(ticketListWithGivenColor == null){
			ticketListWithGivenColor = new ArrayList<Ticket>();
			CarColorToTicketMap.put(carColor, ticketListWithGivenColor);
		}
		ticketListWithGivenColor.add(ticket);
		parkingSlotToTicketMap.put(nearestAvailableSpace, ticket);
		return ticket;
	}
	
	public synchronized Collection<Ticket> getTicketList(){
		return carRegistrationToTicketMap.values();
	}
	
	public synchronized void releaseTicket(int parkingSlot){
		Ticket ticket = parkingSlotToTicketMap.remove(parkingSlot);
		//Remove the tickets from the maps
		carRegistrationToTicketMap.remove(ticket.getRegistrationNo());
		List<Ticket> ticketWithGivenColor = CarColorToTicketMap.get(ticket.getCarColor());
		ticketWithGivenColor.remove(ticket);
		
		availableSpaces.insertElement(parkingSlot);
	}
	
	public synchronized Integer getParkingSlotBasedOnRegistrationNo(String registraionNo){
		Ticket ticket = carRegistrationToTicketMap.get(registraionNo);
		if(ticket == null)
			return null;			
		return ticket.getParkingSlot();
	}
	
	public synchronized List<Integer> getParkingSlotBasedOnColor(String color){
		List<Ticket> ticketListWithColor = CarColorToTicketMap.get(color);
		List<Integer> parkingSlotList = new ArrayList<Integer>();
		for(int i=0;i<ticketListWithColor.size();i++){
			parkingSlotList.add(ticketListWithColor.get(i).getParkingSlot());
		}
		return parkingSlotList;
	}
	
	public synchronized List<Ticket> getParkingTicketsBasedOnColor(String color){
		List<Ticket> ticketListWithColor = CarColorToTicketMap.get(color);
		
		return ticketListWithColor;
	}
	
	public synchronized List<String> getRegistrationNosWithColor(String color){
		List<Ticket> ticketListWithColor = CarColorToTicketMap.get(color);
		List<String> registrationNoList = new ArrayList<String>();
		for(int i=0;i<ticketListWithColor.size();i++){
			registrationNoList.add(ticketListWithColor.get(i).getRegistrationNo());
		}
		return registrationNoList;
	}
	
	public static void main(String[] args) throws IOException{
		
		//1st argument will be considered as input file
		String inputFile = null;
		if(args.length > 0){
			inputFile = args[0]; 
		}
	
		BufferedReader bIn = null;
		if(inputFile != null){
			bIn = new BufferedReader(new FileReader(inputFile));
		}
		else{
			bIn = new BufferedReader(new InputStreamReader(System.in));
		}
			
		ParkingSpace parkingIns = null;
		while(true){
			String str = bIn.readLine();
			if(str == null || str.isEmpty()){
				break;
			}
			if(str.startsWith("create_parking_lot")){
				String[] vals = str.split(" ");
				int parkingSize = Integer.parseInt(vals[1]);
				parkingIns = new ParkingSpace(parkingSize);
				System.out.println("Created Parking Space with size " + parkingSize);
			}
			else if(str.startsWith("park")){
				String[] vals = str.split(" ");
				Ticket ticket = parkingIns.generateTicket(vals[1], vals[2]);
				if(ticket != null)
					System.out.println("Allocated Slot No : " + ticket.getParkingSlot());
				else
					System.out.println("Sorry, Parking Lot is full");
			}
			else if(str.startsWith("slot_numbers_for_cars_with_colour")){
				String[] vals = str.split(" ");
				List<Integer> parkingSlotList = parkingIns.getParkingSlotBasedOnColor(vals[1]);
				for(int i=0;i<parkingSlotList.size();i++){
					if(i!=0){
						System.out.print(",");
					}
					System.out.print(parkingSlotList.get(i));
				}
				System.out.print("\n");
			}
			else if(str.startsWith("slot_number_for_registration_number")){
				String[]  vals = str.split(" ");
				Integer parkingSlot = parkingIns.getParkingSlotBasedOnRegistrationNo(vals[1]);
				if(parkingSlot != null){
					System.out.println(parkingSlot);}
				else{
					System.out.println("Not Found");}
			}
			
			else if(str.startsWith("status")){
				Collection<Ticket> ticketList = parkingIns.getTicketList();
				Iterator<Ticket> iter = ticketList.iterator();
				while(iter.hasNext()){
					Ticket ticket = iter.next();
					System.out.println(ticket.getParkingSlot() + "   " + ticket.getRegistrationNo() + "   " + ticket.getCarColor());
				}
			}
			else if(str.startsWith("leave")){
				String[] vals = str.split(" ");
				parkingIns.releaseTicket(Integer.parseInt(vals[1]));
				System.out.println("Parking slot " + vals[1] + " is free");
			}
	}
	}
	}
